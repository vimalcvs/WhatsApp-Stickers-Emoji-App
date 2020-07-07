<?php

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use MediaBundle\Entity\Media;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Sticker
 *
 * @ORM\Table(name="stickers_table")
 * @ORM\Entity(repositoryClass="AppBundle\Repository\StickerRepository")
 */
class Sticker {
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
	 * @ORM\Column(name="emojis", type="text")
	 */
	private $emojis;

	/**
	 * @var string
	 * @ORM\Column(name="size", type="integer")
	 */
	private $size;

	/**
	 * @ORM\Column(name="position", type="integer",nullable = true)
	 */
	private $position;

	/**
	 * @ORM\ManyToOne(targetEntity="MediaBundle\Entity\Media")
	 * @ORM\JoinColumn(name="media_id", referencedColumnName="id")
	 * @ORM\JoinColumn(nullable=false)
	 */
	private $media;

	/**
	 * @Assert\File(mimeTypes={"image/png","image/webp" },maxSize="40M")
	 */
	private $file;

	/**
	 * @ORM\ManyToOne(targetEntity="Pack", inversedBy="stickers")
	 * @ORM\JoinColumn(name="pack_id", referencedColumnName="id", nullable=true)
	 */
	private $pack;

	public function __construct() {
		$this->size = 0;
		$this->emojis = "";
	}

	/**
	 * Get id
	 * @return
	 */
	public function getId() {
		return $this->id;
	}

	/**
	 * Set id
	 * @return $this
	 */
	public function setId($id) {
		$this->id = $id;
		return $this;
	}

	/**
	 * Get emojis
	 * @return
	 */
	public function getEmojis() {
		return $this->emojis;
	}

	/**
	 * Set emojis
	 * @return $this
	 */
	public function setEmojis($emojis) {
		$this->emojis = $emojis;
		return $this;
	}

	/**
	 * Set media
	 *
	 * @param string $media
	 * @return Wallpaper
	 */
	public function setMedia(Media $media) {
		$this->media = $media;

		return $this;
	}

	/**
	 * Get media
	 *
	 * @return string
	 */
	public function getMedia() {
		return $this->media;
	}

	/**
	 * Get file
	 * @return
	 */
	public function getFile() {
		return $this->file;
	}

	/**
	 * Set file
	 * @return $this
	 */
	public function setFile($file) {
		$this->file = $file;
		return $this;
	}

	/**
	 * Get pack
	 * @return
	 */
	public function getPack() {
		return $this->pack;
	}

	/**
	 * Set pack
	 * @return $this
	 */
	public function setPack(Pack $pack) {
		$this->pack = $pack;
		return $this;
	}
	/**
	 * Get position
	 * @return
	 */
	public function getPosition() {
		return $this->position;
	}

	/**
	 * Set position
	 * @return $this
	 */
	public function setPosition($position) {
		$this->position = $position;
		return $this;
	}
	/**
	 * Get size
	 * @return
	 */
	public function getSize() {
		return $this->size;
	}

	/**
	 * Set size
	 * @return $this
	 */
	public function setSize($size) {
		$this->size = $size;
		return $this;
	}
}
