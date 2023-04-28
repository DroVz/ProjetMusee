package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import museum.Floor;
import museum.Room;
import museum.Spot;
import museum.Zone;

public class EditPlanControl {
	
	private AnchorPane drawSection;
	
	private List<Pane> panesOfFloors = new ArrayList<Pane>();
	private List<Pane> panesOfRooms = new ArrayList<Pane>();
	private List<Pane> panesOfZones = new ArrayList<Pane>();
	private List<Pane> panesOfSpots = new ArrayList<Pane>();
	
	public EditPlanControl(AnchorPane drawSection) {
		this.drawSection = drawSection;
	}
	
	public void drawFloorOn(Floor floor) {
		
		// Création du pane 
		Pane pane = new Pane();
		pane.setId(floor.getName());
		pane.setPrefWidth(floor.getDim_x());
		pane.setPrefHeight(floor.getDim_y());
		pane.setStyle("-fx-background-color: #F3FBFF; -fx-border-color: #284b63 ");
		
		this.panesOfFloors.add(pane);
		this.drawSection.getChildren().add(pane);
	}
	
	
	
	private void DrawRoomOn(Room room, AnchorPane anchorPane) {
		
		// Création du pane 
		Pane pane = new Pane();
		pane.setId(room.getName());
		pane.setLayoutX(room.getPos_x());
		pane.setLayoutY(room.getPos_y());
		pane.setPrefWidth(room.getDim_x());
		pane.setPrefHeight(room.getDim_y());
		pane.setStyle("-fx-background-color: #F3FBFF; -fx-border-color: #284b63 ");
		
		this.panesOfRooms.add(pane);
		anchorPane.getChildren().add(pane);
	}
	private void DrawZoneOn(Zone zone, Pane parentPane) {
		
		// Création du pane 
		Pane pane = new Pane();
		pane.setId(zone.getName());
		pane.setLayoutX(zone.getPos_x());
		pane.setLayoutY(zone.getPos_y());
		pane.setPrefWidth(zone.getDim_x());
		pane.setPrefHeight(zone.getDim_y());
		pane.setStyle("-fx-background-color: #C3DDEE");
		// ; -fx-border-color: #284b63 
		this.panesOfZones.add(pane);
		parentPane.getChildren().add(pane);
	}
	private void DrawSpotOn(Spot spot, Pane parentPane) {
		
		// Création du pane 
		Pane pane = new Pane();
		pane.setId(spot.getName());
		pane.setLayoutX(spot.getPos_x());
		pane.setLayoutY(spot.getPos_y());
		pane.setPrefWidth(spot.getDim_x());
		pane.setPrefHeight(spot.getDim_y());
		pane.setStyle("-fx-background-color: #3c6e71");
		// ; -fx-border-color: #D6FAFC 
		this.panesOfSpots.add(pane);
		parentPane.getChildren().add(pane);
	}
	
	
	public void drawRoomsOn(List<Room> rooms) {
		for(Room room : rooms) {
			this.DrawRoomOn(room, this.drawSection);
		}
	}
	
	public void drawZonesOn(List<Zone> zones) {
		for(Zone zone : zones) {
			this.DrawZoneOn(zone, this.findPane(panesOfRooms, zone.getRoom().getName()));
		}
	}
	
	public void drawSpotsOn(List<Spot> spots) {
		for(Spot spot : spots) {
			this.DrawSpotOn(spot, this.findPane(panesOfZones, spot.getZone().getName()));
		}
	}
	
	private Pane findPane(List<Pane> panes, String idPane) {
		Pane paneFind = null;
		
		try {
			for (Pane pane : panes) {
				if (pane.getId().equals(idPane)) {
					paneFind = pane;
				}
			}
		} catch (Exception e) {
			System.out.println("findPane() On " + this.getClass().getName());
			e.printStackTrace();
		}
		return paneFind;
	}
	
	public void CleanPlan() {
		this.drawSection.getChildren().setAll();
		this.panesOfFloors.clear();
		this.panesOfRooms.clear();
		this.panesOfZones.clear();
		this.panesOfSpots.clear();
	}
	

}
