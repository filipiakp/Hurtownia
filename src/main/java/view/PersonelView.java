package view;

import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PersonelView extends JFrame{

	private JTable tabelaPersonelu;
	private String[] kolumnyTabeli = {"Imię","Nazwisko","Stanowisko","Pensja","Miasto","Nr Domu","Nr Lokalu","Data Zatrudnienia"};
	private Object[][] dane;
	/**
	 * @return the dane
	 */
	public Object[][] getDane() {
		return dane;
	}
	/**
	 * @param dane the dane to set
	 */
	public void setDane(Object[][] dane) {
		this.dane = dane;
	}
	
	public PersonelView(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);//maksymalizuje okno
		
		tabelaPersonelu = new JTable(dane,kolumnyTabeli);
		tabelaPersonelu.setFillsViewportHeight(true);
		JScrollPane jscrollpane = new JScrollPane(tabelaPersonelu);
		jscrollpane.setBounds(0, 0, this.getWidth()-15, this.getHeight()-15);
		add(jscrollpane);
		
	}
}
