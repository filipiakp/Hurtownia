package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import entities.PurchaseOrder;
import view.ModelObserver;

/**
 * Klasa odpowiadająca za komunikację z bazą oraz informowanie obserwujących o zmianie jej stanu.
 * Implementuje interfejs „ModelInterface”
 */
public class OrderModel implements ModelInterface{
	private Session session;
	private ArrayList<ModelObserver> observers;
	
	/**
	 * Konstruktor bezparametrowy powoduje zainicjalizowanie listy obserwujących.
	 */
	public OrderModel(){
		observers = new ArrayList<ModelObserver>();
	}
	
	/**
	 * pobiera Zamówienie z bazy, z wykorzystaniem identyfikatora id
	 * 
	 * @param id identyfikator
	 * @return PurchaseOrder- instancję klasy Zamówienia 
	 */
	public PurchaseOrder getOrderById(int id){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		PurchaseOrder PurchaseOrder = (PurchaseOrder) session.load(PurchaseOrder.class, new Integer(id));
		session.getTransaction().commit();
		return PurchaseOrder;
		
	}
	/**
	 * Pobiera listę(ArrayList) wszystkich Encji z bazy
	 * 
	 * @return ArrayList of PurchaseOrders
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<PurchaseOrder> getOrders(){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<PurchaseOrder> lista = session.createQuery("from PurchaseOrder").list();
		session.getTransaction().commit();
		return (ArrayList<PurchaseOrder>)lista;
		
	}
	/**
	 * Dodaje nowe Zamówienie
	 * 
	 * @param purchaseOrder do dodania do bazy 
	 */
	public void addOrder(PurchaseOrder purchaseOrder){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.persist(purchaseOrder);
		session.getTransaction().commit();
	}
	/**
	 * Zmienia dane Zamówienie w bazie
	 * 
	 * @param orderOld- przed zmianami 
	 * @param orderNew- po zmianach    
	 */
	public void updateOrder(PurchaseOrder orderOld, PurchaseOrder orderNew){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(orderOld);
		orderOld.setOrderDate(orderNew.getOrderDate());
		orderOld.setProductsList(orderNew.getProductsList());
		orderOld.setContractor(orderNew.getContractor());
		session.getTransaction().commit();
	}
	/**
	 * usuwa z bazy wpis
	 * 
	 * @param purchaseOrder do usunięcia z bazy
	 */
	public void removeOrder(PurchaseOrder purchaseOrder){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		PurchaseOrder o = (PurchaseOrder) session.load(PurchaseOrder.class, new Long(purchaseOrder.getOrderId()));
		if(null != o){
			session.delete(o);
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
