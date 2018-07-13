package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import entities.OrderProduct;
import entities.PurchaseOrder;
import model.OrderModel;
import model.OrderProductModel;
import view.OrderView;

/**
 * Klasa odpowiadająca za reakcję na akcje użytkownika, wsyłane przez przyciski. 
 * Kontroler modyfikuje model i zmusza go do powiadomienia obserwujących o zmianie. 
 * Implementuje interfejs ActionListener.
 *
 */
public class OrderController implements ActionListener{
	
	private OrderModel model;
	private OrderView view;
	private OrderProductModel orderProductModel;
	
	
	/**
	 * @param orderProductModel the orderProductModel to set
	 */
	public void setOrderProductModel(OrderProductModel orderProductModel) {
		this.orderProductModel = orderProductModel;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(OrderModel model) {
		this.model = model;
	}
	/**
	 * @param view the view to set
	 */
	public void setView(OrderView view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		
		//table buttons panel
		if (source.equals(view.getTableButtonsPanel().getRemoveBttn())) {
			PurchaseOrder purchaseOrder = view.getSelectedOrder();
			if(purchaseOrder != null){
				model.removeOrder(purchaseOrder);
				model.notifyAllObservers();
			}
		}else if(source.equals(view.getTableButtonsPanel().getEditBttn())) {
			PurchaseOrder purchaseOrder = view.getSelectedOrder();
			if(purchaseOrder != null){
				view.showDialog(purchaseOrder, false);
			}
		} else if(source.equals(view.getTableButtonsPanel().getAddBttn())) {
			view.showDialog(new PurchaseOrder(), true);
			
		//dialog
		//ok cancel buttons
		} else if(source.equals(view.getDialog().getOkCancelButtonsPanel().getOkBttn())){
			if(view.getDialog().validateFields()){
				if(view.getDialog().isNew()){
					model.addOrder(view.getDialog().getOrder());
					model.notifyAllObservers();
				}else{
					model.updateOrder(view.getDialog().getContext(), view.getDialog().getOrder());
					model.notifyAllObservers();
				}
				view.getDialog().dispose();
			}
		} else if(source.equals(view.getDialog().getOkCancelButtonsPanel().getCancelBttn())){
			view.getDialog().dispose();
			
		//add remove from orderProductList buttons
		} else if(source.equals(view.getDialog().getAddBttn())){
			if(view.getDialog().validateProductFields() && !view.getDialog().isProductAdded(view.getDialog().getSelectedProduct().getCode())){
				OrderProduct orderProduct = new OrderProduct();
				orderProduct.setNumberOfProducts(Integer.parseInt(view.getDialog().getAmountTF().getText()));
				orderProduct.setProduct(view.getDialog().getSelectedProduct());
				orderProductModel.addOrderProduct(orderProduct);
				view.getDialog().addOrderProduct(orderProduct);;
			}
		} else if(source.equals(view.getDialog().getRemoveBttn())){
			OrderProduct orderProductOld = view.getDialog().removeSelectedOrderProduct();
			
			if(orderProductOld != null){
				OrderProduct orderProductNew = new OrderProduct();
				orderProductNew.setId(orderProductOld.getId());
				orderProductNew.setNumberOfProducts(orderProductOld.getNumberOfProducts());
				orderProductModel.updateOrderProduct(orderProductOld, orderProductNew);
				
				orderProductModel.removeOrderProduct(orderProductNew);
			}
		}
		
	}


}
