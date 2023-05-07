package controllerView;
import java.awt.Button;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import controller.EditPlanControl;
import dao.ArtDAO;
import dao.FloorDAO;
import dao.SpotDAO;
import dao.ZoneDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import museum.Area;
import museum.Art;
import museum.Floor;
import museum.Notify;
import museum.Room;
import museum.Spot;
import museum.Zone;

public class ShowRoomControl {
	// Tree View
	@FXML 
	private TreeView<String> globalTreeView;
	
	private Main mainControler;
	// Table View
	@FXML 
	private TableView<Zone> zoneTableView;
	@FXML 
	private TableView<Spot> spotTableView;
	
	//Table Column
	@FXML 
	private TableColumn<Zone, String> idZoneColumn;
	@FXML 
	private TableColumn<Zone, String> nameZoneColumn;
	@FXML 
	private TableColumn<Spot, String> idSpotColumn;
	@FXML 
	private TableColumn<Spot, String> nameSpotColumn;
	
	// Anchor Pane
	@FXML 
	private AnchorPane zoneAnchorPane;
	@FXML 
	private AnchorPane spotAnchorPane;
	@FXML
	private AnchorPane drawSection;

	// TextField 
	@FXML 
	private TextField inputNameZone;
	@FXML 
	private TextField inputDimXZone;
	@FXML 
	private TextField inputDimYZone;
	@FXML 
	private TextField inputPosXZone;
	@FXML 
	private TextField inputPosYZone;
	
	@FXML 
	private TextField inputNameSpot;
	@FXML 
	private TextField inputDimXSpot;
	@FXML 
	private TextField inputDimYSpot;
	@FXML 
	private TextField inputDimZSpot;
	@FXML 
	private TextField inputPosXSpot;
	@FXML 
	private TextField inputPosYSpot;
	@FXML 
	private TextField inputPosZSpot;
	
	//Button 
	@FXML 
	private Button createZone;
	@FXML 
	private Button deleteZone;
	@FXML 
	private Button confirmZone;
	@FXML 
	private Button cancelZone;
	
	@FXML 
	private Button createSpot;
	@FXML 
	private Button deleteSpot;
	@FXML 
	private Button confirmSpot;
	@FXML 
	private Button cancelSpot;
	
	//Choice Box
	@FXML
	private ChoiceBox<Room> roomChoiceBox;
	@FXML
	private ChoiceBox<Zone> zoneChoiceBox;
	@FXML 
	private ChoiceBox<Art> artChoiceBox;
	@FXML 
	private ChoiceBox<Floor> floorSelectedChoiceBox;
	
	// Private property 
	private EditPlanControl editPlanControl;
	private Floor selectedFloor;
	private List<Zone> zones = new ArrayList<Zone>();
	private List<Spot> spots = new ArrayList<Spot>();
	
	public ShowRoomControl() {
		super();
	
	}
	
	/**
	 * définit le contrôleur principal
	 * @param mainControler
	 */
	public void setMainControl(Main mainControler) {
		this.mainControler = mainControler;
	}
	
	@FXML 
	private void initialize() {
		
		// Création d'une nouvelle zone de dessin
		this.editPlanControl = new EditPlanControl(this.drawSection);
				
		// Initialisation des éléments FXML
		this.initializeFloorSelectedChoiceBox();
		this.initializeArtChoiceBox();
	}
	
	//Button Actions 
	@FXML
	private void handleCreateZone(ActionEvent event) {
		zoneAnchorPane.setDisable(false);
	}
	
	@FXML
	private void handleDeleteZone(ActionEvent event) {
		Zone selectedZone = zoneTableView.getSelectionModel().getSelectedItem();
		if(selectedZone != null) {
			zoneTableView.getItems().remove(selectedZone);
			zoneTableView.getSelectionModel().clearSelection();
			ZoneDAO.getInstance().delete(selectedZone);
			this.zones.remove(selectedZone);
			
			this.updateFxmlComponent();
		}
	}
	
	@FXML
	private void handleConfirmZone(ActionEvent event) {
		this.addZone();
		this.clearZoneTextField();
		zoneAnchorPane.setDisable(true);
		
		this.updateFxmlComponent();
	}
	
	@FXML
	private void handleCancelZone(ActionEvent event) {
		this.clearZoneTextField();
		zoneAnchorPane.setDisable(true);
	}

	
	@FXML
	private void handleCreateSpot(ActionEvent event) {
	spotAnchorPane.setDisable(false);
	}
	
	@FXML
	private void handleDeleteSpot(ActionEvent event) {
		Spot selectedSpot = spotTableView.getSelectionModel().getSelectedItem();
		if(selectedSpot != null) {
			spotTableView.getItems().remove(selectedSpot);
			spotTableView.getSelectionModel().clearSelection();
			SpotDAO.getInstance().delete(selectedSpot);
			this.spots.remove(selectedSpot);
			
			this.updateFxmlComponent();
		}
	}
	
	@FXML
	private void handleConfirmSpot(ActionEvent event) {
		this.addSpot();
		this.clearSpotTextField();
		spotAnchorPane.setDisable(true);
		
		this.updateFxmlComponent();
	}
	
	@FXML
	private void handleCancelSpot(ActionEvent event) {
		this.clearSpotTextField();
		spotAnchorPane.setDisable(true);
	}
	
	@FXML
	private void handleShowCuratorPane() {
		mainControler.showCuratorPane();
	}
	
	//Clicked Actions 
	@FXML 
	private void handleSetInputOfArt(ActionEvent event) {
		// L'évent onAction est pas écrit dans sceneBuilder.
		if (artChoiceBox.getValue() != null) {
			Art art = artChoiceBox.getValue();
			inputNameSpot.setText(art.getArt_title());
			inputDimXSpot.setText(art.getDim_x() + "");
			inputDimYSpot.setText(art.getDim_y() + "");
			inputDimZSpot.setText(art.getDim_z() + "");
		}
	}
	
	@FXML
	private void handleSetInputOfFloor(ActionEvent event) {
		this.selectedFloor = this.floorSelectedChoiceBox.getValue();
		System.out.println(this.selectedFloor == null);
		
		if (selectedFloor != null) {
			System.out.println(selectedFloor.getName());
			this.initializeListOfZones();
			this.initializeListOfSpots();
			
			this.initializeZoneTableView();
			this.initializeSpotTableView();
			
			this.initializeTreeView();
			
			this.initializeRoomChoiceBox();
			this.initializeZoneChoiceBox();
			
			this.initializePlan();
		}
	}
	
	/**
	 * Supprime le texte des composants zone TextField 
	 */
	private void clearZoneTextField() {
		inputNameZone.clear();
		inputDimXZone.clear();
		inputDimYZone.clear();
		inputPosXZone.clear();
		inputPosYZone.clear();
	}
	
	/**
	 * Supprime le texte des composants spot TextField 
	 */
	private void clearSpotTextField() {
		inputNameSpot.clear();
		inputDimXSpot.clear();
		inputDimYSpot.clear();
		inputDimZSpot.clear();
		inputPosXSpot.clear();
		inputPosYSpot.clear();
		inputPosZSpot.clear();
	}

	/**
	 * Ajoute une zone dans une room
	 */
	private void addZone() {
		try {
			// récupération des champs txt
			String zoneName = inputNameZone.getText();
			Room room = roomChoiceBox.getValue();
			int zoneDimX = Integer.parseInt(inputDimXZone.getText());
			int zoneDimY = Integer.parseInt(inputDimYZone.getText());
			int zonePosX = Integer.parseInt(inputPosXZone.getText());
			int zonePosY = Integer.parseInt(inputPosYZone.getText());
			
			// Création d'une zone 
			Zone zone = new Zone(zoneName,zoneDimX,zoneDimY,zonePosX,zonePosY, room);
			
			// Récupération des zones pour les comparer 
			List<Area> checkArea = new ArrayList<Area>(this.zones);
			
			// Vérifies que les zones ne se chevauche pas et que la zone est bien dans une salle
			if (!zone.overlaps(checkArea) && zone.insideParent()) {
				ZoneDAO.getInstance().create(zone);
				zoneTableView.getItems().add(zone);
				this.zones.add(zone);
			} else {
				this.clearZoneTextField();
				Notify.getInstance().showAlerte("Valeurs Incorectes","Valeurs Incorectes", "La dimension ou le positionnement de la zone est incorecte !");
			}
		}
		catch (NumberFormatException numberException) {
			mainControler.notifyFail("Valeurs inscrites incorectes");
		} 
	}
	
	/**
	 * Ajoute un spot dans une zone 
	 */
	private void addSpot() {
		
		String spotName = inputNameSpot.getText();
		Zone zone = zoneChoiceBox.getValue();
		Art art = artChoiceBox.getValue();
		int spotDimX = Integer.parseInt(inputDimXSpot.getText());
		int spotDimY = Integer.parseInt(inputDimYSpot.getText());
		int spotDimZ = Integer.parseInt(inputDimZSpot.getText());
		int spotPosX = Integer.parseInt(inputPosXSpot.getText());
		int spotPosY = Integer.parseInt(inputPosYSpot.getText());
		int spotPosZ = Integer.parseInt(inputPosZSpot.getText());
		
		Spot spot = new Spot(spotName,spotDimX,spotDimY,spotDimZ, spotPosX, spotPosY, spotPosZ, zone, art);
		 
		List<Area> checkArea = new ArrayList<Area>(this.spots);
		
		if (!spot.overlaps(checkArea) && spot.insideParent()) {
			SpotDAO.getInstance().create(spot);
			spotTableView.getItems().add(spot);
			this.spots.add(spot);
			
		} else {
			this.clearSpotTextField();
			Notify.getInstance().showAlerte("Valeurs Incorectes","Valeurs Incorectes", "La dimension ou le positionnement de l'emplacement est incorecte !");
		}
	}
	
	/**
	 * Initialisation du composant zone TableView 
	 */
	private void initializeZoneTableView() {
			this.zoneTableView.getItems().setAll(this.zones);
			idZoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()+""));
			nameZoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()+""));
	}
	
	/**
	 * Initialisation du composant spot TableView 
	 */
	private void initializeSpotTableView() {
		this.spotTableView.getItems().setAll(this.spots);
		idSpotColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()+""));
		nameSpotColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()+""));
	}
	
	/**
	 * Initialisation du composant room ChoiceBox 
	 */
	private void initializeRoomChoiceBox() {
		roomChoiceBox.getItems().setAll(this.selectedFloor.getRooms());
	}
	
	/**
	 * Initialisation du composant zone ChoiceBox 
	 */
	private void initializeZoneChoiceBox() {
		zoneChoiceBox.getItems().setAll(this.zones);
	}
	
	/**
	 * Initialisation du composant art ChoiceBox 
	 */
	private void initializeArtChoiceBox() {
		artChoiceBox.getItems().setAll(ArtDAO.getInstance().readAllAvailable());
	}
	
	private void initializeFloorSelectedChoiceBox() {
		List<Floor> floors = FloorDAO.getInstance().readAll();
		if(floors.size() != 0) {
			this.floorSelectedChoiceBox.getItems().clear();
			this.floorSelectedChoiceBox.getItems().setAll(floors);
		}
	}
	
	/**
	 * Initialisation du composant TreeView
	 */
	private void initializeTreeView() {
		// Liste pour retrouver l'adresse mémoire des objets créés
		ArrayList<TreeItem<String>> roomItems = new ArrayList<TreeItem<String>>();
		ArrayList<TreeItem<String>> zoneItems = new ArrayList<TreeItem<String>>();
		// Arbre éditable
		TreeItem<String> globalItem = new TreeItem<String>("Room");
		TreeItem<String>  treeItem;
		
		// Ajout des Rooms 
		for(Room room : this.selectedFloor.getRooms()) {
			treeItem = new TreeItem<String>(room.getName());
			globalItem.getChildren().add(treeItem);
			roomItems.add(treeItem);
		}
		
		// Ajout des Zones
		for(TreeItem<String> item : roomItems) {
			for(Zone zone : this.zones) {
				if (item.getValue().equals(zone.getRoom().getName())) {
					treeItem = new TreeItem<String>(zone.getName());
					item.getChildren().add(treeItem);
					zoneItems.add(treeItem);
				}
			}
		}
		// Ajout des spots
		for(TreeItem<String> zoneItem : zoneItems) {
			for(Spot spot : this.spots) {
				if (zoneItem.getValue().equals(spot.getZone().getName())) {
					treeItem = new TreeItem<String>(spot.getName());
					zoneItem.getChildren().add(treeItem);
				}
			}
		}
		
	
		globalTreeView.setRoot(globalItem);
	 }
	
	/**
	 * Initialisation du plan 2D
	 */
	private void initializePlan() {
		this.editPlanControl.CleanPlan();
		this.editPlanControl.setRatioFitPage(this.selectedFloor);
		this.editPlanControl.drawFloorOn(this.selectedFloor);
		this.editPlanControl.drawRoomsOn(this.selectedFloor.getRooms());
		this.editPlanControl.drawZonesOn(this.zones);
		this.editPlanControl.drawSpotsOn(this.spots);
	}
	
	/**
	 * Initialisation de la liste "zones"
	 */
	private void initializeListOfZones() {
		this.zones.clear();
		for(Room room : this.selectedFloor.getRooms()) {
			this.zones.addAll(room.getZones()) ;
		}
	}
	
	/**
	 * Initialisation de la liste "spots"
	 */
	private void initializeListOfSpots() {
		this.spots.clear();
		for(Zone zone : this.zones) {
			this.spots.addAll(zone.getSpots()) ;
		}
	}
	
	/**
	 * Maj du plan 2D et du composant TreeView.
	 */
	private void updateFxmlComponent() {
		this.initializePlan();
		this.initializeTreeView();
	}
	

	
}
