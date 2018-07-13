package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 *
 */
@SuppressWarnings("serial")
public class GreetingsView extends JInternalFrame{
	
	private JLabel greetingLabel, tipLabel;
	private JPanel mainPanel;
	private JButton employeesButton, contractorsButton, stockButton, ordersButton;
	
	/**
	 * @return the employeesButton
	 */
	public JButton getEmployeesButton() {
		return employeesButton;
	}

	/**
	 * @return the contractorsButton
	 */
	public JButton getContractorsButton() {
		return contractorsButton;
	}

	/**
	 * @return the stockButton
	 */
	public JButton getStockButton() {
		return stockButton;
	}

	/**
	 * @return the ordersButton
	 */
	public JButton getOrdersButton() {
		return ordersButton;
	}

	/**
	 * 
	 */
	GreetingsView(){
		super("Witaj", true, true, true);
		JPanel greetingPanel = new JPanel();
		greetingPanel.setLayout(new BoxLayout(greetingPanel, BoxLayout.Y_AXIS));

		greetingLabel = new JLabel("Witamy w Hurtowni AGD/RTV");
		greetingLabel.setFont(new Font("Arial", Font.BOLD, 30));
		greetingLabel.setAlignmentX(CENTER_ALIGNMENT);

		tipLabel = new JLabel("Wybierz, co chcesz teraz zrobić");
		tipLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		tipLabel.setAlignmentX(CENTER_ALIGNMENT);
		greetingPanel.add(greetingLabel);
		greetingPanel.add(tipLabel);

		// przyciski panel
		JPanel buttonsPanel = new JPanel();
		GridLayout buttonsLayout = new GridLayout(2, 2);
		buttonsPanel.setLayout(buttonsLayout);
		ordersButton = new JButton("Zamówienia");
		contractorsButton = new JButton("Kontrahenci");
		stockButton = new JButton("Magazyn");// do produktów
		employeesButton = new JButton("Personel");

		buttonsPanel.add(ordersButton);
		buttonsPanel.add(contractorsButton);
		buttonsPanel.add(stockButton);
		buttonsPanel.add(employeesButton);

		buttonsLayout.setHgap(10);
		buttonsLayout.setVgap(10);

		// panel główny- łączący dwa w.w. panele
		mainPanel = new JPanel();
		GridLayout mainLayout = new GridLayout(2, 1);
		mainPanel.setLayout(mainLayout);
		mainPanel.add(greetingPanel);
		mainPanel.add(buttonsPanel);
		mainLayout.setHgap(10);
		mainLayout.setVgap(10);
		
		//dodanie jako JInternalFrame
		setSize(new Dimension(600, 500));
		setLocation(0, 20);
		setVisible(true);
		setContentPane(mainPanel);
		try {
			setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param controller the controller to set
	 */
	public void setController(ActionListener controller) {
		employeesButton.addActionListener(controller);
		contractorsButton.addActionListener(controller);
		stockButton.addActionListener(controller);
		ordersButton.addActionListener(controller);
		
	}

}
