package view.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entities.Contractor;
import entities.OrderProduct;
import entities.Product;
import entities.PurchaseOrder;
import view.OkCancelButtonsPanel;
import view.OrderView;
import view.ProductsListPanel;


/**
 * Klasa dialogowa reprezentująca w graficzny sposób formularz służący do dodawania i edytowania Zamówienia.
 * Za jej pomocą dodawane są także ZamówienieProdukt
 *
 */
@SuppressWarnings("serial")
public class OrderDialog extends JFrame{

	private OrderView parent;
	private PurchaseOrder context;
	private JTextField dateJTF, amountTF;
	private ProductsListPanel productsListPanel;
	private OkCancelButtonsPanel buttonsPanel;
	private JButton addBttn, removeBttn;
	private JComboBox<String> kntJCB, productsCB;
	private Set<OrderProduct> orderProductSet;
	private boolean isNew = true;// albo nowy rekord, albo edycja

	/**
	 * Konstruktor rysuje okno formularza i przypisuje wartości polom.
	 * 
	 * @param parent- OrderView referencja do widoku nadrzędnego 
	 * @param context- referencja do encji
	 * @param isNew- czy encja jest nowa czy podlega edycji
	 * @param controller- kontroler dla przycisków
	 */
	public OrderDialog(OrderView parent, PurchaseOrder context, boolean isNew, ActionListener controller) {
		this.parent = parent;
		this.isNew = isNew;
		this.context = context;
		setTitle(isNew?"Nowe Zamówienie":"Edytuj Dane Zamówienia");
		setVisible(true);
		//główny panel
		setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		
		//panel z datą
		JLabel dateJL = new JLabel(parent.getTableColumns()[1]);
		dateJL.setMinimumSize(new Dimension(150, 30));
		dateJL.setPreferredSize(dateJL.getMinimumSize());
		dateJTF = new JTextField();
		dateJTF.setPreferredSize(dateJL.getMinimumSize());
		JPanel dateJP = new JPanel();
		dateJP.setLayout(new BoxLayout(dateJP, BoxLayout.X_AXIS));
		dateJP.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		dateJP.add(dateJL);
		dateJP.add(dateJTF);
		
		//panel z kontrahentem
		JLabel kntJL = new JLabel(parent.getTableColumns()[2]);
		kntJL.setMinimumSize(dateJL.getMinimumSize());
		kntJL.setPreferredSize(kntJL.getMinimumSize());
		kntJCB = new JComboBox<String>();
		kntJCB.setPreferredSize(kntJL.getMinimumSize());
		for(Contractor c: parent.getContractorModel().getContractors()){
			kntJCB.addItem(String.valueOf(c.getNip()).concat(" "+c.getName()));
		}
		JPanel kntJP = new JPanel();
		kntJP.setLayout(new BoxLayout(kntJP, BoxLayout.X_AXIS));
		kntJP.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		kntJP.add(kntJL);
		kntJP.add(kntJCB);
		
		//panel łączący datę z kontrahentem
		JPanel dataNipPanel = new JPanel();
		dataNipPanel.setLayout(new BoxLayout(dataNipPanel, BoxLayout.Y_AXIS));
		dataNipPanel.add(kntJP);
		dataNipPanel.add(dateJP);
		dataNipPanel.setPreferredSize(new Dimension(500, 80));
		add(dataNipPanel);
		
		//panel z listą produktów
		amountTF = new JTextField(4);
		amountTF.setPreferredSize(new Dimension(70, 30));
		amountTF.setMaximumSize(amountTF.getPreferredSize());
		productsCB = new JComboBox<String>();
		productsCB.setPreferredSize(new Dimension(420, 30));
		for(Product p: parent.getProductModel().getProducts()){
			productsCB.addItem(String.valueOf(p.getCode()) + " " +p.getName()+ " " +p.getPrice() + "(max szt. "+p.getAmount()+")");
		}
		JPanel cbtf = new JPanel();//panel z rozwijaną listą produktów i polem textowym na ilość
		cbtf.setLayout(new BoxLayout(cbtf, BoxLayout.X_AXIS));
		cbtf.setMinimumSize(new Dimension(490, 30));
		cbtf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		cbtf.add(productsCB);
		cbtf.add(amountTF);
		
		addBttn = new JButton("dodaj");
		removeBttn = new JButton("usuń");
		addBttn.addActionListener(controller);
		removeBttn.addActionListener(controller);
		JPanel addRemovePanel = new JPanel();
		addRemovePanel.setLayout(new BoxLayout(addRemovePanel, BoxLayout.X_AXIS));
		addRemovePanel.setMinimumSize(new Dimension(490, 30));
		addRemovePanel.add(addBttn);
		addRemovePanel.add(removeBttn);
		
		productsListPanel = new ProductsListPanel();
		productsListPanel.add(cbtf);
		productsListPanel.add(addRemovePanel);
		productsListPanel.setMinimumSize(new Dimension(490, 400));
		productsListPanel.setBorder(BorderFactory.createTitledBorder("Lista Produktów"));
		add(productsListPanel);
		
		//panel z przyciskami ok, anuluj
		buttonsPanel = new OkCancelButtonsPanel(controller);
		add(buttonsPanel);
		
		//przypisanie wartości pól obiektu edytowanego
		if(!isNew){
			dateJTF.setText(new SimpleDateFormat("dd.MM.yyyy").format(context.getOrderDate()));
			kntJCB.setSelectedItem(context.getContractor());
			orderProductSet = context.getProductsList();
			productsListPanel.setList(getOrderProductsStringSet());
		}else{
			orderProductSet = new HashSet<OrderProduct>();
		}
			
		
		pack();
		setMinimumSize(getSize());
	}
	
	/**
	 * @return the amountTF- pole tekstowe zawierające ilość produktu do dodania
	 */
	public JTextField getAmountTF() {
		return amountTF;
	}

	/**
	 * @return the productsListPanel
	 */
	public ProductsListPanel getProductsListPanel() {
		return productsListPanel;
	}

	/**
	 * @return the productsCB
	 */
	public JComboBox<String> getProductsCB() {
		return productsCB;
	}
	
	/**
	 * @return the addBttn
	 */
	public JButton getAddBttn() {
		return addBttn;
	}

	/**
	 * @return the removeBttn
	 */
	public JButton getRemoveBttn() {
		return removeBttn;
	}
	
	/**
	 * Sprawdza czy Produkt jest wybrany oraz czy jest wpisana liczba w amountTF.
	 * 
	 * @return true lub false w zależności od poprawności wprowadzonych danych ZamówieniaProduktu.
	 */
	public boolean validateProductFields(){
		boolean isValid = true;
		amountTF.setBackground(Color.WHITE);
		productsCB.setBackground(Color.WHITE);
		if(String.valueOf(productsCB.getSelectedItem()).isEmpty()){
			isValid = false;
			productsCB.setBackground(Color.RED);
		}
		try{
			int amount = Integer.parseInt(amountTF.getText());
			if(amount<1){
				throw new NumberFormatException();
			}
		}catch(NumberFormatException e){
			isValid = false;
			amountTF.setBackground(Color.RED);
			amountTF.setToolTipText("Podaj liczbę produktów");
		}
		
		return isValid;
	}

	/**
	 * Sprawdza poprawność wprowadzonych dancyh. 
	 * Data powinna być w formacie dd.mm.rrrr, Nazwy z dużych liter.
	 * 
	 * @return true lub false w zależności od poprawności wprowadzonych danych Zamówienia.
	 */
	public boolean validateFields(){
		
		boolean isValid = true;
		dateJTF.setBackground(Color.WHITE);
		productsCB.setBackground(Color.WHITE);
		productsCB.setToolTipText("");
		amountTF.setBackground(Color.WHITE);
		productsCB.setToolTipText("");
		kntJCB.setBackground(Color.WHITE);
		
		if(kntJCB.getSelectedIndex() == -1){
			kntJCB.setBackground(Color.RED);
			isValid = false;
		}
		if(productsListPanel.getList().isEmpty()){
			productsCB.setBackground(Color.RED);
			productsCB.setToolTipText("Należy dodać produkty do listy");
			amountTF.setBackground(Color.RED);
			productsCB.setToolTipText("Należy dodać produkty do listy");
			isValid = false;
		}
		
		try {
			new SimpleDateFormat("dd.MM.yyyy").parse(dateJTF.getText());
		} catch (ParseException e) {
			isValid = false;
			dateJTF.setBackground(Color.RED);
			dateJTF.setToolTipText("Format daty: dd.mm.rrrr, np. 01.01.2010");
		}
		
		return isValid;
	}
	
	
	/**
	 * @return PurchaseOrder na podstawie wprowadzonych danych
	 */
	public PurchaseOrder getOrder(){
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		
		try {
			purchaseOrder.setOrderDate( new SimpleDateFormat("dd.MM.yyyy").parse(dateJTF.getText()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		purchaseOrder.setContractor(parent.getContractorModel().getContractorByNip(String.valueOf(kntJCB.getSelectedItem()).substring(0,10)));
		purchaseOrder.setProductsList(orderProductSet);
		
		return purchaseOrder;
	}


	/**
	 * @return the buttonsPanel z "OK" i "Anuluj"
	 */
	public OkCancelButtonsPanel getOkCancelButtonsPanel() {
		return buttonsPanel;
	}


	/**
	 * @return the context- instancja której dane były wyświtlane w momencie uruchomienia okna
	 */
	public PurchaseOrder getContext() {
		return context;
	}

	/**
	 * @return the isNew- czy encja była edytowana, czy dodawana nowa
	 */
	public boolean isNew() {
		return isNew;
	}
	
	/**
	 * @return zaznaczony w rozwijanej liście Produkt z modelu
	 */
	public Product getSelectedProduct(){
		return parent.getProductModel().getProductByCode(String.valueOf(productsCB.getSelectedItem()).substring(0,6));
	}
	
	/**
	 * @return Set ciągów znaków reprezentujących ZamówienieProdukt
	 */
	private Set<String> getOrderProductsStringSet(){
		Set<String> stringSet = new HashSet<String>();
		for(OrderProduct op : orderProductSet){
			if(op.getProduct()!=null)
				stringSet.add(getProductInfo(op.getProduct().getCode())+" zł * "+String.valueOf(op.getNumberOfProducts()));
			//stringSet.add("Produkt id: "+op.getProduct().getCode()+" w ilości: "+String.valueOf(op.getNumberOfProducts())+"szt.");
		}
		return stringSet;
	}
	
	/**
	 * Pobiera z rozwijanej listy produktów dane o produkcie.
	 * 
	 * @param code
	 * @return String z kodem, nazwą i ceną produktu
	 */
	private String getProductInfo(String code){
		for(int i=0; i<productsCB.getItemCount(); i++){
			if(String.valueOf(productsCB.getItemAt(i)).substring(0, 6).equals(code)){
				String temp = String.valueOf(productsCB.getItemAt(i));
				temp = temp.substring(0, temp.indexOf("("));
				return temp;//kod nazwa cena
			}
				
		}
		return null;
	}
	
	/**
	 * dodaje ZamówienieProdukt do wyświetlanej listy
	 * 
	 * @param orderProduct do dodania
	 */
	public void addOrderProduct(OrderProduct orderProduct){
		orderProductSet.add(orderProduct);
		productsListPanel.setList(getOrderProductsStringSet());
	}
	
	/**
	 * Usuwa zaznaczony ZamówienieProdukt i zwraca instancję tego produktu
	 * 
	 * @return OrderProduct, który został usunięty
	 */
	public OrderProduct removeSelectedOrderProduct(){
		String orderProductString = productsListPanel.removeSelectedOrderProduct();
		OrderProduct orderProduct = null;
		if(orderProductString != null){
			orderProduct = getOrderProductByString(orderProductString);
			if(orderProduct != null){
				orderProductSet.remove(orderProduct);
				return orderProduct;
			}
		}
		return null;
		
	}
	/**
	 * Sprawdza czy podany produkt został już dodany do listy.
	 * @return true lub false, w zależności od zawartości <i>orderProductSet</i>
	 * 
	 * @param code Produktu
	 */
	public boolean isProductAdded(String code){
		for(OrderProduct op : orderProductSet)
			if(op.getProduct().getCode().equals(code))
				return true;
		
		return false;
	}
	
	/**
	 * Wyszukuje w zbiorze wyświetlanych produktów instancji OrderProduct.
	 * 
	 * 
	 * @param orderProductString
	 * @return instancje <b>OrderProduct</b> lub null, jeżeli nie istnieje taki wpis
	 */
	private OrderProduct getOrderProductByString(String orderProductString){
		for(OrderProduct op : orderProductSet){
			if(op.getProduct()!=null)
				if(op.getProduct().getCode().equals(orderProductString.substring(0, 6))){
					return op;
				}
		}
		return null;
	}
	
}
