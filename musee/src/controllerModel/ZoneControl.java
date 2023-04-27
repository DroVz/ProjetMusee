package controllerModel;

import java.util.List;

import dao.ZoneDAO;
import museum.Area;
import museum.Zone;

public class ZoneControl {

	private static ZoneControl zoneControl = null;
	
	public static ZoneControl getInstance() {
		if (zoneControl == null) {
			zoneControl = new ZoneControl();
		}
		return zoneControl;
	}
	
	private ZoneControl() {
		
	}
	
	public void deleteZone(Zone zone) {
	
		ZoneDAO.getInstance().delete(zone);
	}
	
	public void createZone(Zone zone) {
		ZoneDAO.getInstance().create(zone) ;
	}
	public List<Zone> readAll(){
		return ZoneDAO.getInstance().readAll();
	}
}
