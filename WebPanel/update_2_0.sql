ALTER TABLE settings_table 
ADD rewardedadmobid VARCHAR(255) DEFAULT NULL, 
ADD banneradmobid VARCHAR(255) DEFAULT NULL, 
ADD bannerfacebookid VARCHAR(255) DEFAULT NULL, 
ADD nativebannerfacebookid VARCHAR(255) DEFAULT NULL, 
ADD bannertype VARCHAR(255) DEFAULT NULL, 
ADD nativeadmobid VARCHAR(255) DEFAULT NULL, 
ADD nativefacebookid VARCHAR(255) DEFAULT NULL, 
ADD nativeitem INT DEFAULT NULL, 
ADD nativetype VARCHAR(255) DEFAULT NULL, 
ADD interstitialadmobid VARCHAR(255) DEFAULT NULL, 
ADD interstitialfacebookid VARCHAR(255) DEFAULT NULL, 
ADD interstitialtype VARCHAR(255) DEFAULT NULL, 
ADD publisherid VARCHAR(255) DEFAULT NULL,
ADD appid VARCHAR(255) DEFAULT NULL,
ADD interstitialclick INT DEFAULT NULL;
_________
src/AppBundle/Controller/HomeController.php
src/AppBundle/Controller/VersionController.php

src/AppBundle/Entity/Settings.php
src/AppBundle/Form/AdsType.php

src/AppBundle/Resources/views/Home/ads.html.twig
src/AppBundle/Resources/views/Home/settings.html.twig
src/AppBundle/Resources/views/Pack/view.html.twig

src/AppBundle/Resources/config/routing.yml
src/UserBundle/Controller/UserController.php
src/UserBundle/Entity/User.php

src/UserBundle/Resources/views/ChangePassword/changePassword_content.html.twig
src/UserBundle/Resources/views/User/index.html.twig

public_html/css/demo.css
public_html/img/mobile.png
