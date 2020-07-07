<?php 
namespace AppBundle\Entity;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use UserBundle\Entity\User;

/**
 * Rate
 *
 * @ORM\Table(name="rate_table")
 * @ORM\Entity(repositoryClass="AppBundle\Repository\RateRepository")
 */
class Rate
{
	/**
	 *
	 * @ORM\Id()
	 * @ORM\Column(type="integer")
	 * @ORM\GeneratedValue(strategy="AUTO")
	*/
	protected $id;

	/**
	* @ORM\Column(name="value", type="integer")
	*/
	private $value;

	/**
	* @ORM\Column(name="review", type="string", length=255)
	*/
	private $review;

	/**
	 * @ORM\Column(name="created", type="datetime")
	 */
	 private $created;


	 /**
	  * @ORM\ManyToOne(targetEntity="AppBundle\Entity\Pack",inversedBy="rates")
	  * @ORM\JoinColumn(name="pack_id", referencedColumnName="id",nullable=false)
	  */
	  private $pack;

	 /**
	  * @ORM\ManyToOne(targetEntity="UserBundle\Entity\User",inversedBy="rates")
	  * @ORM\JoinColumn(name="user_id", referencedColumnName="id",nullable=false)
	  */
	  private $user;
	 public function __construct()
	 {
	 	$this->created =  new \DateTime();
	 }

	/**
	* Get id
	* @return  
	*/
	public function getId()
	{
		return $this->id;
	}

	/**
	* Set id
	* @return $this
	*/
	public function setId($id)
	{
		$this->id = $id;
		return $this;
	}

	/**
	* Get value
	* @return  
	*/
	public function getValue()
	{
	    return $this->value;
	}
	
	/**
	* Set value
	* @return $this
	*/
	public function setValue($value)
	{
	    $this->value = $value;
	    return $this;
	}

	/**
	* Get review
	* @return  
	*/
	public function getReview()
	{
	    return $this->review;
	}
	
	/**
	* Set review
	* @return $this
	*/
	public function setReview($review)
	{
	    $this->review = $review;
	    return $this;
	}

	/**
	* Get created
	* @return  
	*/
	public function getCreated()
	{
	    return $this->created;
	}
	
	/**
	* Set created
	* @return $this
	*/
	public function setCreated($created)
	{
	    $this->created = $created;
	    return $this;
	}
	/**
	* Get pack
	* @return  
	*/
	public function getPack()
	{
	    return $this->pack;
	}
	
	/**
	* Set pack
	* @return $this
	*/
	public function setPack(Pack $pack)
	{
	    $this->pack = $pack;
	    return $this;
	}
	/**
	* Get user
	* @return  
	*/
	public function getUser()
	{
	    return $this->user;
	}
	
	/**
	* Set user
	* @return $this
	*/
	public function setUser(User $user)
	{
	    $this->user = $user;
	    return $this;
	}
}
 ?>