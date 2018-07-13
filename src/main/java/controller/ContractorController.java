package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import entities.Contractor;
import model.ContractorModel;
import view.ContractorView;

/**
 * Klasa odpowiadająca za reakcję na akcje użytkownika, wysłane przez przyciski. 
 * Kontroler modyfikuje model i zmusza go do powiadomienia obserwujących o zmianie. 
 * Implementuje interfejs ActionListener.
 *
 */
public class ContractorController implements ActionListener{
	private ContractorModel model;
	private ContractorView view;
	
	/**
	 * @param view ContractorView
	 */
	public void setView(ContractorView view){
		this.view = view;
	}
	
	/**
	 * @param model ContractorModel
	 */
	public void setModel(ContractorModel model){
		this.model = model;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		
		if (source.equals(view.getTableButtonsPanel().getRemoveBttn())) {
			Contractor cont = view.getSelectedContractor();
			if(cont != null){
				model.removeContractor(cont);
				model.notifyAllObservers();
			}
		}else if(source.equals(view.getTableButtonsPanel().getEditBttn())) {
			Contractor cont = view.getSelectedContractor();
			if(cont != null){
				view.showDialog(cont, false);
			}
		} else if(source.equals(view.getTableButtonsPanel().getAddBttn())) {
			view.showDialog(new Contractor(), true);
		} else if(source.equals(view.getDialog().getOkCancelButtonsPanel().getOkBttn())){
			if(view.getDialog().validateFields()){
				if(view.getDialog().isNew()){
					model.addContractor(view.getDialog().getContractor());
					model.notifyAllObservers();
				}else{
					model.updateContractor(view.getDialog().getContext(), view.getDialog().getContractor());
					model.notifyAllObservers();
				}
				view.getDialog().dispose();
			}
		} else if(source.equals(view.getDialog().getOkCancelButtonsPanel().getCancelBttn())){
			view.getDialog().dispose();
		}
	}
}
