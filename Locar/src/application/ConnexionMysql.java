package application;

import java.sql.*;

public class ConnexionMysql {
	public Connection cn = null;
	public static Connection connexionDB(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/locationlogement","root","");
			System.out.println("connexion reussite");
			return cn;
		}catch(ClassNotFoundException |SQLException e){
			System.out.println("connexion echouee");
			e.printStackTrace();
			return null;
		}
	}

}
