package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Panel zawierający pryciski do kontroli rekordów wyświetlanych w tabeli:
 * dodaj, usuń, edytuj
 *
 */
@SuppressWarnings("serial")
public class TableButtonsPanel extends JPanel{
	
	private GridLayout layout;
	private JButton addBttn, removeBttn, editBttn;

	/**
	 * Tworzy Panel
	 * 
	 * @param listener ActionListener dla przycisków
	 */
	public TableButtonsPanel(ActionListener listener) {
		layout = new GridLayout(1, 3);
		setLayout(layout);
		layout.setHgap(10);
		layout.setVgap(10);
		removeBttn = new JButton("Usuń Rekord");
		addBttn = new JButton("Dodaj Rekord");
		editBttn = new JButton("Edytuj Dane");

		addBttn.addActionListener(listener);
		editBttn.addActionListener(listener);
		removeBttn.addActionListener(listener);

		setMaximumSize(new Dimension(600, 150));
		add(removeBttn);
		add(addBttn);
		add(editBttn);
	}
	
	/**
	 * @return the dodajBttn
	 */
	public JButton getAddBttn() {
		return addBttn;
	}

	/**
	 * @return the usunBttn
	 */
	public JButton getRemoveBttn() {
		return removeBttn;
	}

	/**
	 * @return the edytujBttn
	 */
	public JButton getEditBttn() {
		return editBttn;
	}
}
