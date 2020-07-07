<?php 

namespace AppBundle\Form;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
 
 class PackType extends AbstractType
 {
     /**
      * {@inheritdoc}
      */
     public function buildForm(FormBuilderInterface $builder, array $options)
     {
	        $builder->add('name',null,array("label"=>"Pack name"));
	        $builder->add('publisher',null,array("label"=>"Publisher"));
	        $builder->add('publisheremail',null,array("label"=>"Publisher E-mail"));
	        $builder->add('publisherwebsite',null,array("label"=>"Publisher Website"));
	        $builder->add('privacypolicywebsite',null,array("label"=>"Website Privacy Policy"));
	        $builder->add('licenseagreementwebsite',null,array("label"=>"Website License Agreement"));
	        $builder->add('enabled',null,array("label"=>"Enabled"));
	        $builder->add('premium',null,array("label"=>"Premium"));
	        $builder->add(
        			'categories'
        			,"entity"
        			,array(
        				"class"=>"AppBundle:Category",
        				"expanded"=>true,
        				"multiple"=>true,
        				)
	        		);
 	        $builder->add(
	 			'files'
	 			,"file"
	 			,array(
	 				"label"=>"",
	 				"multiple"=>true,
	 				"data_class"=>null
	 				)
 		);
	       $builder->add('tags',null,array("label"=>"Pack tags"));
	       $builder->add("file",null,array("label"=>"","required"=>true));
	       $builder->add('save', 'submit',array("label"=>"ADD PACK"));
     }
 
     /**
      * {@inheritdoc}
      */
     public function getName()
     {
         return 'Pack';
     }
 } ?>