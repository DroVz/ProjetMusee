package controllerModel;

import java.util.List;

import dao.RoomDAO;
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
	
	public List<Room> readAll(){
		
		return RoomDAO.getInstance().readAll();
	}
	
}
