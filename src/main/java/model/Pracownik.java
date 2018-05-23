package model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Pracownik {
	
	private String imie;
	private String nazwisko;
	private String stanowisko;
	private double pensja;
	private String ulica;
	private String Miasto;
	private String nrMieszkania;
	private String nrLokalu;
	private Date dataZatrudnienia;//String?
	
	public Pracownik(){
		
	}
	/**
	 * @return the pensja
	 */
	public double getPensja() {
		return pensja;
	}

	/**
	 * @param pensja the pensja to set
	 */
	public void setPensja(double pensja) {
		this.pensja = pensja;
	}

	/**
	 * @return the ulica
	 */
	public String getUlica() {
		return ulica;
	}

	/**
	 * @param ulica the ulica to set
	 */
	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	/**
	 * @return the miasto
	 */
	public String getMiasto() {
		return Miasto;
	}

	/**
	 * @param miasto the miasto to set
	 */
	public void setMiasto(String miasto) {
		Miasto = miasto;
	}

	/**
	 * @return the nrMieszkania
	 */
	public String getNrMieszkania() {
		return nrMieszkania;
	}

	/**
	 * @param nrMieszkania the nrMieszkania to set
	 */
	public void setNrMieszkania(String nrMieszkania) {
		this.nrMieszkania = nrMieszkania;
	}

	/**
	 * @return the nrLokalu
	 */
	public String getNrLokalu() {
		return nrLokalu;
	}

	/**
	 * @param nrLokalu the nrLokalu to set
	 */
	public void setNrLokalu(String nrLokalu) {
		this.nrLokalu = nrLokalu;
	}

	/**
	 * @return the dataZatrudnienia
	 */
	public Date getDataZatrudnienia() {
		return dataZatrudnienia;
	}

	/**
	 * @param dataZatrudnienia the dataZatrudnienia to set
	 */
	public void setDataZatrudnienia(Date dataZatrudnienia) {
		this.dataZatrudnienia = dataZatrudnienia;
	}

	/**
	 * @return the imie
	 */
	public String getImie() {
		return imie;
	}

	/**
	 * @param imie the imie to set
	 */
	public void setImie(String imie) {
		this.imie = imie;
	}

	/**
	 * @return the nazwisko
	 */
	public String getNazwisko() {
		return nazwisko;
	}

	/**
	 * @param nazwisko the nazwisko to set
	 */
	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	/**
	 * @return the stanowisko
	 */
	public String getStanowisko() {
		return stanowisko;
	}

	/**
	 * @param stanowisko the stanowisko to set
	 */
	public void setStanowisko(String stanowisko) {
		this.stanowisko = stanowisko;
	}
}
