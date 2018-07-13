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

import entities.Product;
import model.ProductModel;
import view.dialog.ProductDialog;

/**
 * Klasa odpowiadająca za wyświetlanie wpisów Produktu z bazy danych. 
 * Dziedziczy po JInternalFrame, implementuje ModelObserver
 *
 */
@SuppressWarnings("serial")
public class ProductView  extends JInternalFrame implements ModelObserver{

	private JTable table;
	private String[] tableColumns = { "Kod", "Producent", "Nazwa", "Ilość", "Cena", "Dział", "Waga(kg)", "Opis"};
	private ArrayList<Product> showedProducts;
	private DefaultTableModel defaultTableModel;
	private TableButtonsPanel tableButtonsPanel;
	private ProductModel model;
	private ProductDialog dialog;
	private ActionListener controller;
	
	/**
	 * Konstruktor z parametrami: ProductModel model, ActionListener controller, int x, int y. 
	 * Są one potrzebne do ustwienia pól klasy, dzięki którym możliwe jest pobieranie danych z bazy,
	 * przekazywanie reakcji na akcje użytkownika  do kontrolera oraz do określenia położenia okna na panelu głównym.
	 * 
	 * @param model ProductModel
	 * @param controller ActionListener
	 * @param x int
	 * @param y int
	 */
	public ProductView(ProductModel model, ActionListener controller, int x, int y){
		super("Magazyn", true, true, true, true);
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
	 * @return zaznaczony wpis w tabeli jako Product
	 */
	public Product getSelectedProduct() {
		if (table.getSelectedRow() != -1)
			return showedProducts.get(table.getSelectedRow());
		else 
			return null;
	}
	
	/**
	 * Metoda pokazuje dialog do wprowadzania danych
	 * 
	 * @param product - encja
	 * @param isNew- czy dialog ma być pusty czy wypełniony danymi
	 */
	public void showDialog(Product product, boolean isNew){
		dialog = new ProductDialog(this, product, isNew, controller);
	}
	
	/**
	 * @return ProductDialog dialog
	 */
	public ProductDialog getDialog(){
		return dialog;
	}
	
	/**
	 * Wypełnia tabelę danymi, pobierając je z modelu.
	 * 
	 */
	private void setTableData() {
		showedProducts = (ArrayList<Product>)model.getProducts();
		for (int i = 0; i < showedProducts.size(); i++) {
			Product p = showedProducts.get(i);
			Object[] row = new Object[tableColumns.length];
			row[0] = p.getCode();
			row[1] = p.getManufacturer();
			row[2] = p.getName();
			row[3] = p.getAmount();
			row[4] = p.getPrice();
			row[5] = p.getCategory();
			row[6] = p.getWeight();
			row[7] = p.getSpecification();
			
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
