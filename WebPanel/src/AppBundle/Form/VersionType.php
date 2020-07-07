<?php
namespace AppBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;

class VersionType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
       $builder->add('title',null,array("label"=>"Version title"));
       $builder->add('features',null,array("label"=>"The new features"));
       $builder->add('code',null,array("label"=>"Version code"));
       $builder->add('enabled',null,array("label"=>"Enabled"));
       $builder->add('save', 'submit',array("label"=>"save"));
    }
    public function getName()
    {
        return 'Version';
    }
}
?>