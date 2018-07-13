package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import entities.Employee;
import view.ModelObserver;

/**
 * Klasa odpowiadająca za komunikację z bazą oraz informowanie obserwujących o zmianie jej stanu.
 * Implementuje interfejs „ModelInterface”
 */
public class EmployeeModel implements ModelInterface{
	private Session session;
	private ArrayList<ModelObserver> observers;
	
	/**
	 * Konstruktor bezparametrowy powoduje zainicjalizowanie listy obserwujących.
	 */
	public EmployeeModel(){
		observers = new ArrayList<ModelObserver>();
	}
	
	/**
	 * pobiera Pracownika z bazy, z wykorzystaniem identyfikatora id
	 * 
	 * @param id identyfikator
	 * @return Employee- instancję klasy Pracownika
	 */
	public Employee getEmployeeById(int id){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Employee employee = (Employee) session.load(Employee.class, new Integer(id));
		session.getTransaction().commit();
		return employee;
		
	}
	/**
	 * Pobiera listę(ArrayList) wszystkich Encji z bazy
	 * 
	 * @return ArrayList of Employees
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Employee> getEmployees(){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Employee> list = session.createQuery("from Employee").list();
		session.getTransaction().commit();
		return (ArrayList<Employee>)list;
		
	}
	/**
	 * Dodaje nowego pracownika
	 * 
	 * @param employee do dodania do bazy
	 */
	public void addEmployee(Employee employee){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.persist(employee);
		session.getTransaction().commit();
	}
	/**
	 * Zmienia dane istniejącego Pracownika w bazie
	 * 
	 * @param employeeOld - przed zmianami
	 * @param employeeNew - po zmianach
	 */
	public void updateEmployee(Employee employeeOld, Employee employeeNew){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(employeeOld);
		employeeOld.setName(employeeNew.getName());
		employeeOld.setSurname(employeeNew.getSurname());
		employeeOld.setPosition(employeeNew.getPosition());
		employeeOld.setSalary(employeeNew.getSalary());
		employeeOld.setCity(employeeNew.getCity());
		employeeOld.setStreet(employeeNew.getStreet());
		employeeOld.setHouseNumber(employeeNew.getHouseNumber());
		employeeOld.setApartmentNumber(employeeNew.getApartmentNumber());
		employeeOld.setEmploymentDate(employeeNew.getEmploymentDate());
		session.getTransaction().commit();
	}
	/**
	 * usuwa Pracownika
	 * 
	 * @param employee do usunięcia z bazy
	 */
	public void removeEmployee(Employee employee){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Employee p = (Employee) session.load(Employee.class, new Integer(employee.getId()));
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
