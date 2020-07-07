<?php
namespace AppBundle\Controller;
use AppBundle\Entity\Sticker;
use AppBundle\Form\StickerType;
use MediaBundle\Entity\Media;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Form\FormError;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;

class StickerController extends Controller {
	public function sizePack($pack, $em) {
		$size = 0;
		foreach ($pack->getStickers() as $key => $sticker) {
			$size += $sticker->getSize();
		}
		$pack->setSize($size);
		$em->flush();
	}
	public function addAction(Request $request, $id) {
		$em = $this->getDoctrine()->getManager();
		$pack = $em->getRepository("AppBundle:Pack")->find($id);
		if ($pack == null) {
			throw new NotFoundHttpException("Page not found");
		}
		if (sizeof($pack->getStickers())>29) {
			$this->addFlash('error', "You can't add more then 30 stickers inside this pack");
			return $this->redirect($this->generateUrl('app_pack_stickers', array("id" => $pack->getId())));		
		}
		$sticker = new Sticker();
		$form = $this->createForm(new StickerType(), $sticker);
		$em = $this->getDoctrine()->getManager();
		$sticker->setEmojis(base64_decode($sticker->getEmojis()));
		$form->handleRequest($request);
		if ($form->isSubmitted() && $form->isValid()) {
			$sticker->setEmojis(base64_encode($sticker->getEmojis()));
			if ($sticker->getFile() != null) {
				$media = new Media();
				$media->setFile($sticker->getFile());
				$media->upload($this->container->getParameter('files_directory'));
				$em->persist($media);
				$em->flush();
				$sticker->setMedia($media);
				$max = 0;
				$stickers = $em->getRepository('AppBundle:Sticker')->findBy(array("pack"=>$pack));
				foreach ($stickers as $key => $value) {
					if ($value->getPosition() > $max) {
						$max = $value->getPosition();
					}
				}
				$sticker->setPosition($max + 1);
				$sticker->setPack($pack);
				$sticker->setSize($sticker->getFile()->getClientSize());
				$em->persist($sticker);
				$em->flush();

				$this->sizePack($pack, $em);

				$this->addFlash('success', 'Operation has been done successfully');
				if ($pack->getReview()) {
					return $this->redirect($this->generateUrl('app_pack_review', array("id" => $pack->getId())));
				}else{
					return $this->redirect($this->generateUrl('app_pack_stickers', array("id" => $pack->getId())));
				}
			} else {
				$error = new FormError("This value should not be blank");
				$form->get('file')->addError($error);
			}
		}
		return $this->render("AppBundle:Sticker:add.html.twig", array("pack" => $pack, "form" => $form->createView()));
	}

	public function upAction(Request $request, $id) {
		$em = $this->getDoctrine()->getManager();
		$sticker = $em->getRepository("AppBundle:Sticker")->find($id);
		if ($sticker == null) {
			throw new NotFoundHttpException("Page not found");
		}
		if ($sticker->getPosition() > 1) {
			$position = $sticker->getPosition();
			$stickers = $em->getRepository('AppBundle:Sticker')->findBy(array("pack" => $sticker->getPack()), array("position" => "asc"));
			foreach ($stickers as $key => $value) {
				if ($value->getPosition() == $position - 1) {
					$value->setPosition($position);
				}
			}
			$sticker->setPosition($sticker->getPosition() - 1);
			$em->flush();
		}
		$pack = $sticker->getPack();
		if ($pack->getReview()) {
			return $this->redirect($this->generateUrl('app_pack_review', array("id" => $pack->getId())));
		}else{
			return $this->redirect($this->generateUrl('app_pack_stickers', array("id" => $pack->getId())));
		}
	}
	public function downAction(Request $request, $id) {
		$em = $this->getDoctrine()->getManager();
		$sticker = $em->getRepository("AppBundle:Sticker")->find($id);
		if ($sticker == null) {
			throw new NotFoundHttpException("Page not found");
		}
		$max = 0;
		$categories = $em->getRepository('AppBundle:Sticker')->findBy(array("pack" => $sticker->getPack()), array("position" => "asc"));
		foreach ($categories as $key => $value) {
			$max = $value->getPosition();
		}
		if ($sticker->getPosition() < $max) {
			$position = $sticker->getPosition();
			foreach ($categories as $key => $value) {
				if ($value->getPosition() == $position + 1) {
					$value->setPosition($position);
				}
			}
			$sticker->setPosition($sticker->getPosition() + 1);
			$em->flush();
		}
		$pack = $sticker->getPack();
		if ($pack->getReview()) {
			return $this->redirect($this->generateUrl('app_pack_review', array("id" => $pack->getId())));
		}else{
			return $this->redirect($this->generateUrl('app_pack_stickers', array("id" => $pack->getId())));
		}
	}
	public function deleteAction($id, Request $request) {
		$em = $this->getDoctrine()->getManager();

		$sticker = $em->getRepository("AppBundle:Sticker")->find($id);
		if ($sticker == null) {
			throw new NotFoundHttpException("Page not found");
		}

		$form = $this->createFormBuilder(array('id' => $id))
			->add('id', 'hidden')
			->add('Yes', 'submit')
			->getForm();
		$form->handleRequest($request);
		if ($form->isSubmitted() && $form->isValid()) {
			$media = $sticker->getMedia();
			$sticker_old = $sticker;
			$em->remove($sticker);
			$em->flush();

			if ($media != null) {
				$media->delete($this->container->getParameter('files_directory'));
				$em->remove($media);
				$em->flush();
			}
			$categories = $em->getRepository('AppBundle:Sticker')->findBy(array("pack" => $sticker_old->getPack()), array("position" => "asc"));
			$p = 1;
			foreach ($categories as $key => $value) {
				$value->setPosition($p);
				$p++;
			}
			$em->flush();

			$pack = $sticker_old->getPack();
			if ($pack->getReview()) {
				return $this->redirect($this->generateUrl('app_pack_review', array("id" => $pack->getId())));
			}else{
				return $this->redirect($this->generateUrl('app_pack_stickers', array("id" => $pack->getId())));
			}
		}
		return $this->render('AppBundle:Sticker:delete.html.twig', array("form" => $form->createView(), "sticker" => $sticker));
	}
	public function editAction(Request $request, $id) {
		$em = $this->getDoctrine()->getManager();
		$sticker = $em->getRepository("AppBundle:Sticker")->find($id);
		if ($sticker == null) {
			throw new NotFoundHttpException("Page not found");
		}
		$sticker->setEmojis(base64_decode($sticker->getEmojis()));
		$form = $this->createForm(new StickerType(), $sticker);
		$form->handleRequest($request);
		if ($form->isSubmitted() && $form->isValid()) {
			$sticker->setEmojis(base64_encode($sticker->getEmojis()));

			if ($sticker->getFile() != null) {
				$media = new Media();
				$media_old = $sticker->getMedia();
				$media->setFile($sticker->getFile());
				$media->upload($this->container->getParameter('files_directory'));
				$em->persist($media);
				$em->flush();
				$sticker->setMedia($media);
				$sticker->setSize($sticker->getFile()->getClientSize());

				$media_old->delete($this->container->getParameter('files_directory'));
				$em->remove($media_old);
				$em->flush();
			}
			$em->persist($sticker);
			$em->flush();
			$this->sizePack($sticker->getPack(), $em);
			$this->addFlash('success', 'Operation has been done successfully');
			$pack = $sticker->getPack();
			if ($pack->getReview()) {
				return $this->redirect($this->generateUrl('app_pack_review', array("id" => $pack->getId())));
			}else{
				return $this->redirect($this->generateUrl('app_pack_stickers', array("id" => $pack->getId())));
			}
		}
		return $this->render("AppBundle:Sticker:edit.html.twig", array("sticker" => $sticker, "form" => $form->createView()));
	}
}
?>