package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.Employee;
import model.EmployeeModel;
import view.dialog.EmployeeDialog;

/**
 * Klasa odpowiadająca za wyświetlanie wpisów Pracownika z bazy danych. 
 * Dziedziczy po JInternalFrame, implementuje ModelObserver
 *
 */
@SuppressWarnings("serial")
public class EmployeeView extends JInternalFrame implements ModelObserver{

	private JTable table;
	private String[] tableColumns = {"Imię", "Nazwisko", "Stanowisko", "Pensja", "Miasto", "Ulica", "Nr Domu",
			"Nr Lokalu", "Data Zatrudnienia" };
	private DefaultTableModel defaultTableModel;
	private TableButtonsPanel tableButtonsPanel;
	private ArrayList<Employee> showedEmployees;
	private EmployeeModel model;
	private EmployeeDialog dialog;
	private ActionListener controller;
	
	/**
	 * Konstruktor z parametrami: ContractorModel model, ActionListener controller, int x, int y. 
	 * Są one potrzebne do ustwienia pól klasy, dzięki którym możliwe jest pobieranie danych z bazy,
	 * przekazywanie reakcji na akcje użytkownika  do kontrolera oraz do określenia położenia okna na panelu głównym.
	 * 
	 * @param model EmployeeModel
	 * @param controller ActionListener
	 * @param x int
	 * @param y int
	 */
	public EmployeeView(EmployeeModel model, ActionListener controller, int x, int y) {
		super("Personel", true, true, true, true);
		this.model = model;
		this.controller = controller;
		setSize(new Dimension(600, 500));
		setLocation(x, y);
		setVisible(true);
		setLayout(new GridLayout(1, 1));
		// tabela
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(false);
		
		defaultTableModel = new DefaultTableModel(tableColumns, 0){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JScrollPane jscrollpane = new JScrollPane(table);
		
		tableButtonsPanel = new TableButtonsPanel(controller);
		// panel łączący wszystkie
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(jscrollpane);
		mainPanel.add(tableButtonsPanel);
		add(mainPanel);
		setTableData();
	}
	
	/**
	 * @return the kolumnyTabeli
	 */
	public String[] getTableColumns() {
		return tableColumns;
	}

	/**
	 * @return the tableButtonsPanel- panel z przyciskami
	 */
	public TableButtonsPanel getTableButtonsPanel() {
		return tableButtonsPanel;
	}
	
	/**
	 * @return zaznaczony wpis w tabeli jako Employee
	 */
	public Employee getSelectedEmployee() {
		if (table.getSelectedRow() != -1)
			return showedEmployees.get(table.getSelectedRow());
		else 
			return null;
	}
	
	/**
	 * Metoda pokazuje dialog do wprowadzania danych
	 * 
	 * @param employee encja
	 * @param isNew- czy dialog ma być pusty czy wypełniony danymi
	 */
	public void showDialog(Employee employee, boolean isNew){
		dialog = new EmployeeDialog(this, employee, isNew, controller);
	}
	
	/**
	 * @return EmployeeDialog dialog
	 */
	public EmployeeDialog getDialog(){
		return dialog;
	}
	
	/**
	 * Przypisuje dane do tabeli widoku
	 */
	private void setTableData() {
		showedEmployees = (ArrayList<Employee>)model.getEmployees();
		for (int i = 0; i < showedEmployees.size(); i++) {
			Employee p = showedEmployees.get(i);
			Object[] row = new Object[tableColumns.length];
			row[0] = p.getName();
			row[1] = p.getSurname();
			row[2] = p.getPosition();
			row[3] = p.getSalary();
			row[4] = p.getCity();
			row[5] = p.getStreet();
			row[6] = p.getHouseNumber();
			row[7] = p.getApartmentNumber();
			row[8] = new SimpleDateFormat("dd.MM.yyyy").format(p.getEmploymentDate());
			
			defaultTableModel.addRow(row);
		}
		table.setModel(defaultTableModel);
	}
	
	@Override
	public void update() {
		defaultTableModel = new DefaultTableModel(tableColumns, 0){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		setTableData();
		
	}

}
