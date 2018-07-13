package controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.MainModel;
import view.MainView;

/**
 * Klasa odpowiadająca za reakcję na akcje użytkownika, wysłane przez przyciski w GreetingsView i MenuBar. 
 * Kontroler modyfikuje model i zmusza go do powiadomienia obserwujących o zmianie. 
 * Implementuje interfejs ActionListener.
 *
 */
public class MainController implements ActionListener{
	
	private MainView view;
	private MainModel model;
	
	
	/**
	 * @param view MainView
	 */
	public void setView(MainView view){
		this.view = view;
	}
	
	
	/**
	 * @param model MainModel
	 */
	public void setModel(MainModel model){
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source.equals(view.getEmployeesMenuItem())||source.equals(view.getGreetingsView().getEmployeesButton())){
			
			view.showEmployees();
			model.changeFreePosition();
		}else if(source.equals(view.getOrdersMenuItem())||source.equals(view.getGreetingsView().getOrdersButton())){
			
			view.showOrders();
			model.changeFreePosition();
		}else if(source.equals(view.getStockMenuItem())||source.equals(view.getGreetingsView().getStockButton())){
			
			view.showProducts();
			model.changeFreePosition();
		}else if(source.equals(view.getContractorsMenuItem())||source.equals(view.getGreetingsView().getContractorsButton())){
			
			view.showContractors();
			model.changeFreePosition();
		}else if(source.equals(view.getSettingsMenuItem())){
			view.showSettings();
			model.changeFreePosition();
		}else if(source.equals(view.getDocumentationMenuItem())){
			
			
			try {
				Desktop.getDesktop().open(new File("doc/dokumentacja.pdf"));
			} catch (Exception ex) {
				
				try{
					Desktop.getDesktop().browse(new URI("https://orkan.tu.kielce.pl/gitlab/patryk.mendak/Hurtownia/doc/dokumentacja.pdf"));
				}catch(Exception exception){
					JOptionPane.showMessageDialog(new JFrame(), exception.getMessage());
				}
			}

		}
		
	}

	
}
