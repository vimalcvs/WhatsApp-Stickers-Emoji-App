<?php
namespace AppBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\FormEvent;
use Symfony\Component\Form\FormEvents;

class StickerType extends AbstractType {
	public function buildForm(FormBuilderInterface $builder, array $options) {
		$builder->addEventListener(FormEvents::PRE_SET_DATA, function (FormEvent $event) {
			$article = $event->getData();
			$form = $event->getForm();
			if ($article and null !== $article->getId()) {
				$form->add("file", null, array("label" => "", "required" => false));
			} else {
				$form->add("file", null, array("label" => "", "required" => true));
			}
		});
		$builder->add('save', 'submit', array("label" => "SAVE STICKER"));

	}
	public function getName() {
		return 'Sticker';
	}
}
?>