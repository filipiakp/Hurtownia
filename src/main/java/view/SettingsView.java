package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.SettingsModel;

@SuppressWarnings("serial")
public class SettingsView extends JFrame implements ModelObserver{
	
	private JButton saveJB, tryJB;
	private JTextField userJTF, passJTF, urlJTF;
	
	public SettingsView(SettingsModel settingsModel){
		//this.settingsModel = settingsModel;
		setTitle("Ustawienia");
		setVisible(true);
		GridLayout layout = new GridLayout(0,2);
		layout.setHgap(20);
		layout.setVgap(20);
		setLayout(layout);
		JLabel userJL = new JLabel("Użytkownik:");
		JLabel passJL = new JLabel("Hasło:");
		JLabel urlJL = new JLabel("URL do bazy:");
		
		userJL.setMaximumSize(new Dimension(150, 30));
		passJL.setMaximumSize(new Dimension(150, 30));
		urlJL.setMaximumSize(new Dimension(150, 30));
		
		
		saveJB = new JButton("Zapisz");
		tryJB = new JButton("Wypróbuj Połączenie");
		
		userJTF = new JTextField(settingsModel.getUsername());
		passJTF = new JTextField(settingsModel.getPassword());
		urlJTF = new JTextField(settingsModel.getURL());
		
		add(userJL);
		add(userJTF);
		add(passJL);
		add(passJTF);
		add(urlJL);
		add(urlJTF);
		add(tryJB);
		add(saveJB);
		setSize(400, 300);
		
	}
	
	/**
	 * @return the tryJB
	 */
	public JButton getTryJB() {
		return tryJB;
	}

	/**
	 * Metoda służy do ustanowienia kontrolera dla akcji użytkownika.
	 * 
	 * @param controller ActionListener
	 */
	public void setController(ActionListener controller){
		tryJB.addActionListener(controller);
		saveJB.addActionListener(controller);
	}


	/**
	 * @return the saveJB
	 */
	public JButton getSaveJB() {
		return saveJB;
	}

	/**
	 * @return the userJTF
	 */
	public JTextField getUserJTF() {
		return userJTF;
	}

	/**
	 * @return the passJTF
	 */
	public JTextField getPassJTF() {
		return passJTF;
	}

	/**
	 * @return the urlJTF
	 */
	public JTextField getUrlJTF() {
		return urlJTF;
	}

	@Override
	public void update() {
		
		
	}
}
