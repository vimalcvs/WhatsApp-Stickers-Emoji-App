<?php

namespace AppBundle\Controller;

use AppBundle\Entity\Slide;
use AppBundle\Form\SlideType;
use MediaBundle\Entity\Media;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Form\FormError;
use Symfony\Component\HttpFoundation\Request;

class SlideController extends Controller {
	public function indexAction() {
		$em = $this->getDoctrine()->getManager();
		$slides = $em->getRepository("AppBundle:Slide")->findBy(array(), array("position" => "asc"));
		return $this->render("AppBundle:Slide:index.html.twig", array("slides" => $slides));
	}
	public function api_allAction() {
		$em = $this->getDoctrine()->getManager();
		$slides = $em->getRepository("AppBundle:Slide")->findBy(array(), array("position" => "asc"));
		$imagineCacheManager = $this->get('liip_imagine.cache.manager');

		return $this->render('AppBundle:Slide:api_all.html.php', array("slides" => $slides));
	}
	public function upAction(Request $request, $id) {
		$em = $this->getDoctrine()->getManager();
		$slide = $em->getRepository("AppBundle:Slide")->find($id);
		if ($slide == null) {
			throw new NotFoundHttpException("Page not found");
		}
		if ($slide->getPosition() > 1) {
			$p = $slide->getPosition();
			$slides = $em->getRepository('AppBundle:Slide')->findAll();
			foreach ($slides as $key => $value) {
				if ($value->getPosition() == $p - 1) {
					$value->setPosition($p);
				}
			}
			$slide->setPosition($slide->getPosition() - 1);
			$em->flush();
		}
		return $this->redirect($this->generateUrl('app_slide_index'));
	}
	public function downAction(Request $request, $id) {
		$em = $this->getDoctrine()->getManager();
		$slide = $em->getRepository("AppBundle:Slide")->find($id);
		if ($slide == null) {
			throw new NotFoundHttpException("Page not found");
		}
		$max = 0;
		$slides = $em->getRepository('AppBundle:Slide')->findBy(array(), array("position" => "asc"));
		foreach ($slides as $key => $value) {
			$max = $value->getPosition();
		}
		if ($slide->getPosition() < $max) {
			$p = $slide->getPosition();
			foreach ($slides as $key => $value) {
				if ($value->getPosition() == $p + 1) {
					$value->setPosition($p);
				}
			}
			$slide->setPosition($slide->getPosition() + 1);
			$em->flush();
		}
		return $this->redirect($this->generateUrl('app_slide_index'));
	}
	public function deleteAction($id, Request $request) {
		$em = $this->getDoctrine()->getManager();

		$slide = $em->getRepository("AppBundle:Slide")->find($id);
		if ($slide == null) {
			throw new NotFoundHttpException("Page not found");
		}
		$form = $this->createFormBuilder(array('id' => $id))
			->add('id', 'hidden')
			->add('Yes', 'submit')
			->getForm();
		$form->handleRequest($request);
		if ($form->isSubmitted() && $form->isValid()) {
			$media_old = $slide->getMedia();
			$em->remove($slide);
			$em->flush();

			if ($media_old != null) {
				$media_old->delete($this->container->getParameter('files_directory'));
				$em->remove($media_old);
				$em->flush();
			}
			$slides = $em->getRepository('AppBundle:Slide')->findBy(array(), array("position" => "asc"));

			$p = 1;
			foreach ($slides as $key => $value) {
				$value->setPosition($p);
				$p++;
			}
			$em->flush();
			$this->addFlash('success', 'Operation has been done successfully');
			return $this->redirect($this->generateUrl('app_slide_index'));
		}
		return $this->render('AppBundle:Slide:delete.html.twig', array("form" => $form->createView()));
	}
	public function addAction(Request $request) {
		$em = $this->getDoctrine()->getManager();

		$slide = new Slide();
		$form = $this->createForm(new SlideType(), $slide);
		$form->handleRequest($request);
		if ($form->isSubmitted() && $form->isValid()) {
			if ($slide->getFile() != null) {
				$media = new Media();
				$media->setFile($slide->getFile());
				$media->upload($this->container->getParameter('files_directory'));
				$em->persist($media);
				$em->flush();
				$slide->setMedia($media);
				$slide->setTitle(base64_encode($slide->getTitle()));
				$max = 0;
				$slides = $em->getRepository('AppBundle:Slide')->findBy(array(), array("position" => "asc"));
				foreach ($slides as $key => $value) {
					if ($value->getPosition() > $max) {
						$max = $value->getPosition();
					}
				}
				$slide->setPosition($max + 1);
				if ($slide->getType() == 1) {
					$slide->setUrl(null);
					$slide->setPack(null);
				} else if ($slide->getType() == 2) {
					$slide->setCategory(null);
					$slide->setPack(null);
				} else if ($slide->getType() == 3) {
					$slide->setCategory(null);
					$slide->setUrl(null);
				}
				$em->persist($slide);
				$em->flush();
				$this->addFlash('success', 'Operation has been done successfully');
				return $this->redirect($this->generateUrl('app_slide_index'));
			} else {
				$error = new FormError("Required image file");
				$form->get('file')->addError($error);
			}
		}
		return $this->render('AppBundle:Slide:add.html.twig', array("form" => $form->createView()));
	}
	public function editAction(Request $request, $id) {
		$em = $this->getDoctrine()->getManager();
		$slide = $em->getRepository("AppBundle:Slide")->find($id);
		if ($slide == null) {
			throw new NotFoundHttpException("Page not found");
		}
		$slide->setTitle(base64_decode($slide->getTitle()));

		$form = $this->createForm(new SlideType(), $slide);
		$form->handleRequest($request);
		if ($form->isSubmitted() && $form->isValid()) {
			if ($slide->getFile() != null) {
				$media = new Media();
				$media_old = $slide->getMedia();
				$media->setFile($slide->getFile());
				$media->upload($this->container->getParameter('files_directory'));
				$em->persist($media);
				$em->flush();
				$slide->setMedia($media);
				$em->flush();
				$media_old->delete($this->container->getParameter('files_directory'));
				$em->remove($media_old);
				$em->flush();
			}
			$slide->setTitle(base64_encode($slide->getTitle()));
			if ($slide->getType() == 1) {
				$slide->setUrl(null);
				$slide->setPack(null);
			} else if ($slide->getType() == 2) {
				$slide->setCategory(null);
				$slide->setPack(null);
			} else if ($slide->getType() == 3) {
				$slide->setCategory(null);
				$slide->setUrl(null);
			}
			$em->persist($slide);
			$em->flush();

			$this->addFlash('success', 'Operation has been done successfully');
			return $this->redirect($this->generateUrl('app_slide_index'));

		}
		return $this->render("AppBundle:Slide:edit.html.twig", array("form" => $form->createView()));
	}
}
?>