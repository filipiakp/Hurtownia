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

import entities.OrderProduct;
import entities.PurchaseOrder;
import model.ContractorModel;
import model.OrderModel;
import model.OrderProductModel;
import model.ProductModel;
import view.dialog.OrderDialog;

/**
 * Klasa odpowiadająca za wyświetlanie wpisów Zamówienia z bazy danych. 
 * Dziedziczy po JInternalFrame, implementuje ModelObserver
 *
 */
@SuppressWarnings("serial")
public class OrderView extends JInternalFrame implements ModelObserver {
	
	private JTable table;
	private String[] tableColumns = { "ID", "Data", "NIP Kontrahenta", "Wartość"};
	private DefaultTableModel defaultTableModel;
	private TableButtonsPanel tableButtonsPanel;
	private ArrayList<PurchaseOrder> showedOrders;
	private ActionListener controller;
	private OrderModel model;
	private OrderDialog dialog;
	private OrderProductModel orderProductModel;
	private ContractorModel contractorModel;
	private ProductModel productModel;
	
	/**
	 * Konstruktor z parametrami: ContractorModel model, ActionListener controller, int x, int y. 
	 * Są one potrzebne do ustwienia pól klasy, dzięki którym możliwe jest pobieranie danych z bazy,
	 * przekazywanie reakcji na akcje użytkownika  do kontrolera oraz do określenia położenia okna na panelu głównym.
	 * 
	 * @param model OrderModel
	 * @param controller ActionListener
	 * @param x int
	 * @param y int
	 */
	public OrderView(OrderModel model, ActionListener controller, int x, int y){
		super("Zamówienia", true, true, true, true);
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
	 * @return the contractorModel
	 */
	public ContractorModel getContractorModel() {
		return contractorModel;
	}
	/**
	 * ustanawia model kontrahenta poprzez referencję
	 * 
	 * @param contractorModel the contractorModel to set
	 */
	public void setContractorModel(ContractorModel contractorModel) {
		this.contractorModel = contractorModel;
	}
	/**
	 * @return the productModel
	 */
	public ProductModel getProductModel() {
		return productModel;
	}
	/**
	 * ustanawia model kontrahenta poprzez referencję
	 * 
	 * @param productModel the productModel to set
	 */
	public void setProductModel(ProductModel productModel) {
		this.productModel = productModel;
	}
	
	/**
	 * @return the orderProductModel
	 */
	public OrderProductModel getOrderProductModel() {
		return orderProductModel;
	}
	/**
	 * @param orderProductModel the orderProductModel to set
	 */
	public void setOrderProductModel(OrderProductModel orderProductModel) {
		this.orderProductModel = orderProductModel;
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
	 * @return zaznaczony wpis w tabeli jako PurchaseOrder
	 */
	public PurchaseOrder getSelectedOrder() {
		if (table.getSelectedRow() != -1)
			return showedOrders.get(table.getSelectedRow());
		else 
			return null;
	}
	
	/**
	 * Metoda pokazuje dialog do wprowadzania danych
	 * 
	 * @param purchaseOrder - encja
	 * @param isNew- czy dialog ma być pusty czy wypełniony danymi
	 */
	public void showDialog(PurchaseOrder purchaseOrder, boolean isNew){
		dialog = new OrderDialog(this, purchaseOrder, isNew, controller);
	}
	
	/**
	 * @return OrderDialog dialog
	 */
	public OrderDialog getDialog(){
		return dialog;
	}
	
	/**
	 * Wypełnia tabelę danymi, pobierając je z modelu.
	 * Oblicza również sumaryczne wartości dla poszczególnych zamówień 
	 * 
	 */
	private void setTableData() {
		showedOrders = (ArrayList<PurchaseOrder>) model.getOrders();
		for (int i = 0; i < showedOrders.size(); i++) {
			PurchaseOrder o = showedOrders.get(i);
			Object[] row = new Object[tableColumns.length];
			row[0] = o.getOrderId();
			row[1] = o.getOrderDate();
			row[2] = o.getContractor()!=null?o.getContractor().getNip():null;
			double summaryValue = 0.0; 
			for(OrderProduct zp : o.getProductsList()){
				if(zp.getProduct()!=null)
					summaryValue +=zp.getProduct().getPrice() * zp.getNumberOfProducts();
			}
			
			row[3] = summaryValue;
			
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
