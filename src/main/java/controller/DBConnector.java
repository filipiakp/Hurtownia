package controller;

import java.sql.*;

public class DBConnector {
	//TODO: model komunikuje się z bazą dancyh!
	
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USER = "system";
	private final String PASS = "zaq1@WSX";
	private Object[][] zawartoscTabeli = null;//wynik zawierający wszystkie rekordy
	private int typyKolumn[] = null;
	private String tabela;
	
	
	public DBConnector(String tabela) throws SQLException{
		this.tabela = tabela;
		Connection connection = null;
		ResultSet rset;
		Statement statement = null;
		
		try{
			connection = DriverManager.getConnection(URL, USER, PASS);
			statement = connection.createStatement();
			statement.executeQuery("SELECT * FROM sqlplus_product_profile");//"SELECT * FROM Userx");
			
			rset = statement.getResultSet();
			System.out.println("wyników: "+rset.getFetchSize());
			System.out.println("kolumn: "+rset.getMetaData().getColumnCount());
			while(rset.next()){
				
				String prod = rset.getString(1);
				String uid = rset.getString(2);
				String attr = rset.getString(3);
				String scope = rset.getString(4);
				int intval = rset.getInt(5);
				String chrval = rset.getString(6);
				Date dtval = rset.getDate(7);
				long lngval = rset.getLong(8);
				
				System.out.println("prodd"+prod+" "+uid+" "+attr+" "+scope+" "+intval+" "+chrval+" "+dtval+" "+lngval);
				
				/*int id = rset.getInt(1);
				System.out.println("id "+ id);*/
			}
			
			
		}catch(SQLException e){
			System.err.println("Connection fail");
			e.printStackTrace();
		}finally{
			if ( statement!= null) { statement.close(); connection.close();}
		}
		
		
	}
	
	public Object[][] getZawartoscTabeli(){
		return zawartoscTabeli;
	}
	
	public static void main(String[] a){
		try { new DBConnector(""); }
		catch (SQLException e) { e.printStackTrace(); }
	}
	
	

}
