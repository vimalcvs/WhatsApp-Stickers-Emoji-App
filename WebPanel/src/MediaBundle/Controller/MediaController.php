<?php

namespace MediaBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use MediaBundle\Entity\Media;
use MediaBundle\Entity\Gallery;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Encoder\XmlEncoder;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;

class MediaController extends Controller
{
    public function indexAction(Request $request)
    {	
        $em=$this->getDoctrine()->getManager();
        $medias=$em->getRepository("MediaBundle:Media")->findBy(array("enabled"=>false));
            

        $paginator  = $this->get('knp_paginator');
        $pagination = $paginator->paginate(
            $medias, /* query NOT result */
            $request->query->getInt('page', 1)/*page number*/,
            8/*limit per page*/
        );
        return $this->render('MediaBundle:Media:index.html.twig',array(
            'pagination' => $pagination,
        ));
    }
    public function deleteAction($id,Request $request){
    	$em=$this->getDoctrine()->getManager();
    	$media = $em->getRepository("MediaBundle:Media")->find($id);
    	if($media==null){
    		return $this->redirect($this->generateUrl('media_index'));
    	}
    	var_dump($media);
    	$form=$this->createFormBuilder(array('id' => $id))
            ->add('id', 'hidden')
            ->add('oui', 'submit')
            ->getForm();
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
 			$media->delete($this->container->getParameter('files_directory'));
        	$em->remove($media);
			$em->flush();
        	return $this->redirect($this->generateUrl('media_index'));
    	}
    	return $this->render('MediaBundle:Media:delete.html.twig',array("form"=>$form->createView()));

    }

    public function addAction(Request $request)
    {

        $media= new Media();
        $form=$this->createFormBuilder($media)
                   ->add("file")
                   ->getForm();
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em=$this->getDoctrine()->getManager();
            $media->upload($this->container->getParameter('files_directory'));
            $em->persist($media);
            $em->flush();
            #return $this->redirect($this->generateUrl('media_index'));
        }
       return $this->render("MediaBundle:Media:add.html.twig",array("form"=>$form->createView(),"media"=>$media)
        
        );
    }
    public function api_uploadAction(Request $request,$token)
    {
        if ($token!=$this->container->getParameter('token_app')) {
            throw new NotFoundHttpException("Page not found");  
        }
        $code="200";
        $message="madkhlach";
        $errors=array();
        if ($this->getRequest()->files->has('uploaded_file')) {
            $message="dkhal";
            $media= new Media();
            $em=$this->getDoctrine()->getManager();

            $media->setFile($this->getRequest()->files->get('uploaded_file'));
            $media->upload($this->container->getParameter('files_directory'));
            $em->persist($media);
            $em->flush();
            $imagineCacheManager = $this->get('liip_imagine.cache.manager');
            $errors[]=array("name"=>"id","value"=>$media->getId());
            $errors[]=array("name"=>"url","value"=>$imagineCacheManager->getBrowserPath($media->getLink(), 'article_thumb'));

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
        public function api_deleteAction(Request $request,$id,$token)
    {
        if ($token!=$this->container->getParameter('token_app')) {
            throw new NotFoundHttpException("Page not found");  
        }
        $code="200";
        $message="le fichier a ete supprimé";
        $errors=array();
        $em=$this->getDoctrine()->getManager();
        $media = $em->getRepository("MediaBundle:Media")->find($id);
        if($media==null){
            $code="404";
            $message="Le fichier n'existe pas sur nos serveur"; 
        }else{
            $media->delete($this->container->getParameter('files_directory'));
            $em->remove($media);
            $em->flush();
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

}
