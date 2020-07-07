<?php

namespace AppBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Encoder\XmlEncoder;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use AppBundle\Entity\Support;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;

class SupportController extends Controller
{
    public function api_addAction(Request $request,$token)
    {
        if ($token!=$this->container->getParameter('token_app')) {
            throw new NotFoundHttpException("Page not found");  
        }
        $email = $request->get("email");
        $subject = $request->get("name");
        $message = $request->get("message");

        $em         = $this->getDoctrine()->getManager();
        $support    = new Support();
        $support->setEmail($email);
        $support->setSubject($subject);
        $support->setMessage($message);
        $em->persist($support);
        $em->flush();
        $code="200";
        $message="Votre message a bien été envoyé";
        $error=array(
            "code"=>$code,
            "message"=>$message,
            "values"=>array()
        );  
        header('Content-Type: application/json'); 
        $encoders = array(new XmlEncoder(), new JsonEncoder());
        $normalizers = array(new ObjectNormalizer());
        $serializer = new Serializer($normalizers, $encoders);
        $jsonContent=$serializer->serialize($error, 'json');
        return new Response($jsonContent);
    }
    public function indexAction(Request $request)
    {
        $em= $this->getDoctrine()->getManager();
        $dql        = "SELECT s FROM AppBundle:Support s  ";
        $query      = $em->createQuery($dql);
        $paginator  = $this->get('knp_paginator');
        $pagination = $paginator->paginate(
        $query,
        $request->query->getInt('page', 1),
            10
        );
        $supports= $em->getRepository('AppBundle:Support')->findAll();
        return $this->render('AppBundle:Support:index.html.twig',
            array(
                'pagination' => $pagination,
                "supports"=> $supports
            )
        );
    }
    public function viewAction(Request $request,$id)
    {
        $em         = $this->getDoctrine()->getManager();
        $support    = $em->getRepository("AppBundle:Support")->find($id);
        if ($support==null) {
            throw new NotFoundHttpException("Page not found");
        }
        return $this->render('AppBundle:Support:view.html.twig',array("support"=>$support));
    }
    public function deleteAction(Request $request,$id)
    {
        $em         = $this->getDoctrine()->getManager();
        $support    = $em->getRepository('AppBundle:Support')->find($id);
        if ($support==null) {
            throw new NotFoundHttpException("Page not found");
        }
        $form=$this->createFormBuilder(array('id' => $id))
            ->add('id', 'hidden')
            ->add('Yes', 'submit')
            ->getForm();
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()) {
            $em->remove($support);
            $em->flush();
            $request->getSession()->getFlashBag()->add('success','Operation has been done successfully');
            return $this->redirect($this->generateUrl('app_support_index'));
        }
        return $this->render("AppBundle:Support:delete.html.twig",array("form"=>$form->createView()));
    }
}
