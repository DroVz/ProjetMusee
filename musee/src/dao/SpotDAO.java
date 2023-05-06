package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import museum.Art;
import museum.Spot;
import museum.Zone;

public class SpotDAO extends DAO<Spot> {
	
	private static final String TABLE = "spot";
	private static final String PK = "id_spot";
	private static final String NAME = "spot_name";
	private static final String DIMX = "dim_x";
	private static final String DIMY = "dim_y";
	private static final String DIMZ = "dim_z";
	private static final String POSX = "pos_x";
	private static final String POSY = "pos_y";
	private static final String POSZ = "pos_z";
	
	private static final String IDART = "id_art";
	private static final String IDZONE = "id_zone";
	
private static SpotDAO instance  = null;
	
	public static SpotDAO getInstance() {
		if (instance == null) {
			instance = new SpotDAO();
		}
		return instance;
	}
	
	private SpotDAO() {
		super();
	}

	@Override
	public boolean create(Spot spot) {
		boolean success = true;
		try {
			String requete = "INSERT INTO "+TABLE+" ("+NAME+", "+DIMX+", "+DIMY+", "+ DIMZ +", "+POSX+", "+POSY+", "+POSZ+", "+ IDART +", "+ IDZONE +")"
						   + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, spot.getName());
			pst.setInt(2, spot.getDim_x());
			pst.setInt(3, spot.getDim_y());
			pst.setInt(4, spot.getDim_z());
			pst.setInt(5, spot.getPos_x());
			pst.setInt(6, spot.getPos_y());
			pst.setInt(7, spot.getPos_z());
			pst.setInt(8, spot.getArt().getId_art());
			pst.setInt(9, spot.getZone().getId());
			pst.executeUpdate();
			// on récupère la clé générée et on la pousse dans l'objet initial
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				spot.setId(rs.getInt(1));
			}
			data.put(spot.getId(), spot);

		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;	
	}

	@Override
	public boolean delete(Spot spot) {
		boolean success = true;
		try {
			int id_spot = spot.getId();
			String requete = "DELETE FROM "+TABLE+" WHERE "+PK+" = ?";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete);
			pst.setInt(1, id_spot);
			pst.executeUpdate();
			data.remove(id_spot);
		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean update(Spot obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Spot read(int id) {
		Spot spot = null;
		if (data.containsKey(id)) {
			spot=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				String spot_name = rs.getString(NAME);
				int dim_x = rs.getInt(DIMX);
				int dim_y = rs.getInt(DIMY);
				int dim_z = rs.getInt(DIMZ);
				int pos_x = rs.getInt(POSX);
				int pos_y = rs.getInt(POSY);
				int pos_z = rs.getInt(POSZ);
				
				Art art = ArtDAO.getInstance().read(rs.getInt(IDART));
				Zone zone = ZoneDAO.getInstance().read(rs.getInt(IDZONE));
				
				spot = new Spot(id, spot_name, dim_x, dim_y, dim_z, pos_x, pos_y, pos_z, art, zone);
						
				data.put(id, spot);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return spot;
	}
	
	public List<Spot> readAll(){
		List<Spot> spotObjects = new ArrayList<Spot>();
		Spot spot = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int idSpot = rs.getInt(1);
				spot = this.read(idSpot);
				spotObjects.add(spot);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return spotObjects;		
	}

}
