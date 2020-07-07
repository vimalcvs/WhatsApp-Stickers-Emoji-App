<?php

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\Common\Collections\ArrayCollection;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use MediaBundle\Entity\Media;
/**
 * Settings
 *
 * @ORM\Table(name="settings_table")
 * @ORM\Entity(repositoryClass="AppBundle\Repository\SettingsRepository")
 */
class Settings
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="appname", type="string", length=255 , nullable = true)
     */
    private $appname;

    /**
     * @var string
     *
     * @ORM\Column(name="appdescription", type="text", nullable = true)
     */
    private $appdescription;

    /**
     * @var string
     *
     * @ORM\Column(name="googleplay", type="text", nullable = true)
     */
    private $googleplay;

    /**
     * @var string
     *
     * @ORM\Column(name="privacypolicy", type="text", nullable = true)
     */
    private $privacypolicy;

    /**
     * @var string
     *
     * @ORM\Column(name="firebasekey", type="string", length=255 , nullable = true)
     */
    private $firebasekey;

    /**
     * @var string
     *
     * @ORM\Column(name="publisherid", type="string", length=255 , nullable = true)
     */
    private $publisherid;


    /**
     * @var string
     *
     * @ORM\Column(name="appid", type="string", length=255 , nullable = true)
     */
    private $appid;

    /**
     * @var string
     *
     * @ORM\Column(name="rewardedadmobid", type="string", length=255 , nullable = true)
     */
    private $rewardedadmobid;

    /**
     * @var string
     *
     * @ORM\Column(name="banneradmobid", type="string", length=255 , nullable = true)
     */
    private $banneradmobid;


    /**
     * @var string
     *
     * @ORM\Column(name="bannerfacebookid", type="string", length=255 , nullable = true)
     */
    private $bannerfacebookid;

    /**
     * @var string
     *
     * @ORM\Column(name="nativebannerfacebookid", type="string", length=255 , nullable = true)
     */
    private $nativebannerfacebookid;

    /**
     * @var string
     *
     * @ORM\Column(name="bannertype", type="string", length=255 , nullable = true)
     */
    private $bannertype;

    /**
     * @var string
     *
     * @ORM\Column(name="nativeadmobid", type="string", length=255 , nullable = true)
     */
    private $nativeadmobid;

    /**
     * @var string
     *
     * @ORM\Column(name="nativefacebookid", type="string", length=255 , nullable = true)
     */
    private $nativefacebookid;

    /**
     * @var string
     *
     * @ORM\Column(name="nativeitem",  type="integer",  length=255 , nullable = true)
     */
    private $nativeitem;


    /**
     * @var string
     *
     * @ORM\Column(name="nativetype", type="string", length=255 , nullable = true)
     */
    private $nativetype;

    /**
     * @var string
     *
     * @ORM\Column(name="interstitialadmobid", type="string", length=255 , nullable = true)
     */
    private $interstitialadmobid;

    /**
     * @var string
     *
     * @ORM\Column(name="interstitialfacebookid", type="string", length=255 , nullable = true)
     */
    private $interstitialfacebookid;


     /**
     * @var string
     *
     * @ORM\Column(name="interstitialtype", type="string", length=255 , nullable = true)
     */
    private $interstitialtype;

     /**
     * @var string
     *
     * @ORM\Column(name="interstitialclick", type="integer", length=255 , nullable = true)
     */
    private $interstitialclick;

    /**
     * @Assert\File(mimeTypes={"image/jpeg","image/png" },maxSize="40M")
     */
    private $file;
     /**
     * @ORM\ManyToOne(targetEntity="MediaBundle\Entity\Media")
     * @ORM\JoinColumn(name="media_id", referencedColumnName="id")
     * @ORM\JoinColumn(nullable=false)
     */
    private $media;

    /**
     * Get id
     *
     * @return integer 
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set appname
     *
     * @param string $appname
     * @return Settings
     */
    public function setAppname($appname)
    {
        $this->appname = $appname;

        return $this;
    }

    /**
     * Get appname
     *
     * @return string 
     */
    public function getAppname()
    {
        return $this->appname;
    }

    /**
     * Set appdescription
     *
     * @param string $appdescription
     * @return Settings
     */
    public function setAppdescription($appdescription)
    {
        $this->appdescription = $appdescription;

        return $this;
    }

    /**
     * Get appdescription
     *
     * @return string 
     */
    public function getAppdescription()
    {
        return $this->appdescription;
    }

    /**
     * Set googleplay
     *
     * @param string $googleplay
     * @return Settings
     */
    public function setGoogleplay($googleplay)
    {
        $this->googleplay = $googleplay;

        return $this;
    }

    /**
     * Get googleplay
     *
     * @return string 
     */
    public function getGoogleplay()
    {
        return $this->googleplay;
    }

    /**
     * Set privacypolicy
     *
     * @param string $privacypolicy
     * @return Settings
     */
    public function setPrivacypolicy($privacypolicy)
    {
        $this->privacypolicy = $privacypolicy;

        return $this;
    }

    /**
     * Get privacypolicy
     *
     * @return string 
     */
    public function getPrivacypolicy()
    {
        return $this->privacypolicy;
    }

    /**
     * Set firebasekey
     *
     * @param string $firebasekey
     * @return Settings
     */
    public function setFirebasekey($firebasekey)
    {
        $this->firebasekey = $firebasekey;

        return $this;
    }

    /**
     * Get firebasekey
     *
     * @return string 
     */
    public function getFirebasekey()
    {
        return $this->firebasekey;
    }

    public function getFile()
    {
        return $this->file;
    }
    public function setFile($file)
    {
        $this->file = $file;
        return $this;
    }
    /**
     * Set media
     *
     * @param string $media
     * @return image
     */
    public function setMedia(Media $media)
    {
        $this->media = $media;

        return $this;
    }

    /**
     * Get media
     *
     * @return string 
     */
    public function getMedia()
    {
        return $this->media;
    }

    /**
    * Get banneradmobid
    * @return  
    */
    public function getBanneradmobid()
    {
        return $this->banneradmobid;
    }
    
    /**
    * Set banneradmobid
    * @return $this
    */
    public function setBanneradmobid($banneradmobid)
    {
        $this->banneradmobid = $banneradmobid;
        return $this;
    }

    /**
    * Get bannerfacebookid
    * @return  
    */
    public function getBannerfacebookid()
    {
        return $this->bannerfacebookid;
    }
    
    /**
    * Set bannerfacebookid
    * @return $this
    */
    public function setBannerfacebookid($bannerfacebookid)
    {
        $this->bannerfacebookid = $bannerfacebookid;
        return $this;
    }

    /**
    * Get nativefacebookid
    * @return  
    */
    public function getNativefacebookid()
    {
        return $this->nativefacebookid;
    }
    
    /**
    * Set nativefacebookid
    * @return $this
    */
    public function setNativefacebookid($nativefacebookid)
    {
        $this->nativefacebookid = $nativefacebookid;
        return $this;
    }

    /**
    * Get nativeadmobid
    * @return  
    */
    public function getNativeadmobid()
    {
        return $this->nativeadmobid;
    }
    
    /**
    * Set nativeadmobid
    * @return $this
    */
    public function setNativeadmobid($nativeadmobid)
    {
        $this->nativeadmobid = $nativeadmobid;
        return $this;
    }

    /**
    * Get interstitialfacebookid
    * @return  
    */
    public function getInterstitialfacebookid()
    {
        return $this->interstitialfacebookid;
    }
    
    /**
    * Set interstitialfacebookid
    * @return $this
    */
    public function setInterstitialfacebookid($interstitialfacebookid)
    {
        $this->interstitialfacebookid = $interstitialfacebookid;
        return $this;
    }

    /**
    * Get interstitialadmobid
    * @return  
    */
    public function getInterstitialadmobid()
    {
        return $this->interstitialadmobid;
    }
    
    /**
    * Set interstitialadmobid
    * @return $this
    */
    public function setInterstitialadmobid($interstitialadmobid)
    {
        $this->interstitialadmobid = $interstitialadmobid;
        return $this;
    }

    /**
    * Get bannertype
    * @return  
    */
    public function getBannertype()
    {
        return $this->bannertype;
    }
    
    /**
    * Set bannertype
    * @return $this
    */
    public function setBannertype($bannertype)
    {
        $this->bannertype = $bannertype;
        return $this;
    }

    /**
    * Get interstitialtype
    * @return  
    */
    public function getInterstitialtype()
    {
        return $this->interstitialtype;
    }
    
    /**
    * Set interstitialtype
    * @return $this
    */
    public function setInterstitialtype($interstitialtype)
    {
        $this->interstitialtype = $interstitialtype;
        return $this;
    }

    /**
    * Get nativetype
    * @return  
    */
    public function getNativetype()
    {
        return $this->nativetype;
    }
    
    /**
    * Set nativetype
    * @return $this
    */
    public function setNativetype($nativetype)
    {
        $this->nativetype = $nativetype;
        return $this;
    }

    /**
    * Get interstitialclick
    * @return  
    */
    public function getInterstitialclick()
    {
        return $this->interstitialclick;
    }
    
    /**
    * Set interstitialclick
    * @return $this
    */
    public function setInterstitialclick($interstitialclick)
    {
        $this->interstitialclick = $interstitialclick;
        return $this;
    }

    /**
    * Get nativeitem
    * @return  
    */
    public function getNativeitem()
    {
        return $this->nativeitem;
    }
    
    /**
    * Set nativeitem
    * @return $this
    */
    public function setNativeitem($nativeitem)
    {
        $this->nativeitem = $nativeitem;
        return $this;
    }

    /**
    * Get rewardedadmobid
    * @return  
    */
    public function getRewardedadmobid()
    {
        return $this->rewardedadmobid;
    }
    
    /**
    * Set rewardedadmobid
    * @return $this
    */
    public function setRewardedadmobid($rewardedadmobid)
    {
        $this->rewardedadmobid = $rewardedadmobid;
        return $this;
    }

    /**
    * Get nativebannerfacebookid
    * @return  
    */
    public function getNativebannerfacebookid()
    {
        return $this->nativebannerfacebookid;
    }
    
    /**
    * Set nativebannerfacebookid
    * @return $this
    */
    public function setNativebannerfacebookid($nativebannerfacebookid)
    {
        $this->nativebannerfacebookid = $nativebannerfacebookid;
        return $this;
    }

    /**
    * Get publisherid
    * @return  
    */
    public function getPublisherid()
    {
        return $this->publisherid;
    }
    
    /**
    * Set publisherid
    * @return $this
    */
    public function setPublisherid($publisherid)
    {
        $this->publisherid = $publisherid;
        return $this;
    }

    /**
    * Get appid
    * @return  
    */
    public function getAppid()
    {
        return $this->appid;
    }
    
    /**
    * Set appid
    * @return $this
    */
    public function setAppid($appid)
    {
        $this->appid = $appid;
        return $this;
    }
}
