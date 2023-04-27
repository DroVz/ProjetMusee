package controllerModel;

import java.util.List;

import dao.ArtDAO;
import museum.Art;

public class ArtControl {
	
	private static ArtControl artControl = null;

	public static ArtControl getInstance() {
		if (artControl == null) {
			artControl = new ArtControl();
		}
		return artControl;
	}
	
	private ArtControl() {
		
	}
	
	public List<Art> readAll(){
		return ArtDAO.getInstance().readAll();
	}

}
