package controller;

import java.util.List;

import dao.SpotDAO;
import museum.Spot;


public class SpotControl {
	
	private static SpotControl spotControl = null;
	
	public static SpotControl getInstance() {
		if (spotControl == null) {
			spotControl = new SpotControl();
		}
		return spotControl;
	}
	
	private SpotControl() {
	}
	
	public void deleteSpot(Spot spot) {	
		SpotDAO.getInstance().delete(spot);
	}
	
	public void createSpot(Spot spot) {
		SpotDAO.getInstance().create(spot) ;
	}

	public List<Spot> readAll() {
		return SpotDAO.getInstance().readAll();
	}

}
