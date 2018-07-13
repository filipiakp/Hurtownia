package entities;


import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Klasa przedstawiająca encję produktu. Posiada następujące właściwości:
 * code-6 cyfrowy kod
 * manufacturer- producent
 * name- nazwa
 * specification- specyfikacja produktu, miejsce na szczegółowe dane
 * amount- ilość produktu na magazynie
 * price- cena za sztukę
 * category- kategoria, dział do którego należy na magazynie
 * weight- waga jednej sztuki, podawana w kilogramach, z dokładnością do 3 miejsc po przecinku(gram)
 *
 */
@Entity
public class Product {

	@Id
	private String code;//6 cyfrowy code
	private String manufacturer;
	private String name;
	private String specification;
	private long amount;//na magazynie
	private double price;
	private String category;
	private double weight;
	
	
	public Product() {
		super();
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the specification
	 */
	public String getSpecification() {
		return specification;
	}
	/**
	 * @param specification the specification to set
	 */
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	/**
	 * @return the amount
	 */
	public long getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(long amount) {
		this.amount = amount;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	@Override
	public String toString(){
		return code+" "+name+" "+price;
	}
	
}
