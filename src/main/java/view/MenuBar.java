package view;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Klasa przedstawiająca belkę menu. W niej przyciski: 
 * Zarządzanie i Pomoc oraz podprzyciski: do zarządzania encjami , wyświetlania dokumentacji i ustawień połączenia.
 *
 */
@SuppressWarnings("serial")
public class MenuBar extends JMenuBar{
	
	private JMenuItem settingsItem, documentationItem, ordersItem, contractorsItem, stockItem, employeesItem;
	private JMenu helpMenu, manageMenu;
	/**
	 * @return the ustawieniaItem
	 */
	public JMenuItem getSettingsItem() {
		return settingsItem;
	}

	/**
	 * @return the dokumentacjaItem
	 */
	public JMenuItem getDocumentationItem() {
		return documentationItem;
	}

	/**
	 * @return the zamowieniaItem
	 */
	public JMenuItem getOrdersItem() {
		return ordersItem;
	}

	/**
	 * @return the kontrahenciItem
	 */
	public JMenuItem getContractorsItem() {
		return contractorsItem;
	}

	/**
	 * @return the magazynItem
	 */
	public JMenuItem getStockItem() {
		return stockItem;
	}

	/**
	 * @return the personelItem
	 */
	public JMenuItem getEmployeesItem() {
		return employeesItem;
	}
	
	/**
	 * Konstruktor powoduje inicjalizację komponentów graficznych i stworzenie menu.
	 */
	public MenuBar(){
		//menu pomocy: ustawienia i dokumentacja
		settingsItem = new JMenuItem("Ustawienia");
		settingsItem.setMnemonic(KeyEvent.VK_U);
		
		documentationItem = new JMenuItem("Dokumentacja");
		documentationItem.setMnemonic(KeyEvent.VK_D);
		
		helpMenu = new JMenu("Pomoc");
		helpMenu.setMnemonic(KeyEvent.VK_P);
		helpMenu.add(settingsItem);
		helpMenu.add(documentationItem);
		
		
		//menu zarządzania: personelem, kontrahentami, sprzedarzami i produktami
		ordersItem = new JMenuItem("Zamówienia");
		ordersItem.setMnemonic(KeyEvent.VK_Z);
		
		contractorsItem = new JMenuItem("Kontrahenci");
		contractorsItem.setMnemonic(KeyEvent.VK_K);
		
		stockItem = new JMenuItem("Magazyn");
		stockItem.setMnemonic(KeyEvent.VK_M);
		
		employeesItem = new JMenuItem("Personel");
		employeesItem.setMnemonic(KeyEvent.VK_P);
		manageMenu = new JMenu("Zarządzaj");
		manageMenu.setMnemonic(KeyEvent.VK_Z);
		manageMenu.add(ordersItem);
		manageMenu.add(contractorsItem);
		manageMenu.add(stockItem);
		manageMenu.add(employeesItem);
		
		//dodanie do belki
		add(manageMenu);
		add(helpMenu);
	}
	
	/**
	 * ustanawia kontroler dla itemów
	 * 
	 * @param controller ActionListener
	 */
	public void setController(ActionListener controller){
		settingsItem.addActionListener(controller);
		documentationItem.addActionListener(controller);
		ordersItem.addActionListener(controller);
		contractorsItem.addActionListener(controller);
		stockItem.addActionListener(controller);
		employeesItem.addActionListener(controller);;
	}

}
