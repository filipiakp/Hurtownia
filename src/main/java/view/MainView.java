package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;

import controller.ContractorController;
import controller.EmployeeController;
import controller.OrderController;
import controller.ProductController;
import controller.SettingsController;
import model.ContractorModel;
import model.EmployeeModel;
import model.MainModel;
import model.OrderModel;
import model.OrderProductModel;
import model.ProductModel;
import model.SettingsModel;

/**
* Klasa MainView jest oknem głównym aplikacji.
* Posiada panel roboczy, na którym wyświetlane są okna,
* oraz menu, z którego wybieramy, nad czym chcemy pracować.
* 
*/
@SuppressWarnings("serial")
public class MainView extends JFrame implements ModelObserver{

	private MainModel model;
	private BufferedImage img;
	private JDesktopPane desktop;
	private MenuBar menuBar;
	
	private EmployeeModel employeeModel;
	private EmployeeView employeeView;
	private EmployeeController employeeController;

	private ProductModel productModel;
	private ProductView productView;
	private ProductController productController;
	
	private ContractorModel contractorModel;
	private ContractorView contractorView;
	private ContractorController contractorController;
	
	private OrderModel orderModel;
	private OrderView orderView;
	private OrderController orderController;
	private OrderProductModel orderProductModel;
	
	private SettingsModel settingsModel;
	private SettingsView settingsView;
	
	private GreetingsView greetingsView;
	
	ActionListener controller;
	
	/**
	 * powoduje rozpoczęcie działania aplikacji- pokazanie widoku użytkownikowi oraz
	 * inicjalizację modeli encji i obiektów.
	 * 
	 * @param controller ActionListener
	 */
	public MainView(ActionListener controller){
		this.controller = controller;
		
		employeeModel = new EmployeeModel();
		productModel = new ProductModel();
		contractorModel = new ContractorModel();
		orderModel = new OrderModel();
		orderProductModel = new OrderProductModel();
		settingsModel = new SettingsModel();
		
		// desktop- panel roboczy
		//załadowanie tła
		try {
			img = ImageIO.read(new File("resources/bg.jpg"));// pobranie tła panelu
		} catch (IOException e) {
			e.printStackTrace();
		}
		// własna implementacja DesktopPane- dodanie tła
		desktop = new JDesktopPane() {
			@Override
			protected void paintComponent(Graphics grphcs) {
				super.paintComponent(grphcs);
				//Image scaledImg = img.getScaledInstance(desktop.getWidth(),desktop.getHeight(), Image.SCALE_FAST);
				grphcs.drawImage(img/*scaledImg*/, 0, 0, null);
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(img.getWidth(), img.getHeight());
			}
		};
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);// bezproblemowe
															// przenoszenie
															// okienek
		setContentPane(desktop);
		menuBar = new MenuBar();
		menuBar.setController(controller);
		setJMenuBar(menuBar);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setTitle("Hurtownia");
		setMinimumSize(new Dimension(500, 300));
	}
	
	/**
	 * ustawia model główny
	 * 
	 * @param model MainModel
	 */
	public void setModel(MainModel model){
		this.model=model;
	}
	
	/**
	 * metoda ta służy do pokazania okienka ustawień 
	 */
	public void showSettings(){
		settingsView = new SettingsView(settingsModel);
		settingsModel.addObserver(settingsView);
		SettingsController settingsController = new SettingsController(settingsModel, settingsView);
		settingsView.setController(settingsController);
	}
	
	/**
	 * metoda ta służy do pokazania okienka produktów 
	 */
	public void showProducts(){
		if(!isViewShown(productView)){
			productController = new ProductController();
			productView = new ProductView(productModel, productController, model.getFreePosition(), 0);
			productController.setModel(productModel);
			productController.setView(productView);
			productModel.addObserver(productView);
			desktop.add(productView);
			try {
				productView.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}else{
			try {
				productView.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * metoda ta służy do pokazania okienka kontrahentów 
	 */
	public void showContractors(){
		if(!isViewShown(contractorView)){
			contractorController = new ContractorController();
			contractorView = new ContractorView(contractorModel, contractorController, model.getFreePosition(), 0);
			contractorController.setModel(contractorModel);
			contractorController.setView(contractorView);
			contractorModel.addObserver(contractorView);
			desktop.add(contractorView);
			try {
				contractorView.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}else{
			try {
				contractorView.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * metoda ta służy do pokazania okienka zamówień 
	 */
	public void showOrders(){
		if(!isViewShown(orderView)){
			orderController = new OrderController();
			orderView = new OrderView(orderModel, orderController, model.getFreePosition(), 0);
			orderController.setModel(orderModel);
			orderController.setView(orderView);
			orderController.setOrderProductModel(orderProductModel);
			orderModel.addObserver(orderView);
			orderView.setContractorModel(contractorModel);
			orderView.setOrderProductModel(orderProductModel);
			orderView.setProductModel(productModel);
			desktop.add(orderView);
			try {
				orderView.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}else{
			try {
				orderView.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * metoda ta służy do pokazania okienka pracowników 
	 */
	public void showEmployees(){
		if(!isViewShown(employeeView)){
			employeeController = new EmployeeController();
			employeeView = new EmployeeView(employeeModel, employeeController, model.getFreePosition(), 0);
			employeeController.setModel(employeeModel);
			employeeController.setView(employeeView);
			employeeModel.addObserver(employeeView);
			desktop.add(employeeView);
			try {
				employeeView.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}else{
			try {
				employeeView.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * metoda ta służy do pokazania okienka powitalnego 
	 */
	public void showGreetingsView(){
		greetingsView = new GreetingsView();
		greetingsView.setController(controller);
		desktop.add(greetingsView);
		try {
			greetingsView.setSelected(true);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * @return greetingsView
	 */
	public GreetingsView getGreetingsView(){
		return greetingsView;
	}
	
	/**
	 * Sprawdza czy okienko jest już pokazane.
	 * 
	 * @param subView- okienko do sprawdzenia
	 * @return true lub false w zależności od tego, jest juz pokazane
	 */
	public boolean isViewShown(JInternalFrame subView){
		for(JInternalFrame jif : desktop.getAllFrames())
			if(jif.equals(subView)){return true;}

		return false;

	}
	
	public JMenuItem getSettingsMenuItem(){
		return menuBar.getSettingsItem();
	}
	public JMenuItem getEmployeesMenuItem(){
		return menuBar.getEmployeesItem();
	}
	public JMenuItem getOrdersMenuItem(){
		return menuBar.getOrdersItem();
	}
	public JMenuItem getContractorsMenuItem(){
		return menuBar.getContractorsItem();
	}
	public JMenuItem getDocumentationMenuItem(){
		return menuBar.getDocumentationItem();
	}
	public JMenuItem getStockMenuItem(){
		return menuBar.getStockItem();
	}

	@Override
	public void update() {
		
		
	}
}
