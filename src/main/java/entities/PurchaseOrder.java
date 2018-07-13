package entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Klasa przedstawiająca zamówienie. Zamówienie składa się z
 * orderId- identyfikator, automatycznie generowany
 * orderDate- data realizacji zamówienia
 * productsList- lista produktów z ich ilościami(OrderProduct)- każdy element(produkt) nie może występować więcej niż raz
 * contractor- kontrahent kupujący, sprzedający- w zależności od wartości pola idSupplier(join na nip kontrahenta)
 *
 */
@Entity
public class PurchaseOrder {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="order_id")
	private long orderId;
	@Temporal(TemporalType.DATE)
	private Date orderDate;
	@OneToMany(orphanRemoval=true, fetch=FetchType.EAGER)
	private Set<OrderProduct> productsList;
	@ManyToOne
	@JoinColumn(name="nip")
	private Contractor contractor;
	
	public PurchaseOrder(){
		super();
	}
	
	/**
	 * @return the id
	 */
	public long getOrderId() {
		return orderId;
	}
	/**
	 * @param id the id to set
	 */
	public void setOrderId(long id) {
		this.orderId = id;
	}
	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}
	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	/**
	 * @return the productsList
	 */
	public Set<OrderProduct> getProductsList() {
		return productsList;
	}
	/**
	 * @param productsList the productsList to set
	 */
	public void setProductsList(Set<OrderProduct> productsList) {
		this.productsList = productsList;
	}

	/**
	 * @return the contractor
	 */
	public Contractor getContractor() {
		return contractor;
	}

	/**
	 * @param contractor the contractor to set
	 */
	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}
	
	
}
