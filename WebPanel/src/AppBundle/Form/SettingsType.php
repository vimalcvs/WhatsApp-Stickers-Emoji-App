<?php
namespace AppBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormEvent;
use Symfony\Component\Form\FormEvents;
use Ivory\CKEditorBundle\Form\Type\CKEditorType;
class SettingsType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('appname',null,array("label"=>"Application name"));
        $builder->add('appdescription',null,array("label"=>"Application description"));
        $builder->add('firebasekey',null,array("label"=>"Firebase Legacy server key"));
       $builder->add('privacypolicy', CKEditorType::class, 
                    array(
                        'config_name' => 'user_config'
                    )
                );
        $builder->add('googleplay',null,array("label"=>"Google Play App Url"));
        $builder->addEventListener(FormEvents::PRE_SET_DATA, function (FormEvent $event) {
            $article = $event->getData();
            $form = $event->getForm();
            if ($article and null !== $article->getId()) {
                 $form->add("file",null,array("label"=>"","required"=>false));
            }else{
                 $form->add("file",null,array("label"=>"","required"=>true));
            }
        });
        $builder->add('save', 'submit',array("label"=>"SAVE"));
    }
    public function getName()
    {
        return 'Settings';
    }
}
?>