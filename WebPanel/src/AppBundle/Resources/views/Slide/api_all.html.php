<?php
$list = array();
foreach ($slides as $key => $slide) {
	$s = null;
	$s["id"] = $slide->getId();
	$s["title"] = $slide->getTitle();
	$s["type"] = $slide->getType();
	$s["image"] = $this['imagine']->filter($view['assets']->getUrl($slide->getMedia()->getLink()), 'slide_thumb');
	if ($slide->getType() == 3 && $slide->getPack() != null) {
		$pack = $slide->getPack();
		$a["identifier"] = $pack->getId();
    $a["name"] = $pack->getName();
    $a["publisher"] = $pack->getPublisher();
    $a["tray_image_file"] = $this['imagine']->filter($view['assets']->getUrl($pack->getImage()->getLink()), 'tray_image');
    $a["publisher_email"] = $pack->getPublisheremail();
    $a["publisher_website"] = $pack->getPublisherwebsite();
    $a["privacy_policy_website"] = $pack->getPrivacypolicywebsite();
    $a["license_agreement_website"] = $pack->getLicenseagreementwebsite();
    $a["premium"] = $pack->getPremiumValue();
    $a["review"] = $pack->getReviewValue();
    $a["trusted"] = $pack->getUser()->getTrusedValue();
    $a["downloads"] = $pack->getDownloadValue();
    $a["size"] = $pack->getSizes();
    $a["created"] = $view['time']->diff($pack->getCreated());
    $a["user"] = $pack->getUser()->getName();
    $a["userid"] = $pack->getUser()->getId() . "";
    $a["userimage"] = $pack->getUser()->getImage();
    $stickers = array();
    foreach ($pack->getStickers() as $key => $sticker) {
        if ($sticker->getMedia()->getType()=="image/webp") {
            $s["image_file_thum"] = $app->getRequest()->getSchemeAndHttpHost()."/".$sticker->getMedia()->getLink();
            $s["image_file"] = $app->getRequest()->getSchemeAndHttpHost()."/".$sticker->getMedia()->getLink();
            $s["emojis"] = array($sticker->getEmojis());
            $stickers[] = $s;   
        }else{
            $s["image_file_thum"] = $this['imagine']->filter($view['assets']->getUrl($sticker->getMedia()->getLink()), 'sticker_image_thum');
            $s["image_file"] = $app->getRequest()->getSchemeAndHttpHost()."/".$sticker->getMedia()->getLink();
            $s["emojis"] = array($sticker->getEmojis());
            $stickers[] = $s;           
        }
    }
    $a["stickers"] = $stickers;
		$s["pack"] = $a;
	} elseif ($slide->getType() == 1 && $slide->getCategory() != null) {
		$c["id"] = $slide->getCategory()->getId();
		$c["title"] = $slide->getCategory()->getTitle();
		$c["image"] = $this['imagine']->filter($view['assets']->getUrl($slide->getCategory()->getMedia()->getLink()), 'category_thumb_api');
		$s["category"] = $c;
	} elseif ($slide->getType() == 2 && $slide->getUrl() != null) {
		$s["url"] = $slide->getUrl();
	}
	$list[] = $s;
}
echo json_encode($list, JSON_UNESCAPED_UNICODE);

?>