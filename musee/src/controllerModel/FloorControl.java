package controllerModel;

import java.util.List;

import dao.FloorDAO;
import museum.Floor;

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
	
	public void updateFloor(Floor floor) {
		FloorDAO.getInstance().update(floor);
		
		
	}
}
