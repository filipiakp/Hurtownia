package model;


import java.io.File;
import java.io.FileInputStream;

import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import entities.Contractor;
import entities.Employee;
import entities.OrderProduct;
import entities.Product;
import entities.PurchaseOrder;

/**
 * Jest to to klasa umożliwiająca obsługę bazy dzięki frameworkowi mapowania obiektowo-relacyjnego Hibernate.
 */
public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static Properties properties;

	/**
	 * Metoda ta konfiguruje sesję.
	 * 
	 * @return aktualną sesję
	 */
	private static SessionFactory buildSessionFactory() {
        try {
        	FileInputStream in = new FileInputStream(new File("resources/db.properties"));
        	properties = new Properties();
        	properties.load(in);
        	in.close();
        	
            Configuration configuration = new Configuration()
            		.addPackage("model")
            		.addProperties(properties)
            	    .addAnnotatedClass(Employee.class)
            	    .addAnnotatedClass(PurchaseOrder.class)
            	    .addAnnotatedClass(Contractor.class)
            	    .addAnnotatedClass(Product.class)
            	    .addAnnotatedClass(OrderProduct.class);		
            configuration.configure();
            StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
            standardServiceRegistryBuilder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = standardServiceRegistryBuilder.build();
 
            return configuration.buildSessionFactory(serviceRegistry);
        }
        catch(Exception e) {
        	JOptionPane.showMessageDialog(new JFrame(),
        		    e.getMessage(),
        		    "Błąd Połączenia",
        		    JOptionPane.ERROR_MESSAGE);
            throw new ExceptionInInitializerError(e);
        }
    }
 
    /**
     * 
     * @return bierzącą skonfigurowaną sesję
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    

}
