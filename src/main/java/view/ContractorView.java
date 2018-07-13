package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.Contractor;
import model.ContractorModel;
import view.dialog.ContractorDialog;

/**
 * Klasa odpowiadająca za wyświetlanie wpisów Kontrahenta z bazy danych. 
 * Dziedziczy po JInternalFrame, implementuje ModelObserver
 *
 */
@SuppressWarnings("serial")
public class ContractorView extends JInternalFrame implements ModelObserver{

	private JTable table;
	private String[] tableColumns = { "Nip","Nazwa", "Telefon", "Miasto", "Ulica", "Nr Domu", "Nr Lokalu", "Dostawca"};
	private DefaultTableModel defaultTableModel;
	private TableButtonsPanel tableButtonsPanel;
	private ArrayList<Contractor> showedContractors;
	private ContractorModel model;
	private ContractorDialog dialog;
	private ActionListener controller;
	
	/**
	 * Konstruktor z parametrami: ContractorModel model, ActionListener controller, int x, int y. 
	 * Są one potrzebne do ustwienia pól klasy, dzięki którym możliwe jest pobieranie danych z bazy,
	 * przekazywanie reakcji na akcje użytkownika  do kontrolera oraz do określenia położenia okna na panelu głównym.
	 * 
	 * @param model ContractorModel
	 * @param controller ActionListener
	 * @param x int
	 * @param y int
	 */
	public ContractorView(ContractorModel model, ActionListener controller, int x, int y){
		super("Kontrahenci", true, true, true, true);
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
	 * @return zaznaczony wpis w tabeli jako Contractor
	 */
	public Contractor getSelectedContractor() {
		if (table.getSelectedRow() != -1)
			return showedContractors.get(table.getSelectedRow());
		else 
			return null;
	}
	
	/**
	 * Metoda pokazuje dialog do wprowadzania danych
	 * 
	 * @param contractor encja
	 * @param isNew- czy dialog ma być pusty czy wypełniony danymi
	 */
	public void showDialog(Contractor contractor, boolean isNew){
		dialog = new ContractorDialog(this, contractor, isNew, controller);
	}
	
	/**
	 * @return ContractorDialog dialog
	 */
	public ContractorDialog getDialog(){
		return dialog;
	}
	
	/**
	 * Wypełnia tabelę danymi, pobierając je z modelu.
	 * 
	 */
	private void setTableData() {
		showedContractors = (ArrayList<Contractor>)model.getContractors();
		for (int i = 0; i < showedContractors.size(); i++) {
			Contractor k = showedContractors.get(i);
			Object[] row = new Object[tableColumns.length];
			row[0] = k.getNip();
			row[1] = k.getName();
			row[2] = k.getPhoneNumber();
			row[3] = k.getCity();
			row[4] = k.getStreet();
			row[5] = k.getHouseNumber();
			row[6] = k.getApartmentNumber();
			row[7] = k.isSupplier()?"Tak":"Nie";
			
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
