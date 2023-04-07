package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class Connect {

	private static Connection connection = null;
	
	private static final String ID = "admin";
	private static final String MDP = "admin";

	/**
	 * Patron de conception Singleton
	 * @return l'instance unique de connexion
	 */
	public static Connection getInstance() {
		if (connection==null) {
			try { 
				SQLServerDataSource ds = new SQLServerDataSource();
				ds.setUser(ID);
				ds.setPassword(MDP);
				ds.setServerName("localhost");
				ds.setDatabaseName("museum");
				connection = ds.getConnection();
				System.out.println("connecté");
			}
			catch (SQLException e){
				System.out.println("Echec de la tentative de connexion : " + e.getMessage() + e.getStackTrace()) ;
			}
		}
		return connection;
	}
	
	private Connect() {
		super();
	}

	
	// TODO revoir le fichier à partir d'ici, je sais pas à quoi tout sert
	
	public static ResultSet executeQuery(String requete){
		Statement st = null ;
		ResultSet rs = null;
		//System.out.println("requete = "+requete);
		try{
			st = getInstance().createStatement() ;
			rs = st.executeQuery(requete) ;
		}catch(SQLException e){
			System.out.println("Echec de la tentative d'exécution de requete : " +requete + " ["+ e.getMessage()+"]") ;
		}
		return rs;
	}

	/**
	 * Une m�thode statique qui permet de faire une mise � jour d'une table (INSERT / UPDATE / DELETE)
	 * @param requete
	 * @return
	 */
	public static boolean executeUpdate(String requete){
		boolean succes = true;
		//System.out.println(requete);
		Statement st = null ;
		try{
			st = getInstance().createStatement() ;
			st.executeUpdate(requete) ;
		}catch(SQLException e){
			succes = false;
			System.out.println("Echec de la tentative d'ex�cution de Mise � Jour : " +requete + " ["+ e.getMessage()+"]") ;
		}
		return succes;
	}

	/**
	 * Fermeture de la connexion au SGBD SQL ServerExpress
	 */
	public static void fermer()
	{
		try
		{
			getInstance().close();
			System.out.println("deconnexion ok");
		}
		catch (SQLException e)
		{
			System.out.println("echec de la fermeture");
		}
		connection=null;
	}	
}
