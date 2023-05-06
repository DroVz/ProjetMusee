package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import museum.Room;
import museum.Spot;
import museum.Zone;

public class ZoneDAO extends DAO<Zone> {

	private static final String TABLE = "zone";
	private static final String PK = "id_zone";
	private static final String EK = "id_room";
	private static final String NAME = "zone_name";
	private static final String DIMX = "dim_x";
	private static final String DIMY = "dim_y";
	private static final String POSX = "pos_x";
	private static final String POSY = "pos_y";
	
	private static final String SPOTTABLE = "spot";
	private static final String IDSPOT = "id_spot";
	
	private static ZoneDAO instance  = null;
	
	public static ZoneDAO getInstance() {
		if (instance == null) {
			instance = new ZoneDAO();
		}
		return instance;
	}
	
	private ZoneDAO() {
		super();
	}

	@Override
	public boolean create(Zone zone) {
		boolean success = true;
		try {
			String requete = "INSERT INTO "+TABLE+" ("+NAME+", "+DIMX+", "+DIMY+", "+POSX+", "+POSY+", "+ EK +")"
						   + " VALUES (?, ?, ?, ?, ?, ?)";
			
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, zone.getName());
			pst.setInt(2, zone.getDim_x());
			pst.setInt(3, zone.getDim_y());
			pst.setInt(4, zone.getPos_x());
			pst.setInt(5, zone.getPos_y());
			pst.setInt(6, zone.getRoom().getId_room());
			pst.executeUpdate();
			// on récupère la clé générée et on la pousse dans l'objet initial
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				zone.setId(rs.getInt(1));
			}
			data.put(zone.getId(), zone);

		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;		
	}

	@Override
	public boolean delete(Zone zone) {
		boolean success = true;
		try {
			int id_zone = zone.getId();
			String requete = "DELETE FROM "+TABLE+" WHERE "+PK+" = ?";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete);
			pst.setInt(1, id_zone);
			pst.executeUpdate();
			data.remove(id_zone);
		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean update(Zone obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Zone read(int id) {
		Zone zone = null;
		
		if (data.containsKey(id)) {
			zone=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				String name = rs.getString(NAME);
				int dim_x = rs.getInt(DIMX);
				int dim_y = rs.getInt(DIMY);
				int pos_x = rs.getInt(POSX);
				int pos_y = rs.getInt(POSY);
				Room room = RoomDAO.getInstance().read( rs.getInt(EK));
				zone = new Zone(id, name, dim_x, dim_y, pos_x, pos_y, room);
				data.put(id, zone);
				
	            if (zone != null) {
	                requete = "SELECT * FROM " + SPOTTABLE + " WHERE " + PK + " = " + id;
	                rs = Connect.executeQuery(requete);
	                List<Spot> spots = new ArrayList<>();

	                while (rs.next()) {
	                    int spotId = rs.getInt(IDSPOT);
	                    Spot spot = SpotDAO.getInstance().read(spotId);
	                    spots.add(spot);
	                }

	                zone.setSpots(spots);
	            }

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return zone;
	}
	
	public List<Zone> readAll(){
		List<Zone> zoneObjects = new ArrayList<Zone>();
		Zone zone = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int idZone = rs.getInt(1);
				zone = this.read(idZone);
				zoneObjects.add(zone);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return zoneObjects;		
	}
}
