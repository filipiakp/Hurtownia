package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import entities.Contractor;
import view.ModelObserver;

/**
 * Klasa odpowiadająca za komunikację z bazą oraz informowanie obserwujących o zmianie jej stanu.
 * Implementuje interfejs „ModelInterface”
 */
public class ContractorModel implements ModelInterface{
	private Session session;
	ArrayList<ModelObserver> observers;
	
	/**
	 * Konstruktor bezparametrowy powoduje zainicjalizowanie listy obserwujących.
	 */
	public ContractorModel(){
		observers = new ArrayList<ModelObserver>();
	}
	
	/**
	 * pobiera Kontrahenta z bazy, z wykorzystaniem identyfikatora nip
	 * 
	 * @param nip identyfikator
	 * @return Contractor- instancję klasy Kontrahenta 
	 */
	public Contractor getContractorByNip(String nip){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Contractor Contractor = (Contractor) session.load(Contractor.class, nip);
		session.getTransaction().commit();
		return Contractor;
		
	}
	/**
	 * Pobiera listę(ArrayList) wszystkich Encji z bazy
	 * 
	 * @return ArrayList of Contractors 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Contractor> getContractors(){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Contractor> list = session.createQuery("from Contractor").list();
		session.getTransaction().commit();
		return (ArrayList<Contractor>)list;
		
	}
	/**
	 * Dodaje nowego Kontrahenta do bazy
	 * 
	 * @param contractor- klasy Contractor
	 */
	public void addContractor(Contractor contractor){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.persist(contractor);
		session.getTransaction().commit();
	}
	/**
	 * Zmienia dane istniejącego Kontrahenta w bazie
	 * 
	 * @param contractorOld - przed zmianami
	 * @param contractorNew - po zmianach
	 */
	public void updateContractor(Contractor contractorOld, Contractor contractorNew){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(contractorOld);
		contractorOld.setNip(contractorNew.getNip());
		contractorOld.setName(contractorNew.getName());
		contractorOld.setPhoneNumber(contractorNew.getPhoneNumber());
		contractorOld.setCity(contractorNew.getCity());
		contractorOld.setStreet(contractorNew.getStreet());
		contractorOld.setHouseNumber(contractorNew.getHouseNumber());
		contractorOld.setApartmentNumber(contractorNew.getApartmentNumber());
		contractorOld.setSupplier(contractorNew.isSupplier());
		session.getTransaction().commit();
	}
	/**
	 * usuwa Kontrahenta
	 * 
	 * @param contractor do usunięcia z bazy
	 */
	public void removeContractor(Contractor contractor){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Contractor k = (Contractor) session.load(Contractor.class, contractor.getNip());
		if(null != k){
			session.delete(k);
		}
		session.getTransaction().commit();
	}
	
	@Override
	public void addObserver(ModelObserver observer) {
		observers.add(observer);
	}
	@Override
	public void removeObserver(ModelObserver observer) {
		observers.remove(observer);
	}
	@Override
	public void notifyAllObservers() {
		for(ModelObserver mo: observers){
			mo.update();
		}
		
	}
}
