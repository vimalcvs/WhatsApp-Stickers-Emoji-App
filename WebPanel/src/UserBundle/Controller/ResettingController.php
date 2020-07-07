<?php 
namespace UserBundle\Controller;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Response;
use UserBundle\Entity\User;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;
use Symfony\Component\HttpFoundation\Request;
use FOS\UserBundle\Form\Model\ChangePassword;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Encoder\XmlEncoder;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use FOS\UserBundle\Model\UserInterface;

class ResettingController   extends Controller
{
	const SESSION_EMAIL = 'fos_user_send_resetting_email/email';
    public function api_emailAction($email,$token){
        if ($token!=$this->container->getParameter('token_app')) {
            throw new NotFoundHttpException("Page not found");  
        }
        $code="200";
        $message="A new activation key email has successfully been sent";
        $errors=array();
        $username = $email;
        $user = $this->container->get('fos_user.user_manager')->findUserByUsernameOrEmail($username);

        if (null === $user) {
            $code="500";
            $message="There is no account for this email address";
        }else{
            if ($user->isPasswordRequestNonExpired($this->container->getParameter('fos_user.resetting.token_ttl'))) {
                $code="500";
                $message="You cannot reset the password just now as the maximum number of invalid login attempts has been reached. Try again in 24 hours";
            }else{
                if ($user->hasRole("ROLE_ADMIN")) {
                        $code="500";
                        $message="There is no account for this email address";
                }
                elseif ($user->getType()!="email") {
                         $code="500";
                        $message="There is no account for this email address";
                }else{
                	$tokenGenerator = $this->container->get('fos_user.util.token_generator');
                    $tkn = $tokenGenerator->generateToken();
                    if (null === $user->getConfirmationToken()) {
                        /** @var $tokenGenerator \FOS\UserBundle\Util\TokenGeneratorInterface */
                        $user->setConfirmationToken($tkn);
                    }
                    $this->container->get('session')->set(static::SESSION_EMAIL, $this->getObfuscatedEmail($user));

    					$to = $email;
    					$subject = "Password Resetting";
    					$from = 'admin@admin.com';
    					 
    					// To send HTML mail, the Content-type header must be set
    					$headers  = 'MIME-Version: 1.0' . "\r\n";
    					$headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";
    					 
    					// Create email headers
    					$headers .= 'From: '.$from."\r\n".
    					    'Reply-To: '.$from."\r\n" .
    					    'X-Mailer: PHP/' . phpversion();
    					 
    					// Compose a simple HTML email message
    					$content = '<html><body>';
    					$content .= '<h1 style="color:#f40;">Hi '.$user->getName().'</h1>';
    					$content .= '<p style="color:#080;font-size:18px;">To reset your password , copy this code and paste it in application:</p>';
    					$content .= '<p style="color:#080;font-size:18px;">Code :</p>'. $tkn;
    					$content .= '<p style="color:#080;font-size:18px;">Thanks!</p>';
    					$content .= '</body></html>';
    					if(mail($to, $subject, $content, $headers)){
    					    
    					}
                    $user->setPasswordRequestedAt(new \DateTime());
                    $this->container->get('fos_user.user_manager')->updateUser($user);
                }
            }
        }
        $error=array(
            "code"=>$code,
            "message"=>$message,
            "values"=>$errors
        );
        $encoders = array(new XmlEncoder(), new JsonEncoder());
        $normalizers = array(new ObjectNormalizer());
        $serializer = new Serializer($normalizers, $encoders);
        $jsonContent=$serializer->serialize($error, 'json');
        return new Response($jsonContent);        
    }
    public function api_requestAction($key,$token){
        if ($token!=$this->container->getParameter('token_app')) {
            throw new NotFoundHttpException("Page not found");  
        }
        $code="200";
        $message="Reset your password";
        $errors=array();
        $user = $this->container->get('fos_user.user_manager')->findUserByConfirmationToken($key);

        if (null === $user) {
            $code="500";
            $message="There is no account for this key";
        }else{
            if ($user->hasRole("ROLE_ADMIN")) {
                $code="500";
                $message="There is no account for this key";
            }
            if ($user->getType()!="email") {
                $code="500";
                $message="There is no account for this key";
            }else{
                $code="200";
                $message="Reset your password";
                $errors[]=array("name"=>"id","value"=>$user->getId());
                $errors[]=array("name"=>"token","value"=>sha1($user->getPassword()));
            }
        }
        $error=array(
            "code"=>$code,
            "message"=>$message,
            "values"=>$errors
        );
        $encoders = array(new XmlEncoder(), new JsonEncoder());
        $normalizers = array(new ObjectNormalizer());
        $serializer = new Serializer($normalizers, $encoders);
        $jsonContent=$serializer->serialize($error, 'json');
        return new Response($jsonContent);        
    }
    public function api_resetAction($id,$key,$new_password,$token)
    {
        if ($token!=$this->container->getParameter('token_app')) {
            throw new NotFoundHttpException("Page not found");  
        }
        $code="200";
        $message="";
        $errors=array();
        $em = $this->getDoctrine()->getManager();
        $user=$em->getRepository('UserBundle:User')->findOneBy(array("id"=>$id));  
        if ($user) {
            if (sha1($user->getPassword()) != $key) {
                $code="500";
                $message="There is no account for this key";
            }
            elseif ($user->hasRole("ROLE_ADMIN")) {
                $code="500";
                $message="There is no account for this key";
            }
            elseif($user->getType()!="email") {
                $code="500";
                $message="There is no account for this key";
            }else{
                if (strlen($new_password)<6) {
                    $code=500;
                    $message="cette valeur est trop courte";
                }else{
            $encoder_service = $this->get('security.encoder_factory');
            $encoder = $encoder_service->getEncoder($user);

                    $newPasswordEncoded = $encoder->encodePassword($new_password, $user->getSalt());
                    $user->setPassword($newPasswordEncoded);
                    $user->setConfirmationToken(NULL);
                    $user->setPasswordRequestedAt(NULL);
                    $em->persist($user);
                    $em->flush();
                    $code=200;
                    $message="Password has been resetted successfully";
                        $errors[]=array("name"=>"id","value"=>$user->getId());
                        $errors[]=array("name"=>"name","value"=>$user->getName());
                        $errors[]=array("name"=>"type","value"=>$user->getType());
                        $errors[]=array("name"=>"username","value"=>$user->getUsername());
                        $errors[]=array("name"=>"salt","value"=>$user->getSalt());
                        $errors[]=array("name"=>"token","value"=>sha1($user->getPassword()));
                }
            }
        }else{
                $code="500";
                $message="There is no account for this key";
        }
        $error=array(
                "code"=>$code,
                "message"=>$message,
                "values"=>$errors
                );
        $encoders = array(new XmlEncoder(), new JsonEncoder());
        $normalizers = array(new ObjectNormalizer());
        $serializer = new Serializer($normalizers, $encoders);
        $jsonContent=$serializer->serialize($error, 'json');
        return new Response($jsonContent);   
    }
    protected function getObfuscatedEmail(UserInterface $user)
    {
        $email = $user->getEmail();
        if (false !== $pos = strpos($email, '@')) {
            $email = '...' . substr($email, $pos);
        }

        return $email;
    }
} 
?>