package view;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;


/**
 * Służy do wyświetlania Listy ZamówienieProdukt, którą przechowuje  Zamówienie.
 *
 */
@SuppressWarnings("serial")
public class ProductsListPanel extends JPanel{
	
	private JList<String> list;
	private DefaultListModel<String> listModel;
	
	/**
	 * Tworzy komponent graficzny listy ZamówienieProduct
	 */
	public ProductsListPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		list= new JList<String>();
		
		listModel = new DefaultListModel<String>();
		list.setModel(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane jsp = new JScrollPane(list);
		setMinimumSize(new Dimension(470,300));
		add(jsp);
		
		
	}
	
	/**
	 * @return the list
	 */
	public Set<String> getList() {
		Set<String> result = new HashSet<String>();
		
		for(int i= 0;i<list.getModel().getSize();i++){
			result.add((String)list.getModel().getElementAt(i));
		}
		
		return result;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(Set<String> list) {
		listModel.clear();
		if(list != null)
			for(String s:list){
				addProduct(s);
			}
		
	}
	
	/**
	 * Usuwa zaznaczony ZamówienieProdukt
	 * 
	 * @return String tekst usuniętego ZamówienieProdukt
	 */
	public String removeSelectedOrderProduct(){
		String result = null;
		if(list.getSelectedIndex() != -1){
			result = (String) listModel.getElementAt(list.getSelectedIndex());
			listModel.remove(list.getSelectedIndex());
		}
		return result;
			
	}
	
	/**
	 * @param orderProduct String
	 */
	public void addProduct(String orderProduct){
		listModel.add(listModel.size()==0?0:listModel.size()-1,orderProduct);//
		list.setSelectedIndex(listModel.size()-1);
	}
}
