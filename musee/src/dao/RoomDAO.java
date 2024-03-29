package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import museum.Floor;
import museum.Room;
import museum.Zone;

public class RoomDAO extends DAO<Room> {
	
	private static final String TABLE = "room";
	private static final String ZONETABLE = "zone";
	private static final String PK = "id_room";
	private static final String NAME = "name";
	private static final String IDFLOOR = "id_floor";
	private static final String DIMX = "dim_x";
	private static final String DIMY = "dim_y";
	private static final String DIMZ = "dim_z";
	private static final String POSX = "pos_x";
	private static final String POSY = "pos_y";	
	
	private static RoomDAO instance=null;

	public static RoomDAO getInstance(){
		if (instance==null){
			instance = new RoomDAO();
		}
		return instance;
	}

	private RoomDAO() {
		super();
	}

	@Override
	public boolean create(Room room) {
		boolean success = true;
		try {
			String requete = "INSERT INTO "+TABLE+" ("+NAME+", "+IDFLOOR+", "+DIMX+", "+DIMY+", "+DIMZ
					+", "+POSX+", "+POSY+") VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, room.getName());
			pst.setInt(2, room.getFloor().getId_floor());
			pst.setInt(3, room.getDim_x());
			pst.setInt(4, room.getDim_y());
			pst.setInt(5, room.getDim_z());
			pst.setInt(6, room.getPos_x());
			pst.setInt(7, room.getPos_y());
			pst.executeUpdate();
			// on récupère la clé générée et on la pousse dans l'objet initial
			ResultSet rs = pst.getGeneratedKeys();
			if (rs.next()) {
				room.setId_room(rs.getInt(1));
			}
			data.put(room.getId_room(), room);

		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean delete(Room room) {
		boolean success = true;
		try {
			int id_room = room.getId_room();
			String requete = "DELETE FROM "+TABLE+" WHERE "+PK+" = ?";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete);
			pst.setInt(1, id_room);
			pst.executeUpdate();
			data.remove(id_room);
		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean update(Room room) {
		boolean success = true;
		try {
			String requete = "UPDATE "+TABLE+" SET "+NAME+"= ?,"+IDFLOOR+"= ?,"+DIMX+"= ?,"+DIMY+"= ?,"+DIMZ
					+"= ?,"+POSX+"= ?,"+POSY+"= ? WHERE "+PK+"= ?";
			PreparedStatement pst = Connect.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, room.getName());
			pst.setInt(2, room.getFloor().getId_floor());
			pst.setInt(3, room.getDim_x());
			pst.setInt(4, room.getDim_y());
			pst.setInt(5, room.getDim_z());
			pst.setInt(6, room.getPos_x());
			pst.setInt(7, room.getPos_y());
			pst.setInt(8, room.getId_room());
			pst.executeUpdate();
			data.put(room.getId_room(), room);
		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public Room read(int id) {
		Room room = null;
		if (data.containsKey(id)) {
			room=data.get(id);
		}
		else {
			try {
				String requete = "SELECT * FROM " + TABLE + " WHERE " + PK + " = " + id;
				ResultSet rs = Connect.executeQuery(requete);
				rs.next();
				String nom = rs.getString(NAME);
				int dim_x = rs.getInt(DIMX);
				int dim_y = rs.getInt(DIMY);
				int dim_z = rs.getInt(DIMZ);
				int pos_x = rs.getInt(POSX);
				int pos_y = rs.getInt(POSY);
				
				Floor floor = FloorDAO.getInstance().read(rs.getInt(IDFLOOR));
				room = new Room(id, nom, floor, dim_x, dim_y, dim_z, pos_x, pos_y);
				data.put(id, room);
				
				if (room != null) {
	                requete = "SELECT * FROM " + ZONETABLE + " WHERE " + PK + " = " + id;
	                rs = Connect.executeQuery(requete);
	                List<Zone> zones = new ArrayList<>();

	                while (rs.next()) {
	                    int zoneId = rs.getInt("id_zone");
	                    Zone zone = ZoneDAO.getInstance().read(zoneId);
	                    zones.add(zone);
	                }
	                room.setZones(zones);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return room;
	}
	
	public List<Room> readAll() {
		List<Room> rooms = new ArrayList<Room>();
		Room room = null;
		try {			
			String requete = "SELECT * FROM " + TABLE;
			ResultSet rs = Connect.executeQuery(requete);
			while(rs.next()) {
				int id_room = rs.getInt(1);
				room = RoomDAO.getInstance().read(id_room);
				rooms.add(room);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Échec de la tentative d'interrogation Select * : " + e.getMessage()) ;
		}
		return rooms;		
	}
}
