<?php

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use MediaBundle\Entity\Media;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Slide
 *
 * @ORM\Table(name="slide_table")
 * @ORM\Entity(repositoryClass="AppBundle\Repository\SlideRepository")
 */
class Slide {
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
	 * @ORM\Column(name="title", type="string", length=255, unique=true)
	 * @Assert\NotBlank()
	 * @Assert\Length(
	 *      min = 3,
	 *      max = 50,
	 * )
	 */
	private $title;

	/**
	 * @var string
	 *
	 * @ORM\Column(name="url", type="string", length=255, nullable=true)
	 */
	private $url;

	/**
	 * @var string
	 *
	 * @ORM\Column(name="type", type="string", length=255)
	 */
	private $type;

	/**
	 * @ORM\ManyToOne(targetEntity="Pack")
	 * @ORM\JoinColumn(name="pack_id", referencedColumnName="id", nullable=true)
	 */
	private $pack;

	/**
	 * @ORM\ManyToOne(targetEntity="Category",)
	 * @ORM\JoinColumn(name="category_id", referencedColumnName="id", nullable=true)
	 */
	private $category;
	/**
	 * Get id
	 *
	 * @return integer
	 */
	/**
	 * @ORM\ManyToOne(targetEntity="MediaBundle\Entity\Media")
	 * @ORM\JoinColumn(name="media_id", referencedColumnName="id")
	 * @ORM\JoinColumn(nullable=false)
	 */
	private $media;

	/**
	 * @var int
	 *
	 * @Assert\Range(
	 *      min = 1,
	 *      max = 10000,
	 * )
	 * @ORM\Column(name="position", type="integer", nullable=false)
	 */
	private $position;
	/**
	 * @Assert\File(mimeTypes={"image/jpeg","image/png" },maxSize="40M")
	 */
	private $file;

	public function getId() {
		return $this->id;
	}

	/**
	 * Set title
	 *
	 * @param string $title
	 * @return Slide
	 */
	public function setTitle($title) {
		$this->title = $title;
		return $this;
	}

	/**
	 * Get title
	 *
	 * @return string
	 */
	public function getTitle() {
		return $this->title;
	}

	/**
	 * Set url
	 *
	 * @param string $url
	 * @return Slide
	 */
	public function setUrl($url) {
		$this->url = $url;

		return $this;
	}

	/**
	 * Get url
	 *
	 * @return string
	 */
	public function getUrl() {
		return $this->url;
	}

	/**
	 * Set type
	 *
	 * @param string $type
	 * @return Slide
	 */
	public function setType($type) {
		$this->type = $type;

		return $this;
	}

	/**
	 * Get type
	 *
	 * @return string
	 */
	public function getType() {
		return $this->type;
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
	public function setPack($pack) {
		$this->pack = $pack;
		return $this;
	}

	/**
	 * Get category
	 * @return
	 */
	public function getCategory() {
		return $this->category;
	}

	/**
	 * Set category
	 * @return $this
	 */
	public function setCategory($category) {
		$this->category = $category;
		return $this;
	}
	/**
	 * Set media
	 *
	 * @param string $media
	 * @return ringtone
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
	 * Set clear
	 * @return $this
	 */
	public function getClear() {
		return base64_decode($this->title);
	}

	public function getFile() {
		return $this->file;
	}
	public function setFile($file) {
		$this->file = $file;
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
}