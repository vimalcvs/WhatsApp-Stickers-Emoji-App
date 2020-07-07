<?php

namespace AppBundle\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\ORM\Mapping as ORM;
use MediaBundle\Entity\Media;
use Symfony\Component\Validator\Constraints as Assert;
use UserBundle\Entity\User;

/**
 * Pack
 *
 * @ORM\Table(name="packs_table")
 * @ORM\Entity(repositoryClass="AppBundle\Repository\PackRepository")
 */
class Pack {
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
	 * @ORM\Column(name="name", type="string", length=255)
	 * @Assert\NotBlank()
	 * @Assert\Length(
	 *     min = 3,
	 *     max = 50
	 * )
	 */
	private $name;

	/**
	 * @var string
	 *
	 * @ORM\Column(name="publisher", type="string", length=255)
	 * @Assert\NotBlank()
	 * @Assert\Length(
	 *     min = 3,
	 *     max = 50
	 * )
	 */
	private $publisher;

	/**
	 * @ORM\ManyToOne(targetEntity="MediaBundle\Entity\Media")
	 * @ORM\JoinColumn(name="media_id", referencedColumnName="id")
	 * @ORM\JoinColumn(nullable=false)
	 */
	private $image;

	/**
	 * @var string
	 *
	 * @ORM\Column(name="publisheremail", type="string", length=255, nullable=true)
	 * @Assert\Email()
	 */
	private $publisheremail;

	/**
	 * @var string
	 *
	 * @ORM\Column(name="publisherwebsite", type="string", length=255, nullable=true)
	 * @Assert\Url()
	 */
	private $publisherwebsite;

	/**
	 * @var string
	 *
	 * @ORM\Column(name="privacypolicywebsite", type="string", length=255, nullable=true)
	 */
	private $privacypolicywebsite;

	/**
	 * @var string
	 *
	 * @ORM\Column(name="licenseagreementwebsite", type="string", length=255, nullable=true)
	 */
	private $licenseagreementwebsite;

	/**
	 * @var string
	 *
	 * @ORM\Column(name="tags", type="text",nullable = true)
	 */
	private $tags;
	/**
	 * @var string
	 *
	 * @ORM\Column(name="size", type="integer")
	 */
	private $size;
	/**
	 * @var bool
	 *
	 * @ORM\Column(name="enabled", type="boolean")
	 */
	private $enabled;

	/**
	 * @var bool
	 *
	 * @ORM\Column(name="review", type="boolean")
	 */
	private $review;

	/**
	 * @var bool
	 *
	 * @ORM\Column(name="premium", type="boolean")
	 */
	private $premium;

	/**
	 * @ORM\Column(name="downloads", type="integer")
	 */
	private $downloads;

	/**
	 * @ORM\Column(name="created", type="datetime")
	 */
	private $created;

	/**
	 * @ORM\OneToMany(targetEntity="AppBundle\Entity\Sticker", mappedBy="pack")
	 * @ORM\OrderBy({"position" = "asc"})
	 */
	private $stickers;

	/**
	 * @ORM\ManyToMany(targetEntity="Category")
	 * @ORM\JoinTable(name="packs_categories_table",
	 *      joinColumns={@ORM\JoinColumn(name="pack_id", referencedColumnName="id",onDelete="CASCADE")},
	 *      inverseJoinColumns={@ORM\JoinColumn(name="category_id", referencedColumnName="id",onDelete="CASCADE")},
	 *      )
	 */
	private $categories;

	/**
	 * @ORM\OneToMany(targetEntity="Rate", mappedBy="pack",cascade={"persist","remove"})
	 * @ORM\OrderBy({"created" = "desc"})
	 */
	private $rates;

	/**
	 * @ORM\ManyToMany(targetEntity="Tag")
	 * @ORM\JoinTable(name="packs_tags_table",
	 *      joinColumns={@ORM\JoinColumn(name="pack_id", referencedColumnName="id",onDelete="CASCADE")},
	 *      inverseJoinColumns={@ORM\JoinColumn(name="tag_id", referencedColumnName="id",onDelete="CASCADE")},
	 *      )
	 */
	private $tagslist;

	/**
	 * @ORM\ManyToOne(targetEntity="UserBundle\Entity\User",inversedBy="packs")
	 * @ORM\JoinColumn(name="user_id", referencedColumnName="id",nullable=false)
	 */
	private $user;
	/**
	 * @Assert\File(mimeTypes={"image/png" },maxSize="40M")
	 */
	private $file;
	/**
	 * @var ArryCollection
	 */
	private $files;
	public function __construct() {
		$this->review = false;
		$this->size = 0;
		$this->downloads = 0;
		$this->created = new \DateTime();
		$this->categories = new ArrayCollection();
		$this->publisher = "";

	}

	/**
	 * Get id
	 *
	 * @return integer
	 */
	public function getId() {
		return $this->id;
	}

	/**
	 * Set name
	 *
	 * @param string $name
	 * @return Pack
	 */
	public function setName($name) {
		$this->name = $name;

		return $this;
	}

	/**
	 * Get name
	 *
	 * @return string
	 */
	public function getName() {
		return $this->name;
	}

	/**
	 * Set publisher
	 *
	 * @param string $publisher
	 * @return Pack
	 */
	public function setPublisher($publisher) {
		$this->publisher = $publisher;

		return $this;
	}

	/**
	 * Get publisher
	 *
	 * @return string
	 */
	public function getPublisher() {
		return $this->publisher;
	}

	/**
	 * Set image
	 *
	 * @param string $image
	 * @return Pack
	 */
	public function setImage($image) {
		$this->image = $image;

		return $this;
	}

	/**
	 * Get image
	 *
	 * @return string
	 */
	public function getImage() {
		return $this->image;
	}

	/**
	 * Set publisheremail
	 *
	 * @param string $publisheremail
	 * @return Pack
	 */
	public function setPublisheremail($publisheremail) {
		$this->publisheremail = $publisheremail;

		return $this;
	}

	/**
	 * Get publisheremail
	 *
	 * @return string
	 */
	public function getPublisheremail() {
		return $this->publisheremail;
	}

	/**
	 * Set publisherwebsite
	 *
	 * @param string $publisherwebsite
	 * @return Pack
	 */
	public function setPublisherwebsite($publisherwebsite) {
		$this->publisherwebsite = $publisherwebsite;

		return $this;
	}

	/**
	 * Get publisherwebsite
	 *
	 * @return string
	 */
	public function getPublisherwebsite() {
		return $this->publisherwebsite;
	}

	/**
	 * Set privacypolicywebsite
	 *
	 * @param string $privacypolicywebsite
	 * @return Pack
	 */
	public function setPrivacypolicywebsite($privacypolicywebsite) {
		$this->privacypolicywebsite = $privacypolicywebsite;

		return $this;
	}

	/**
	 * Get privacypolicywebsite
	 *
	 * @return string
	 */
	public function getPrivacypolicywebsite() {
		return $this->privacypolicywebsite;
	}

	/**
	 * Set licenseagreementwebsite
	 *
	 * @param string $licenseagreementwebsite
	 * @return Pack
	 */
	public function setLicenseagreementwebsite($licenseagreementwebsite) {
		$this->licenseagreementwebsite = $licenseagreementwebsite;

		return $this;
	}

	/**
	 * Get licenseagreementwebsite
	 *
	 * @return string
	 */
	public function getLicenseagreementwebsite() {
		return $this->licenseagreementwebsite;
	}

	/**
	 * Set enabled
	 *
	 * @param boolean $enabled
	 * @return Pack
	 */
	public function setEnabled($enabled) {
		$this->enabled = $enabled;

		return $this;
	}

	/**
	 * Get enabled
	 *
	 * @return boolean
	 */
	public function getEnabled() {
		return $this->enabled;
	}

	/**
	 * Get stickers
	 * @return
	 */
	public function getStickers() {
		return $this->stickers;
	}

	/**
	 * Set stickers
	 * @return $this
	 */
	public function setStickers($stickers) {
		$this->stickers = $stickers;
		return $this;
	}

	public function addSticker(Sticker $sticker) {
		$this->stickers[] = $sticker;
		return $this;
	}

	public function removeSticker(Sticker $sticker) {
		$this->stickers->removeElement($sticker);
	}

	/**
	 * Get getDownloadValue
	 * @return
	 */
	public function getDownloadValue() {
		return $this->getNumbers($this->downloads);
	}
	/**
	 * Get downloads
	 * @return
	 */
	public function getDownloads() {
		return $this->downloads;
	}

	/**
	 * Set downloads
	 * @return $this
	 */
	public function setDownloads($downloads) {
		$this->downloads = $downloads;
		return $this;
	}

	/**
	 * Get rates
	 * @return
	 */
	public function getRates() {
		return $this->rates;
	}

	/**
	 * Set rates
	 * @return $this
	 */
	public function setRates($rates) {
		$this->rates = $rates;
		return $this;
	}
	/**
	 * Get user
	 * @return
	 */
	public function getUser() {
		return $this->user;
	}

	/**
	 * Set user
	 * @return $this
	 */
	public function setUser(User $user) {
		$this->user = $user;
		return $this;
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
	 * Get tags
	 * @return
	 */
	public function getTags() {
		return $this->tags;
	}

	/**
	 * Set tags
	 * @return $this
	 */
	public function setTags($tags) {
		$this->tags = $tags;
		return $this;
	}

	/**
	 * Get premium
	 * @return
	 */
	public function getPremiumValue() {
		if ($this->premium) {
			return "true";
		} else {
			return "false";
		}
	}
	/**
	 * Get premium
	 * @return
	 */
	public function getPremium() {
		return $this->premium;
	}

	/**
	 * Set premium
	 * @return $this
	 */
	public function setPremium($premium) {
		$this->premium = $premium;
		return $this;
	}

	/**
	 * add Category
	 * @return
	 */
	public function addCategory(Category $category) {
		$this->categories[]=$category;
	}
	/**
	 * Get categories
	 * @return
	 */
	public function getCategories() {
		return $this->categories;
	}

	/**
	 * Set categories
	 * @return $this
	 */
	public function setCategories($categories) {
		$this->categories = $categories;
		return $this;
	}
	/**
	 * Get files
	 * @return
	 */
	public function getFiles() {
		return $this->files;
	}

	/**
	 * Set files
	 * @return $this
	 */
	public function setFiles($files) {
		$this->files = $files;
		return $this;
	}
	/**
	 * Get review
	 * @return
	 */
	public function getReview() {
		return $this->review;
	}

	/**
	 * Set review
	 * @return $this
	 */
	public function setReview($review) {
		$this->review = $review;
		return $this;
	}
	/**
	 * Get review
	 * @return
	 */
	public function getReviewValue() {
		if ($this->review) {
			return "true";
		} else {
			return "false";
		}
	}
	/**
	 * Add tags
	 *
	 * @param Wallpaper $tags
	 * @return tag
	 */
	public function addTagslist(Tag $tagslist) {
		$this->tagslist[] = $tagslist;

		return $this;
	}

	/**
	 * Remove tags
	 *
	 * @param tag $tags
	 */
	public function removeTagslist(Tag $tagslist) {
		$this->tagslist->removeElement($tagslist);
	}

	/**
	 * Get tags
	 *
	 * @return \Doctrine\Common\Collections\Collection
	 */
	public function getTagslist() {
		return $this->tagslist;
	}
	/**
	 * Get tags
	 *
	 * @return \Doctrine\Common\Collections\Collection
	 */
	public function setTagslist($tagslist) {
		return $this->tagslist = $tagslist;
	}

	/**
	 * Get created
	 * @return
	 */
	public function getCreated() {
		return $this->created;
	}

	/**
	 * Set created
	 * @return $this
	 */
	public function setCreated($created) {
		$this->created = $created;
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
	public function getSizes() {
		if ($this->size < 1000) {
			return $this->size . ' B';
		} else {
			$this->size = $this->size / 1000;
			$units = ['KB', 'MB', 'GB', 'TB'];
			foreach ($units as $unit) {
				if (round($this->size, 2) >= 1000) {
					$this->size = $this->size / 1000;
				} else {
					break;
				}
			}
			return round($this->size, 0) . ' ' . $unit;
		}
	}
	public function getNumbers($value) {
		if ($value < 1000) {
			return $value . '';
		} else {
			$value = $value / 1000;
			$units = ['K', 'M', 'B', 'T'];
			foreach ($units as $unit) {
				if (round($value, 2) >= 1000) {
					$value = $value / 1000;
				} else {
					break;
				}
			}
			return round($value, 2) . ' ' . $unit;
		}
	}
	public function __toString() {
		return $this->getName();
	}
}
