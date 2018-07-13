
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.MainController;
import model.MainModel;
import view.MainView;

/**
 * Klasa <b>Main</b> odpowiada za rozruch aplikacji i
 * ustawienie wyglądu "look and feel".
 * 
 * nie posiada żadnych metod ani pól, poza główną metodą main(String[] args)
 * 
 */
public class Main {

	/**
	 * Metoda główna
	 * 
	 * Tworzy główny widok, model i kontroler oraz ustawia wygląd "look and feel" poprzez <b>UIManager</b>'a
	 * @param args Argumenty wiersza poleceń- zbędne
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
        MainModel model = new MainModel();
        MainController controller = new MainController();
        MainView view = new MainView(controller);
        view.setModel(model);
        controller.setView(view);
        controller.setModel(model);
        view.showGreetingsView();
    }

}
