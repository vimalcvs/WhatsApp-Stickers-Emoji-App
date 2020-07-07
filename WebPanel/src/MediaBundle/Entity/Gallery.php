<?php

namespace MediaBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Gallery
 *
 * @ORM\Table(name="gallery_table")
 * @ORM\Entity(repositoryClass="MediaBundle\Repository\GalleryRepository")
 */
class Gallery
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
     * @ORM\Column(name="titre", type="string", length=255, nullable=true)
     */
    private $titre;

    /**
     * @ORM\ManyToMany(targetEntity="Media")
     * @ORM\JoinTable(name="medias_gallerys_table",
     *      joinColumns={@ORM\JoinColumn(name="gallery_id", referencedColumnName="id",onDelete="CASCADE")},
     *      inverseJoinColumns={@ORM\JoinColumn(name="media_id", referencedColumnName="id",onDelete="CASCADE")},
     *      )
     * @ORM\OrderBy({"date" = "desc"})
     */
    private $medias;
    public function __construct() {
        $this->medias = new \Doctrine\Common\Collections\ArrayCollection();
    }
    /*** G
     et id
     *
     * @return integer 
     */
    public function getId()
    {
        return $this->id;
    }
    public function addMedia(Media $media)
    {

        $this->medias[] = $media;

    }
    public function getMedias()
    {

        return $this->medias;

    }
    public function removeMedia(Media $media)
    {
        return $this->medias->removeElement($media);
    }
}
