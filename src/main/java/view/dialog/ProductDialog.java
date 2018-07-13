package view.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import entities.Product;
import view.OkCancelButtonsPanel;
import view.ProductView;

/**
 * Klasa dialogowa reprezentująca w graficzny sposób formularz, służący do dodawania i edytowania Produkt.
 *
 */
@SuppressWarnings("serial")
public class ProductDialog extends JFrame{

	private JTextComponent[] jtxtField;
	private String[] contextValues = new String[8];
	private OkCancelButtonsPanel buttonsPanel;
	private Product context;
	private boolean isNew = true;// albo nowy rekord, albo edycja
	
	/**
	 * Konstruktor rysuje okno formularza i przypisuje wartości polom.
	 * 
	 * @param parent- ProductView referencja do widoku nadrzędnego 
	 * @param context- referencja do encji
	 * @param isNew- czy encja jest nowa czy podlega edycji
	 * @param controller- kontroler dla przycisków
	 */
	public ProductDialog(ProductView parent, Product context, boolean isNew, ActionListener controller) {
		this.isNew = isNew;
		this.context = context;
		setTitle("Dodaj Nowy Produkt");
		setVisible(true);
		if(!isNew){
			contextValues[0] = context.getCode();
			contextValues[1] = context.getManufacturer();
			contextValues[2] = context.getName();       
			contextValues[3] = String.valueOf(context.getAmount());       
			contextValues[4] = String.valueOf(context.getPrice());        
			contextValues[5] = context.getCategory();       
			contextValues[6] = String.valueOf(context.getWeight());
			contextValues[7] = context.getSpecification();
			setTitle("Edytuj Dane Produktu");
		}
		
		// textfield
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JPanel labelField;// panel zawierający label i textfield
		jtxtField = new JTextComponent[parent.getTableColumns().length];// textfieldy w tablicy, by móc szybko pobrać wartości
		
		for (int i = 0; i < parent.getTableColumns().length; i++) {
			JLabel jlabel = new JLabel(parent.getTableColumns()[i]);
			jlabel.setPreferredSize(new Dimension(150, 30));
			labelField = new JPanel();
			labelField.setPreferredSize(new Dimension(400, 30));
			labelField.setLayout(new BoxLayout(labelField, BoxLayout.X_AXIS));
			labelField.add(jlabel);
			if(i==(parent.getTableColumns().length-1)){
				jtxtField[i] = new JTextArea();
				jtxtField[i].setAutoscrolls(true);
				jtxtField[i].setFont(new Font("Arial", Font.PLAIN, 14));
				JScrollPane jsc = new JScrollPane(jtxtField[i]);

				labelField.add(jsc);
				labelField.setPreferredSize(new Dimension(150,150));
				labelField.setMinimumSize(new Dimension(150,100));
			}
			else{
				jtxtField[i] = new JTextField();
				labelField.add(jtxtField[i]);
			}
			if(!isNew){//jeśli edycja, to wypełnij danymi
				jtxtField[i].setText(contextValues[i]);
				if(i==0)
					jtxtField[i].setEnabled(false);
			}
			
			
			add(labelField);
			
		}
		
		buttonsPanel = new OkCancelButtonsPanel(controller);
		add(buttonsPanel);
		pack();
		setVisible(true);

	}

	/**
	 * @return the buttonsPanel zawierający przyciski "OK" i "Anuluj"
	 */
	public OkCancelButtonsPanel getOkCancelButtonsPanel(){
		return buttonsPanel;
	}
	/**
	 * @return the context
	 */
	public Product getContext() {
		return context;
	}

	/**
	 * @return the isNew
	 */
	public boolean isNew() {
		return isNew;
	}
	
	/**
	 * @return Product na podstwaie wprowadzonych danych
	 */
	public Product getProduct(){
		Product p = new Product();
		p.setCode(jtxtField[0].getText());
		p.setManufacturer(jtxtField[1].getText());
		p.setName(jtxtField[2].getText());
		p.setAmount(Integer.parseInt(jtxtField[3].getText()));
		p.setPrice(Double.parseDouble(jtxtField[4].getText()));
		p.setCategory(jtxtField[5].getText());
		p.setWeight(Double.parseDouble(jtxtField[6].getText()));
		p.setSpecification(jtxtField[7].getText());
		
		return p;
	}
	
	/**
	 * Sprawdza poprawność wprowadzonych dancyh. 
	 * Data powinna być w formacie dd.mm.rrrr, Nazwy z dużych liter.
	 * 
	 * @return true lub false w zależności od poprawności wprowadzonych danych Kontrahenta.
	 */
	public boolean validateFields(){
		for(JTextComponent jtf:jtxtField){
			jtf.setBackground(Color.WHITE);
		}
		boolean isValid = true;
		if(!jtxtField[0].getText().matches("[0-9]{6}")){//kod
			isValid = false;
			jtxtField[0].setBackground(Color.RED);
		}
		if(!jtxtField[1].getText().matches("[\\w- ?]{3,}")){//producent
			isValid = false;
			jtxtField[1].setBackground(Color.RED);
		}
		if(!jtxtField[2].getText().matches("[\\w- ?]{3,}")){//nazwa
			isValid = false;
			jtxtField[2].setBackground(Color.RED);
		}
		if(!jtxtField[3].getText().matches("[0-9]{1,7}")){//ilosc
			isValid = false;
			jtxtField[3].setBackground(Color.RED);
		}
		if(!jtxtField[4].getText().matches("[0-9]{1,7}(\\.[0-9]{1,2})?")){//cena
			isValid = false;
			jtxtField[4].setBackground(Color.RED);
			
		}
		if(!jtxtField[5].getText().matches("[A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]+( [A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]+){0,}")){//dzial
			isValid = false;
			jtxtField[5].setBackground(Color.RED);
		}
		if(!jtxtField[6].getText().matches("[0-9]{1,7}(\\.[0-9]{1,3})?")){//waga
			isValid = false;
			jtxtField[6].setBackground(Color.RED);
		}
		
		return isValid;
	}
}
