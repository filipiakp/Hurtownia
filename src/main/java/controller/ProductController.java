package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import entities.Product;
import model.ProductModel;
import view.ProductView;

/**
 * Klasa odpowiadająca za reakcję na akcje użytkownika, wsyłane przez przyciski. 
 * Kontroler modyfikuje model i zmusza go do powiadomienia obserwujących o zmianie. 
 * Implementuje interfejs ActionListener.
 *
 */
public class ProductController implements ActionListener{
	private ProductModel model;
	private ProductView view;
	
	/**
	 * @param view ProductView
	 */
	public void setView(ProductView view){
		this.view = view;
	}
	
	/**
	 * @param model ProductModel
	 */
	public void setModel(ProductModel model){
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		
		
		if (source.equals(view.getTableButtonsPanel().getRemoveBttn())) {
			Product prod = view.getSelectedProduct();
			if(prod != null){
				model.removeProduct(prod);
				model.notifyAllObservers();
			}
		}else if(source.equals(view.getTableButtonsPanel().getEditBttn())) {
			Product prod = view.getSelectedProduct();
			if(prod != null){
				view.showDialog(prod, false);
			}
		} else if(source.equals(view.getTableButtonsPanel().getAddBttn())) {
			view.showDialog(new Product(), true);
		} else if(source.equals(view.getDialog().getOkCancelButtonsPanel().getOkBttn())){
			if(view.getDialog().validateFields()){
				if(view.getDialog().isNew()){
					model.addProduct(view.getDialog().getProduct());
					model.notifyAllObservers();
				}else{
					model.updateProduct(view.getDialog().getContext(), view.getDialog().getProduct());
					model.notifyAllObservers();
				}
				view.getDialog().dispose();
			}
		} else if(source.equals(view.getDialog().getOkCancelButtonsPanel().getCancelBttn())){
			view.getDialog().dispose();
		}
		
	}

}
