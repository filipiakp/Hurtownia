package view;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Klasa OkCancelButtonsPanel to jak nazwa wskazuje, panel z przyciskami „OK” i „Anuluj”, wykorzystywana przez każdy dialog.
 *
 */
@SuppressWarnings("serial")
public class OkCancelButtonsPanel extends JPanel{
	
	private JButton okBttn, cancelBttn;
	
	public OkCancelButtonsPanel(ActionListener controller){
		okBttn = new JButton("OK");
		okBttn.addActionListener(controller);
		cancelBttn = new JButton("Anuluj");
		cancelBttn.addActionListener(controller);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(okBttn);
		add(cancelBttn);
	}

	/**
	 * @return the okBttn
	 */
	public JButton getOkBttn() {
		return okBttn;
	}

	/**
	 * @return the cancelBttn
	 */
	public JButton getCancelBttn() {
		return cancelBttn;
	}

}
