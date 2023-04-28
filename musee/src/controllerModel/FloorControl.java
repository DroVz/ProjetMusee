package controllerModel;

import java.util.List;

import dao.FloorDAO;
import museum.Floor;
import museum.Room;

public class FloorControl {

	private static FloorControl instance = null;
	
	public static FloorControl getInstance() {
		if (instance == null) {
			instance = new FloorControl();
		}
		return instance;
	}
	
	private FloorControl() {
		
	}
	
	public void deleteFloor(Floor floor) {
	
		FloorDAO.getInstance().delete(floor);
	}
	
	public void createFloor(Floor floor) {
		FloorDAO.getInstance().create(floor) ;
	}
	public List<Floor> readAll(){
		return FloorDAO.getInstance().readAll();
	}
	
	public List<Room> readRoomsBy(Floor floor){
		return FloorDAO.getInstance().readRoomsBy(floor);
	}

	public void updateFloor(Floor floor) {
		FloorDAO.getInstance().update(floor);
		
		
	}
}
