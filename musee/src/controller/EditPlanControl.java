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
	private double ratio = 1;
	
	private List<Pane> panesOfFloors = new ArrayList<Pane>();
	private List<Pane> panesOfRooms = new ArrayList<Pane>();
	private List<Pane> panesOfZones = new ArrayList<Pane>();
	private List<Pane> panesOfSpots = new ArrayList<Pane>();
	
	/**
	 * Constructeur : Défini la zone de dessin.
	 * @param drawSection
	 */
	public EditPlanControl(AnchorPane drawSection) {
		this.drawSection = drawSection;
	}
	
	/**
	 * Permet de rechercher dans les listes créées un pane avec comme id le paramèttre idPane.
	 * @param panes
	 * @param idPane
	 * @return
	 */
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

	/**
	 * Dessine un étage sur la zone de dessin.
	 * @param floor
	 */
	public void drawFloorOn(Floor floor) {
		
		// Création du pane 
		Pane pane = new Pane();
		pane.setId(floor.getName());
		pane.setPrefWidth(floor.getDim_x() * this.ratio);
		pane.setPrefHeight(floor.getDim_y() * this.ratio);
		pane.setStyle("-fx-background-color: #F3FBFF; -fx-border-color: #284b63 ");
		
		this.panesOfFloors.add(pane);
		this.drawSection.getChildren().add(pane);
	}

	/**
	 * Dessine une salle sur un pane parent.
	 * @param room 
	 * @param anchorPane 
	 */
	private void DrawRoomOn(Room room, AnchorPane anchorPane) {
		
		// Création du pane 
		Pane pane = new Pane();
		pane.setId(room.getName());
		pane.setLayoutX(room.getPos_x()*this.ratio);
		pane.setLayoutY(room.getPos_y()*this.ratio);
		pane.setPrefWidth(room.getDim_x() * this.ratio);
		pane.setPrefHeight(room.getDim_y() * this.ratio);
		pane.setStyle("-fx-background-color: #F3FBFF; -fx-border-color: #284b63 ");
		
		this.panesOfRooms.add(pane);
		anchorPane.getChildren().add(pane);
	}
	
	/**
	 * Dessine une zone sur un pane parent.
	 * @param zone
	 * @param parentPane
	 */
	private void DrawZoneOn(Zone zone, Pane parentPane) {
		
		// Création du pane 
		Pane pane = new Pane();
		pane.setId(zone.getName());
		pane.setLayoutX(zone.getPos_x() * this.ratio);
		pane.setLayoutY(zone.getPos_y() * this.ratio);
		pane.setPrefWidth(zone.getDim_x() * this.ratio);
		pane.setPrefHeight(zone.getDim_y() * this.ratio);
		pane.setStyle("-fx-background-color: #C3DDEE");
		// ; -fx-border-color: #284b63 
		this.panesOfZones.add(pane);
		parentPane.getChildren().add(pane);
	}
	
	/**
	 * Dessine un emplacement sur un pane parent.
	 * @param spot
	 * @param parentPane
	 */
	private void DrawSpotOn(Spot spot, Pane parentPane) {
		
		// Création du pane 
		Pane pane = new Pane();
		pane.setId(spot.getName());
		pane.setLayoutX(spot.getPos_x() * this.ratio);
		pane.setLayoutY(spot.getPos_y() * this.ratio);
		pane.setPrefWidth(spot.getDim_x() * this.ratio);
		pane.setPrefHeight(spot.getDim_y() * this.ratio);
		pane.setStyle("-fx-background-color: #3c6e71");
		// ; -fx-border-color: #D6FAFC 
		this.panesOfSpots.add(pane);
		parentPane.getChildren().add(pane);
	}
	
	/**
	 * Dessine la liste des salles envoyée dans leurs étages.
	 * @param rooms
	 */
	public void drawRoomsOn(List<Room> rooms) {
		for(Room room : rooms) {
			this.DrawRoomOn(room, this.drawSection);
		}
	}
	
	/**
	 * Dessine la liste des zones envoyée dans leurs salles respectives.
	 * @param zones
	 */
	public void drawZonesOn(List<Zone> zones) {
		for(Zone zone : zones) {
			this.DrawZoneOn(zone, this.findPane(panesOfRooms, zone.getRoom().getName()));
		}
	}
	
	/**
	 * Dessine la liste des emplacements envoyée dans leurs zones respectives.
	 * @param spots
	 */
	public void drawSpotsOn(List<Spot> spots) {
		for(Spot spot : spots) {
			this.DrawSpotOn(spot, this.findPane(panesOfZones, spot.getZone().getName()));
		}
	}

	/**
	 * Efface le contenu de la zone de dessin.
	 */
	public void CleanPlan() {
		this.drawSection.getChildren().setAll();
		this.panesOfFloors.clear();
		this.panesOfRooms.clear();
		this.panesOfZones.clear();
		this.panesOfSpots.clear();
	}
	
	/**
	 * Permet de définir un ratio pour adapter la taille des éléments à la zone de dessin. 
	 * @param floor 
	 */
	public void setRatioFitPage(Floor floor) {
		while (!floor.insidePane((int)this.drawSection.getPrefWidth(), (int)this.drawSection.getPrefHeight(),this.ratio)) {
			this.ratio -= 0.05;
		}
	}

}
