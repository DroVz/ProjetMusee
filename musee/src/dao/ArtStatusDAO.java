package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import museum.ArtStatus;

public class ArtStatusDAO extends DAO<ArtStatus> {
	
	private static final String TABLE = "art_status";
	private static final String PK = "id_art_status";
	private static final String NOM = "name";
	
	private static ArtStatusDAO instance=null;
	
	public static ArtStatusDAO getInstance(){
		if (instance==null){
			instance = new ArtStatusDAO();
		}
		return instance;
	}
	
	private ArtStatusDAO() {
		super();
	}

	@Override
	public boolean create(ArtStatus obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(ArtStatus obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(ArtStatus obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArtStatus read(int id) {
		ArtStatus art_status = null;
		if (data.containsKey(id)) {
			art_status=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				String nom = rs.getString(NOM);
				art_status = new ArtStatus(id, nom);
				data.put(id, art_status);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return art_status;
	}
	
	public List<ArtStatus> readAll() {
		List<ArtStatus> art_statuses = new ArrayList<ArtStatus>();
		ArtStatus art_status = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_art_status = rs.getInt(1);
				art_status = ArtStatusDAO.getInstance().read(id_art_status);
				art_statuses.add(art_status);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return art_statuses;
	}
}
