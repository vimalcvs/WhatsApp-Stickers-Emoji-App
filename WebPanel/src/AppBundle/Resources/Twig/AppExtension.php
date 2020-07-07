<?php 
namespace AppBundle\Twig;

class AppExtension extends \Twig_Extension
{
    public function getFilters()
    {
        return array(
            new \Twig_SimpleFilter('suite', array($this, 'suiteFilter')),
        );
    }

    public function suiteFilter($text)
    {

        $suite = strip_tags($text);
        $suite =str_replace(array("&quot;","&nbsp;","&hellip;","&rsquo;","&raquo;","&laquo;"),array(""," ","","","",""), $suite);
        return $suite;
    }

    public function getName()
    {
        return 'app_extension';
    }
}