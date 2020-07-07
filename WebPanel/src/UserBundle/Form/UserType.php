<?php
namespace UserBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use UserBundle\Entity\User;
class UserType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
       $builder
            ->add('name',"text",array("label"=>"Full name"))
            ->add('facebook',"text",array("label"=>"Facebook account","required"=>false))
            ->add('twitter',"text",array("label"=>"Twitter account","required"=>false))
            ->add('instagram',"text",array("label"=>"Instagram account","required"=>false))
            ->add('emailo',"text",array("label"=>"E-email","required"=>false))
            ->add('type',"text",array("label"=>"Account type","read_only"=>true))
            ->add('email', "text",array("label"=>"E-mail or AuthId",'read_only' => true))
            ->add('trusted',null,array())
            ->add('enabled',null,array())
        ;
        $builder->add('save', 'submit',array("label"=>"SAVE USER"));

    }
    public function getName()
    {
        return 'user';
    }
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class'      => User::class,
            'csrf_protection' => true,
            'csrf_field_name' => '_token',
            // a unique key to help generate the secret token
            'csrf_token_id'   => 'task_item',
        ));
    }
}
?>
