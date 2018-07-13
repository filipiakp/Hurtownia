package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import entities.OrderProduct;
import view.ModelObserver;

/**
 * Klasa odpowiadająca za komunikację z bazą oraz informowanie obserwujących o zmianie jej stanu.
 * Reprezentuje połączenie Produktu z Zamówieniem z dodatkową informacją o ilości produktów.
 * Implementuje interfejs „ModelInterface”
 */
public class OrderProductModel implements ModelInterface{
	private Session session;
	private ArrayList<ModelObserver> observers;
	
	/**
	 * Konstruktor bezparametrowy powoduje zainicjalizowanie listy obserwujących.
	 */
	public OrderProductModel(){
		observers = new ArrayList<ModelObserver>();
	}
	
	/**
	 * pobiera Zamówienie z bazy, z wykorzystaniem identyfikatora id
	 * 
	 * @param id identyfikator
	 * @return OrderProduct- instancję klasy ZamówienieProdukt 
	 */
	public OrderProduct getOrderProductById(long id){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		OrderProduct orderProduct = (OrderProduct) session.load(OrderProduct.class, new Long(id));
		session.getTransaction().commit();
		return orderProduct;
		
	}
	
	/**
	 * Pobiera listę(ArrayList) wszystkich Encji z bazy
	 * 
	 * @return ArrayList of OrderProducts
	 */
	@SuppressWarnings("unchecked")
	public Set<OrderProduct> getOrderProducts(){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<OrderProduct> list = session.createQuery("from OrderProduct").list();
		session.getTransaction().commit();
		return (Set<OrderProduct>)list;
		
	}
	
	/**
	 * Dodaje nowe ZamówienieProdukt
	 * 
	 * @param orderProduct do dodania do bazy
	 */
	public void addOrderProduct(OrderProduct orderProduct){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.persist(orderProduct);
		session.getTransaction().commit();
	}
	
	/**
	 * Zmienia dane ZamówienieProdukt w bazie
	 * 
	 * @param orderProductOld - przed zmianami 
	 * @param orderProductNew - po zmianach    
	 */
	public void updateOrderProduct(OrderProduct orderProductOld, OrderProduct orderProductNew){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(orderProductOld);
		orderProductOld.setProduct(orderProductNew.getProduct());
		orderProductOld.setNumberOfProducts(orderProductNew.getNumberOfProducts());
		session.getTransaction().commit();
	}
	
	/**
	 * usuwa z bazy wpis
	 * 
	 * @param orderProduct do usunięcia z bazy
	 */
	public void removeOrderProduct(OrderProduct orderProduct){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		OrderProduct o = (OrderProduct) session.load(OrderProduct.class, new Long(orderProduct.getId()));
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
