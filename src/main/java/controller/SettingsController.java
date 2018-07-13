package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.SettingsModel;
import view.SettingsView;

/**
 * Klasa odpowiadająca za reakcję na akcje użytkownika, wsyłane przez przyciski. 
 * Kontroler modyfikuje model(plik db.properties). 
 * Implementuje interfejs ActionListener.
 *
 */
public class SettingsController implements ActionListener{
	
	SettingsModel settingsModel;
	SettingsView settingsView;
	
	public SettingsController(SettingsModel settingsModel, SettingsView settingsView){
		this.settingsModel = settingsModel;
		this.settingsView = settingsView;
	}

	/**
	 * @return the settingsModel
	 */
	public SettingsModel getSettingsModel() {
		return settingsModel;
	}

	/**
	 * @param settingsModel the settingsModel to set
	 */
	public void setSettingsModel(SettingsModel settingsModel) {
		this.settingsModel = settingsModel;
	}

	/**
	 * @return the settingsView
	 */
	public SettingsView getSettingsView() {
		return settingsView;
	}

	/**
	 * @param settingsView the settingsView to set
	 */
	public void setSettingsView(SettingsView settingsView) {
		this.settingsView = settingsView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)e.getSource();
		if(source.equals(settingsView.getSaveJB())){
			settingsModel.setSettings(settingsView.getUserJTF().getText(), settingsView.getPassJTF().getText(), settingsView.getUrlJTF().getText());
			settingsView.dispose();
		}else if(source.equals(settingsView.getTryJB())){
			String message = settingsModel.chceckConnnection(settingsView.getUserJTF().getText(), settingsView.getPassJTF().getText(), settingsView.getUrlJTF().getText());
			JOptionPane.showMessageDialog(new JFrame(), message);
		}
	}
}
