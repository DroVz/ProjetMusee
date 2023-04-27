package controller;

import java.util.List;

import dao.FloorDAO;
import museum.Floor;

public class FloorControl {

	private static FloorControl roomControl = null;
	
	public static FloorControl getInstance() {
		if(roomControl == null) {
			roomControl = new FloorControl();
		}
		return roomControl;
	}
	
	private FloorControl() {
	}
	
	public List<Floor> readAll(){
		
		return FloorDAO.getInstance().readAll();
	}
	
}
