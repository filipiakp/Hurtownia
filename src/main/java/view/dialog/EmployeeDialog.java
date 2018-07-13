package view.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JTextField;

import entities.Employee;
import view.EmployeeView;
import view.OkCancelButtonsPanel;


/**
 * Klasa dialogowa reprezentująca w graficzny sposób formularz, służący do dodawania i edytowania Pracownika.
 *
 */
@SuppressWarnings("serial")
public class EmployeeDialog extends JFrame {

	private JTextField[] jtxtField;
	private String[] contextValues = new String[9];
	private OkCancelButtonsPanel buttonsPanel;
	private Employee context;
	private boolean isNew;

	/**
	 * Konstruktor rysuje okno formularza i przypisuje wartości polom.
	 * 
	 * @param parent- EmployeeView referencja do widoku nadrzędnego 
	 * @param context- referencja do encji
	 * @param isNew- czy encja jest nowa czy podlega edycji
	 * @param controller- kontroler dla przycisków
	 */
	public EmployeeDialog(EmployeeView parent, Employee context, boolean isNew, ActionListener controller) {
		this.context = context;
		this.isNew = isNew;
		setTitle("Dodaj Nowego Pracownika");
		setVisible(true);
		if(!isNew){
			contextValues[0] = context.getName();
			contextValues[1] = context.getSurname();
			contextValues[2] = context.getPosition();
			contextValues[3] = String.valueOf(context.getSalary());
			contextValues[4] = context.getCity();
			contextValues[5] = context.getStreet();
			contextValues[6] = context.getHouseNumber();
			contextValues[7] = context.getApartmentNumber();
			contextValues[8] = new SimpleDateFormat("dd.MM.yyyy").format(context.getEmploymentDate());
			setTitle("Edytuj Dane Pracownika");
		}
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JPanel labelField;// panel zawierający label i textfield
		jtxtField = new JTextField[parent.getTableColumns().length];// textfieldy w tablicy, by móc szybko pobrać wartości

		for (int i = 0; i < parent.getTableColumns().length; i++) {
			JLabel jlabel = new JLabel(parent.getTableColumns()[i]);
			jlabel.setPreferredSize(new Dimension(150, 30));
			jtxtField[i] = new JTextField();
			jtxtField[i].setPreferredSize(new Dimension(200, 30));
			if(!isNew){jtxtField[i].setText(contextValues[i]);}
			jlabel.setLabelFor(jtxtField[i]);
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
	 * @return buttons "OK" "Cancel" Panel
	 */
	public OkCancelButtonsPanel getOkCancelButtonsPanel(){
		return buttonsPanel;
	}
	
	/**
	 * @return Employee na podstawie wprowadzonych danych
	 */
	public Employee getEmployee(){
		Employee emp = new Employee();
		emp.setName(jtxtField[0].getText());
		emp.setSurname(jtxtField[1].getText());
		emp.setPosition(jtxtField[2].getText());
		emp.setSalary(Double.parseDouble(jtxtField[3].getText()));
		emp.setCity(jtxtField[4].getText());
		emp.setStreet(jtxtField[5].getText());
		emp.setHouseNumber(jtxtField[6].getText());
		emp.setApartmentNumber(jtxtField[7].getText());
		try {
			emp.setEmploymentDate(new SimpleDateFormat("dd.MM.yyyy").parse(jtxtField[8].getText()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return emp;
	}
	
	/**
	 * @return the context
	 */
	public Employee getContext() {
		return context;
	}

	/**
	 * @return the isNew
	 */
	public boolean isNew() {
		return isNew;
	}

	/**
	 * Sprawdza poprawność wprowadzonych dancyh. 
	 * Data powinna być w formacie dd.mm.rrrr, Nazwy z dużych liter.
	 * 
	 * @return true lub false w zależności od poprawności wprowadzonych danych Pracownika.
	 */
	public boolean validateFields(){
		for(JTextField jtf:jtxtField){
			jtf.setBackground(Color.WHITE);
		}
		boolean isValid = true;
		if(!jtxtField[0].getText().matches("[A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]{2,}")){//imie
			isValid = false;
			jtxtField[0].setBackground(Color.RED);
		}
		if(!jtxtField[1].getText().matches("([A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]+)(-[A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]{1,})?")){//nazwisko
			isValid = false;
			jtxtField[1].setBackground(Color.RED);
		}
		if(!jtxtField[2].getText().matches("[A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]{2,}( [A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]{2,})?")){//stanowisko
			isValid = false;
			jtxtField[2].setBackground(Color.RED);
		}
		if(!jtxtField[3].getText().matches("[0-9]{3,7}(\\.[0-9]{1,2})?")){//pensja
			isValid = false;
			jtxtField[3].setBackground(Color.RED);
			
		}
		if(!jtxtField[4].getText().matches("([A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]{2,} ?)+")){//miasto
			isValid = false;
			jtxtField[4].setBackground(Color.RED);
		}
		if(!jtxtField[5].getText().matches("(al\\. )?[A-ZŻŹĆĘŚĄÓŁŃ][a-zżźćńąśłęó]+([- ][A-ZŻŹĆĘŚĄÓŁŃ0-9][a-zżźćńąśłęó0-9]+){0,4}")){//ulica
			isValid = false;
			jtxtField[5].setBackground(Color.RED);
		}
		if(!jtxtField[6].getText().matches("[1-9][0-9]{0,4}[A-Z]?[A-Z]?")){//nr domu
			isValid = false;
			jtxtField[6].setBackground(Color.RED);
		}
		if(!jtxtField[7].getText().matches("[1-9][0-9]{0,4}[A-Z]?[A-Z]?")){//nr lokalu
			isValid = false;
			jtxtField[7].setBackground(Color.RED);
		}
		
		try {
			new SimpleDateFormat("dd.MM.yyyy").parse(jtxtField[8].getText());
		} catch (ParseException e) {
			isValid = false;
			jtxtField[8].setBackground(Color.RED);
			jtxtField[8].setToolTipText("Format daty: dd.mm.rrrr, np. 01.01.2010");
		}
		
		
		return isValid;
	}
}
