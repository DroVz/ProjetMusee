package controllerModel;

import java.util.List;

import dao.RoomDAO;
import museum.Floor;
import museum.Room;

public class RoomControl {

	private static RoomControl roomControl = null;
	
	public static RoomControl getInstance() {
		if(roomControl == null) {
			roomControl = new RoomControl();
		}
		return roomControl;
	}
	
	private RoomControl() {
	}
	
	public void deleteRoom(Room room) {	
		RoomDAO.getInstance().delete(room);
	}
	
	public void createRoom(Room room) {
		RoomDAO.getInstance().create(room) ;
	}
	
	public void updateRoom(Room room) {
		RoomDAO.getInstance().update(room) ;
	}
	
	
	public List<Room> readAll(){
		
		return RoomDAO.getInstance().readAll();
	}
	
	public void deleteRoomOf(Floor floor) {
		for (Room room : RoomDAO.getInstance().readAll()) {
			if(room.getFloor().getId_floor() == floor.getId_floor()) {
				RoomDAO.getInstance().delete(room);
			}
		}
	}
}
