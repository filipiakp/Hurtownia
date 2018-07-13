package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

/**
 * Klasa przedstawiająca encję kontrahenta. Posiada następujące właściwości:
 * nip- podstawowy identyfikator, dziesięciocyfrowy
 * name – nazwa
 * phoneNumber- numer telefonu kontrahenta
 * city- miasto
 * street- ulica
 * houseNumber- nr domu
 * apartmentNumber- nr mieszkania/lokalu
 * isSupplier- czy jest dostawcą
 * 
 */
@Entity
public class Contractor {
	
	@Id
	private String nip;
	private String name;
	private String phoneNumber;
	private String city;
	private String street;
	private String houseNumber;
	@Column(nullable=true)
	private String apartmentNumber;
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isSupplier;//dostawcy lub klienci
	
	public Contractor() {
		super();
	}
	/**
	 * @return the nip
	 */
	public String getNip() {
		return nip;
	}
	/**
	 * @param nip the nip to set
	 */
	public void setNip(String nip) {
		this.nip = nip;
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
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * @return the houseNumber
	 */
	public String getHouseNumber() {
		return houseNumber;
	}
	/**
	 * @param houseNumber the houseNumber to set
	 */
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	/**
	 * @return the apartmentNumber
	 */
	public String getApartmentNumber() {
		return apartmentNumber;
	}
	/**
	 * @param apartmentNumber the apartmentNumber to set
	 */
	public void setApartmentNumber(String apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}
	/**
	 * @return the isSupplier
	 */
	public boolean isSupplier() {
		return isSupplier;
	}
	/**
	 * @param isSupplier the isSupplier to set
	 */
	public void setSupplier(boolean isSupplier) {
		this.isSupplier = isSupplier;
	}
	
	
	
}
