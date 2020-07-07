<?php 
$list=array();
foreach ($comments as $key => $comment) {
	$a["id"]=$comment->getId();
	$a["user"]=$comment->getUser()->getName();
	$a["image"]=$comment->getUser()->getImage();
	$a["content"]=$comment->getContent();
	$a["enabled"]=$comment->getEnabled();
	$a["created"]=$view['time']->diff($comment->getCreated());
	$list[]=$a;
}
echo json_encode($list, JSON_UNESCAPED_UNICODE);
?>