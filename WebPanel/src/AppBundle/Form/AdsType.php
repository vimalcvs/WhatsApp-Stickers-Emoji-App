<?php
namespace AppBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormEvent;
use Symfony\Component\Form\FormEvents;
use Ivory\CKEditorBundle\Form\Type\CKEditorType;
class AdsType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {


        $builder->add('publisherid',null,array("label"=>"AdMob Publisher id"));
        $builder->add('appid',null,array("label"=>"AdMob App id"));
        $builder->add('nativebannerfacebookid',null,array("label"=>"Facebook Native Banner ID "));
        $builder->add('rewardedadmobid',null,array("label"=>"AdMob Rewarded ID "));
        $builder->add('banneradmobid',null,array("label"=>"AdMob Banner ID "));
        $builder->add('bannerfacebookid',null,array("label"=>"Facebook Banner ID "));
        $builder->add('bannertype',ChoiceType::class, array(
                "label"=>"Banner Ad Type",
                'choices' => array(
                    "FALSE" => "Disable Ad Banner",
                    "ADMOB" => "Google AdMob Ad Banner",
                    "FACEBOOK" => "Facebook Network Audience Ad Banner",
                    "BOTH"=>"Both"
                )));
        $builder->add('nativeadmobid',null,array("label"=>"AdMob Native ID "));
        $builder->add('nativefacebookid',null,array("label"=>"Facebook Native ID "));
        $builder->add('nativetype',ChoiceType::class, array(
                "label"=>"Native Ad Type",
                'choices' => array(
                    "FALSE" => "Disable Ad Native",
                    "ADMOB" => "Google AdMob Ad Native",
                    "FACEBOOK" => "Facebook Network Audience Ad Native",
                    "BOTH"=>"Both"
                )));
        $builder->add('nativeitem',null,array("label"=>"Native Ad Type "));
        $builder->add('interstitialadmobid',null,array("label"=>"AdMob Interstitial ID "));
        $builder->add('interstitialfacebookid',null,array("label"=>"Facebook Interstitial ID "));
        $builder->add('interstitialtype',ChoiceType::class, array(
                "label"=>"Interstitial Ad Type",
                'choices' => array(
                    "FALSE" => "Disable Ad Interstitial",
                    "ADMOB" => "Google AdMob Ad Interstitial",
                    "FACEBOOK" => "Facebook Network Audience Ad Interstitial",
                    "BOTH"=>"Both"
                )));    
        $builder->add('interstitialclick',null,array("label"=>"Interstitial Ad Type "));

        $builder->add('save', 'submit',array("label"=>"SAVE"));
    }
    public function getName()
    {
        return 'Ads';
    }
}
?>