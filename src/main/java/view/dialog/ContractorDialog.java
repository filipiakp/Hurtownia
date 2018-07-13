package view.dialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entities.Contractor;
import view.ContractorView;
import view.OkCancelButtonsPanel;


/**
 * Klasa dialogowa reprezentująca w graficzny sposób formularz, służący do dodawania i edytowania Kontrahenta.
 *
 */
@SuppressWarnings("serial")
public class ContractorDialog extends JFrame{
	
	private Component[] jtxtField;
	private String[] contextValues = new String[8];
	private OkCancelButtonsPanel buttonsPanel;
	private Contractor context;
	private boolean isNew;// albo nowy rekord, albo edycja
	
	/**
	 * Konstruktor rysuje okno formularza i przypisuje wartości polom.
	 * 
	 * @param parent- ContractorView referencja do widoku nadrzędnego 
	 * @param context- referencja do encji
	 * @param isNew- czy encja jest nowa czy podlega edycji
	 * @param controller- kontroler dla przycisków
	 */
	public ContractorDialog(ContractorView parent, Contractor context, boolean isNew, ActionListener controller) {
		this.isNew = isNew;
		this.context = context;
		setTitle("Dodaj Nowego Kontrahenta");
		setVisible(true);
		if(!isNew){
			contextValues[0] = context.getNip();         
			contextValues[1] = context.getName();       
			contextValues[2] = context.getPhoneNumber();       
			contextValues[3] = context.getCity();        
			contextValues[4] = context.getStreet();         
			contextValues[5] = context.getHouseNumber();    
			contextValues[6] = context.getApartmentNumber();
			setTitle("Edytuj Dane Kontrahenta");
		}
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JPanel labelField;// panel zawierający label i textfield
		jtxtField = new Component[parent.getTableColumns().length];// textfieldy w tablicy, by móc szybko pobrać wartości
		
		for (int i = 0; i < parent.getTableColumns().length; i++) {
			JLabel jlabel = new JLabel(parent.getTableColumns()[i]);
			
			if(i==7){
				jlabel.setSize(new Dimension(300, 30));
				jlabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
				jtxtField[i] = new JCheckBox();
			
				if(!isNew){
					((JCheckBox)jtxtField[i]).setSelected(context.isSupplier());
					if(i==0)//bez edycji nipu- klucz podstawowy
						((JTextField)jtxtField[i]).setEnabled(false);
				}
			}else{
				jlabel.setMinimumSize(new Dimension(100, 25));
				jlabel.setPreferredSize(new Dimension(140,30));
				jtxtField[i] = new JTextField();
				jtxtField[i].setMinimumSize(jlabel.getMinimumSize());
				jtxtField[i].setPreferredSize(jlabel.getPreferredSize());
				if(!isNew){((JTextField)jtxtField[i]).setText(contextValues[i]);}//jeśli edycja, to wypełnij danymi
			}
			labelField = new JPanel();
			labelField.setLayout(new BoxLayout(labelField, BoxLayout.X_AXIS));
			labelField.add(jlabel);
			labelField.add(jtxtField[i]);
			add(labelField);
		}
		buttonsPanel = new OkCancelButtonsPanel(controller);
		add(buttonsPanel);
		pack();

	}

	
	/**
	 * Sprawdza poprawność wprowadzonych dancyh. 
	 * Data powinna być w formacie dd.mm.rrrr, Nazwy z dużych liter.
	 * 
	 * @return true lub false w zależności od poprawności wprowadzonych danych Kontrahenta.
	 */
	public boolean validateFields(){
		for(Component jtf:jtxtField){
			jtf.setBackground(Color.WHITE);
		}
		boolean isValid = true;
		if(!((JTextField)jtxtField[0]).getText().matches("[0-9]{10}")){//nip
			isValid = false;
			jtxtField[0].setBackground(Color.RED);
		}
		if(!((JTextField)jtxtField[1]).getText().matches("[A-ZŻŹĆĘŚĄÓŁŃ\\.][a-zżźćńąśłęó\\.]{0,}( [A-ZŻŹĆĘŚĄÓŁŃ\\.][a-zżźćńąśłęó\\.]{0,}){0,}")){//nazwa
			isValid = false;
			jtxtField[1].setBackground(Color.RED);
		}
		if(!((JTextField)jtxtField[2]).getText().matches("[0-9]{9}")){//telefon
			isValid = false;
			jtxtField[2].setBackground(Color.RED);
		}
		if(!((JTextField)jtxtField[3]).getText().matches("([A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]{2,} ?)+")){//miasto
			isValid = false;
			jtxtField[3].setBackground(Color.RED);
			
		}
		if(!((JTextField)jtxtField[4]).getText().matches("(al\\. )?[A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]+([- ][A-ZŻŹĆĘŚĄÓŁŃ0-9][a-zżźćńąśłęó0-9]+){0,4}")){//ulica
			isValid = false;
			jtxtField[4].setBackground(Color.RED);
		}
		if(!((JTextField)jtxtField[5]).getText().matches("[1-9][0-9]{0,4}[A-Z]?[A-Z]?")){//nrdomu
			isValid = false;
			jtxtField[5].setBackground(Color.RED);
		}
		if(!((JTextField)jtxtField[6]).getText().matches("[1-9][0-9]{0,4}[A-Z]?[A-Z]?")){//nrlokalu
			isValid = false;
			jtxtField[6].setBackground(Color.RED);
		}
		return isValid;
	}
	
	
	/**
	 * @return Contractor na podsawie wprowadzonych danych
	 */
	public Contractor getContractor(){
		Contractor contractor = new Contractor();
		contractor.setNip(((JTextField)jtxtField[0]).getText());
		contractor.setName(((JTextField)jtxtField[1]).getText());
		contractor.setPhoneNumber(((JTextField)jtxtField[2]).getText());
		contractor.setCity(((JTextField)jtxtField[3]).getText());
		contractor.setStreet(((JTextField)jtxtField[4]).getText());
		contractor.setHouseNumber(((JTextField)jtxtField[5]).getText());
		contractor.setApartmentNumber(((JTextField)jtxtField[6]).getText());
		contractor.setSupplier(((JCheckBox)jtxtField[7]).isSelected());
		
		return contractor;
	}


	/**
	 * @return the buttonsPanel zawierający przyciski "OK" i "Anuluj"
	 */
	public OkCancelButtonsPanel getOkCancelButtonsPanel() {
		return buttonsPanel;
	}


	/**
	 * @return the context
	 */
	public Contractor getContext() {
		return context;
	}


	/**
	 * @return the isNew
	 */
	public boolean isNew() {
		return isNew;
	}
	
	
}
