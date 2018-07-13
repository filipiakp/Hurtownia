package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import view.ModelObserver;

/**
 * Klasa odpowiadająca za zmianę i przechowywanie danych połączeniowych z bazą Oracle. 
 * Dane te są zapisywane w pliku tekstowym db.properties.
 * Implementuje interfejs „ModelInterface”.
 *
 */
public class SettingsModel implements ModelInterface{

	private ModelObserver observer;
	private Properties prop;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	/**
	 * Konstruktor bezparametrowy powoduje zainicjalizowanie zmiennych oraz załadowanie danych z pliku do obiektu typu Properties.
	 */
	public SettingsModel(){
		 super();
		 prop = new Properties();
		 try {
			fis = new FileInputStream("resources/db.properties");
			prop.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return String URL do bazy
	 */
	public String getURL(){
		return prop.getProperty("hibernate.connection.url");
	}
	
	/**
	 * @return String Nazwa użytkownika bazy
	 */
	public String getUsername(){
		return prop.getProperty("hibernate.connection.username");
	}
	
	/**
	 * 
	 * @return String hasło
	 */
	public String getPassword(){
		return prop.getProperty("hibernate.connection.password");
	}
	
	/**
	 * Metoda zapisuje podane wartości w pliku db.prop, jeśli zostały zmienione.
	 * 
	 * @param username - nazwa użytkownika
	 * @param password - hasło
	 * @param url - url połączenia
	 */
	public void setSettings(String username, String password, String url){
		if (!getUsername().equals(username) || !getPassword().equals(password) || !getURL().equals(url)) {
			
			try {
				fos = new FileOutputStream("resources/db.properties");
				prop.setProperty("hibernate.connection.url", url);
				prop.setProperty("hibernate.connection.username", username);
				prop.setProperty("hibernate.connection.password", password);
				prop.store(fos, null);
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Sprawdza połączenie z podanymi parametrami.
	 * 
	 * @param username - nazwa użutkownika
	 * @param password - hasło
	 * @param url - url połączenia
	 * @return String wiadomość dla użytkownika
	 */
	public String chceckConnnection(String username, String password, String url){
		String message = "";
		try {
			DriverManager.getConnection(url, username, password);
			message = "Próba połączenia zakończona sukcesem.";
			
		} catch (SQLException e) {
			message = "Nieprawidłowe dane.\n Sczegóły: "+e.getMessage();
			
		}
		return message;
	}

	@Override
	public void addObserver(ModelObserver observer) {
		this.observer = observer;
		
	}

	@Override
	public void removeObserver(ModelObserver observer) {
		observer = null;
		
	}

	@Override
	public void notifyAllObservers() {
		if(observer!=null)
			observer.update();
		
	}
}
