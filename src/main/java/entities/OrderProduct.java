package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Klasa przedstawiająca połączenie informacji klasy Order oraz Product, służąca tworzeniu list produktów w Zamówieniach. Posiada następujące właściwości:
 * id- identyfikator, automatycznie generowany
 * product- pole przechowujące Produkt(Join na kod produktu)
 * numberOfProducts- ilość produktu
 *
 */
@Entity
public class OrderProduct {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@ManyToOne
	@JoinColumn( name="code")
	private Product product;
	private int numberOfProducts;
	
	public OrderProduct(){
		super();
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the produkt
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * @param product the produkt to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	/**
	 * @return the numberOfProducts
	 */
	public int getNumberOfProducts() {
		return numberOfProducts;
	}
	/**
	 * @param numberOfProducts the numberOfProducts to set
	 */
	public void setNumberOfProducts(int numberOfProducts) {
		this.numberOfProducts = numberOfProducts;
	}
	
}
