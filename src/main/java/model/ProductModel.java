package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import entities.Product;
import view.ModelObserver;

/**
 * Klasa odpowiadająca za komunikację z bazą oraz informowanie obserwujących o zmianie jej stanu.
 * Implementuje interfejs „ModelInterface”
 */
public class ProductModel implements ModelInterface{
	private Session session;
	private ArrayList<ModelObserver> observers;
	
	/**
	 * Konstruktor bezparametrowy powoduje zainicjalizowanie listy obserwujących.
	 */
	public ProductModel(){
		observers = new ArrayList<ModelObserver>();
	}
	
	/**
	 * pobiera Produkt  z bazy, z wykorzystaniem identyfikatora code
	 * 
	 * @param code identyfikator
	 * @return Product- instancję klasy Produktu
	 */
	public Product getProductByCode(String code){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Product product = (Product) session.load(Product.class, code);
		session.getTransaction().commit();
		return product;
		
	}
	/**
	 * Pobiera listę(ArrayList) wszystkich Encji z bazy
	 * 
	 * @return ArrayList of OrderProducts
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Product> getProducts(){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Product> lista = session.createQuery("from Product").list();
		session.getTransaction().commit();
		return (ArrayList<Product>)lista;
		
	}
	/**
	 * Dodaje nowy Produkt do bazy
	 * 
	 * @param product do dodania do bazy
	 */
	public void addProduct(Product product){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.persist(product);
		session.getTransaction().commit();
	}
	/**
	 * Zmienia dane Produkut w bazie
	 * 
	 * @param productOld - przed zmianami 
	 * @param productNew - po zmianach    
	 */
	public void updateProduct(Product productOld, Product productNew){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(productOld);
		productOld.setCode(productNew.getCode());
		productOld.setManufacturer(productNew.getManufacturer());
		productOld.setName(productNew.getName());
		productOld.setSpecification(productNew.getSpecification());
		productOld.setAmount(productNew.getAmount());
		productOld.setPrice(productNew.getPrice());
		productOld.setCategory(productNew.getCategory());
		productOld.setWeight(productNew.getWeight());
		session.getTransaction().commit();
	}
	/**
	 * usuwa z bazy wpis
	 * 
	 * @param product do usunięcia z bazy
	 */
	public void removeProduct(Product product){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Product p = (Product) session.load(Product.class, product.getCode());
		if(null != p){
			session.delete(p);
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
