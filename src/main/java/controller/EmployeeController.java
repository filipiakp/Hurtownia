package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import entities.Employee;
import model.EmployeeModel;
import view.EmployeeView;

/**
 * Klasa odpowiadająca za reakcję na akcje użytkownika, wsyłane przez przyciski. 
 * Kontroler modyfikuje model i zmusza go do powiadomienia obserwujących o zmianie. 
 * Implementuje interfejs ActionListener.
 *
 */
public class EmployeeController implements ActionListener{
	
	private EmployeeView view;
	private EmployeeModel model;
	
	/**
	 * @param view EmployeeView
	 */
	public void setView(EmployeeView view){
		this.view = view;
	}
	
	/**
	 * @param model EmployeeModel
	 */
	public void setModel(EmployeeModel model){
		this.model = model;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		
		
		if (source.equals(view.getTableButtonsPanel().getRemoveBttn())) {
			Employee emp = view.getSelectedEmployee();
			if(emp != null){
				model.removeEmployee(emp);
				model.notifyAllObservers();
			}
		}else if(source.equals(view.getTableButtonsPanel().getEditBttn())) {
			Employee emp = view.getSelectedEmployee();
			if(emp != null){
				view.showDialog(emp, false);
			}
		} else if(source.equals(view.getTableButtonsPanel().getAddBttn())) {
			view.showDialog(new Employee(), true);
		} else if(source.equals(view.getDialog().getOkCancelButtonsPanel().getOkBttn())){
			if(view.getDialog().validateFields()){
				if(view.getDialog().isNew()){
					model.addEmployee(view.getDialog().getEmployee());
					model.notifyAllObservers();
				}else{
					model.updateEmployee(view.getDialog().getContext(), view.getDialog().getEmployee());
					model.notifyAllObservers();
				}
				view.getDialog().dispose();
			}
		} else if(source.equals(view.getDialog().getOkCancelButtonsPanel().getCancelBttn())){
			view.getDialog().dispose();
		}

	}

}
