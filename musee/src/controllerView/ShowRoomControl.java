package controllerView;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import controller.EditPlanControl;
import controllerModel.ArtControl;
import controllerModel.Notify;
import controllerModel.RoomControl;
import controllerModel.SpotControl;
import controllerModel.ZoneControl;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import museum.Area;
import museum.Art;
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
	
	// Anchor Pane State 
	private boolean stateOfZoneAnchorPane = false;
	private boolean stateOfSpotAnchorPane = false;
	
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
	
	// Private property 
	private int selectedZoneLine = 0; 
	private int selectedSpotLine = 0; 
	private EditPlanControl editPlanControl;
	
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
		// Initialisation des éléments FXML
		this.initializeZoneTableView();
		this.initializeSpotTableView();
		this.initializeRoomChoiceBox();
		this.initializeZoneChoiceBox();
		this.initializeArtChoiceBox();
		this.initializeTreeView();
		
		// Chargement du plan 2D
		this.editPlanControl = new EditPlanControl(this.drawSection);
		this.initializePlan();
		
	}
	
	//Button Actions 
	@FXML
	private void handleCreateZone(ActionEvent event) {
		zoneAnchorPane.setDisable(false);
	}
	@FXML
	private void handleDeleteZone(ActionEvent event) {
		
		Zone selectedZone = zoneTableView.getItems().get(selectedZoneLine);
		SpotControl.getInstance().deleteSpotOf(selectedZone);
		ZoneControl.getInstance().deleteZone(selectedZone);
		selectedZoneLine = 0;
		
		this.refreshWindow();
	}
	@FXML
	private void handleConfirmZone(ActionEvent event) {
		this.addZone();
		this.refreshWindow();
		this.resetZoneTextField();
		zoneAnchorPane.setDisable(true);
	}
	@FXML
	private void handleCancelZone(ActionEvent event) {
		this.resetZoneTextField();
		zoneAnchorPane.setDisable(true);
	}
	
	private void resetZoneTextField() {
		inputNameZone.setText("");
		inputDimXZone.setText("");
		inputDimYZone.setText("");
		inputPosXZone.setText("");
		inputPosYZone.setText("");
	}
	private void setZoneError() {
		inputDimXZone.setText("Erreur");
		inputDimYZone.setText("Erreur");
		inputPosXZone.setText("Erreur");
		inputPosYZone.setText("Erreur");
	}
	
	@FXML
	private void handleCreateSpot(ActionEvent event) {
	spotAnchorPane.setDisable(false);
	}
	@FXML
	private void handleDeleteSpot(ActionEvent event) {
		SpotControl spotControl = SpotControl.getInstance();
		
		museum.Spot selectedSpot = spotTableView.getItems().get(selectedSpotLine);
		spotControl.deleteSpot(selectedSpot);
		selectedSpotLine = 0;
		
		this.refreshWindow();
	}
	@FXML
	private void handleConfirmSpot(ActionEvent event) {
		this.addSpot();
		this.refreshWindow();
		this.resetSpotTextField();
		spotAnchorPane.setDisable(true);
	}
	@FXML
	private void handleCancelSpot(ActionEvent event) {
		this.resetSpotTextField();
		spotAnchorPane.setDisable(true);
	}
	private void resetSpotTextField() {
		inputNameSpot.setText("");
		inputDimXSpot.setText("");
		inputDimYSpot.setText("");
		inputDimZSpot.setText("");
		inputPosXSpot.setText("");
		inputPosYSpot.setText("");
		inputPosZSpot.setText("");
	}
	private void setSpotError() {
		inputDimXSpot.setText("Erreur");
		inputDimYSpot.setText("Erreur");
		inputPosXSpot.setText("Erreur");
		inputPosYSpot.setText("Erreur");
	}
	//Clicked Actions 
	@FXML 
	private void handleSelectZoneItem(MouseEvent event) {
		selectedZoneLine = zoneTableView.getSelectionModel().getSelectedIndex();
	}
	@FXML 
	private void handleSelectSpotItem(MouseEvent event) {
		selectedSpotLine = spotTableView.getSelectionModel().getSelectedIndex();
	}
	
	
	
	// private sub and function 
	private void addZone() {
		try {
			String zoneName = inputNameZone.getText();
			Room room = roomChoiceBox.getValue();
			int zoneDimX = Integer.parseInt(inputDimXZone.getText());
			int zoneDimY = Integer.parseInt(inputDimYZone.getText());
			int zonePosX = Integer.parseInt(inputPosXZone.getText());
			int zonePosY = Integer.parseInt(inputPosYZone.getText());
			
			Zone zone = new Zone(zoneName,zoneDimX,zoneDimY,zonePosX,zonePosY, room);
			List<Area> checkArea = new ArrayList<Area>(ZoneControl.getInstance().readAll());
			if (!zone.overlaps(checkArea) && zone.insideParent()) {
				ZoneControl.getInstance().createZone(zone);
			} else {
				this.resetZoneTextField();
				this.setZoneError();
			}
		}
		catch (NumberFormatException numberException) {
			mainControler.notifyFail("Valeurs inscrites incorectes");
		} 
	}
	
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
		 
		List<Area> checkArea = new ArrayList<Area>(SpotControl.getInstance().readAll());
		if (!spot.overlaps(checkArea) && spot.insideParent()) {
			SpotControl.getInstance().createSpot(spot);
		} else {
			this.resetSpotTextField();
			this.setSpotError();
			Notify.GetInstance().showNotif("Erreur emplacement", "Le dimension ou le positionnement de l'emplacement est incorecte !");
		}
	}
	
	// Initialisation des éléments 
	private void initializeZoneTableView() {
			this.zoneTableView.getItems().setAll(ZoneControl.getInstance().readAll());
			idZoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()+""));
			nameZoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()+""));
	}
	private void initializeSpotTableView() {
		this.spotTableView.getItems().setAll(SpotControl.getInstance().readAll());
		idSpotColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()+""));
		nameSpotColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()+""));
}
	
	private void initializeRoomChoiceBox() {
		roomChoiceBox.getItems().setAll(RoomControl.getInstance().readAll());
	}
	
	private void initializeZoneChoiceBox() {
		zoneChoiceBox.getItems().setAll(ZoneControl.getInstance().readAll());
	}
	
	private void initializeArtChoiceBox() {
		artChoiceBox.getItems().setAll(ArtControl.getInstance().readAll());
	}
	
	private void initializeTreeView() {
		// Récupère les éléments du musée 
		List<Room> rooms = RoomControl.getInstance().readAll();
		List<Zone> zones = ZoneControl.getInstance().readAll();
		List<Spot> spots = SpotControl.getInstance().readAll();
		// Liste pour retrouver l'adresse mémoire des objets créés
		ArrayList<TreeItem<String>> roomItems = new ArrayList<TreeItem<String>>();
		ArrayList<TreeItem<String>> zoneItems = new ArrayList<TreeItem<String>>();
		// Arbre éditable
		TreeItem<String> globalItem = new TreeItem<String>("Room");
		TreeItem<String>  treeItem;
		
		// Ajout des Rooms 
		for(Room room : rooms) {
			treeItem = new TreeItem<String>(room.getName());
			globalItem.getChildren().add(treeItem);
			roomItems.add(treeItem);
		}
		
		// Ajout des Zones
		for(TreeItem<String> item : roomItems) {
			for(Zone zone : zones) {
				if (item.getValue().equals(zone.getRoom().getName())) {
					treeItem = new TreeItem<String>(zone.getName());
					item.getChildren().add(treeItem);
					zoneItems.add(treeItem);
				}
			}
		}
		
		// Ajout des spots
		for(TreeItem<String> item : zoneItems) {
			for(Spot spot : spots) {
				if (item.getValue().equals(spot.getZone().getName())) {
					treeItem = new TreeItem<String>(spot.getName());
					item.getChildren().add(treeItem);
				}
			}
		}
		
	
		globalTreeView.setRoot(globalItem);
	}
	
	private void initializePlan() {
		this.editPlanControl.drawRoomsOn(RoomControl.getInstance().readAll());
		this.editPlanControl.drawZonesOn(ZoneControl.getInstance().readAll());
		this.editPlanControl.drawSpotsOn(SpotControl.getInstance().readAll());
	}
	
	private void refreshWindow() {
		this.initializeZoneTableView();
		this.initializeSpotTableView();
		this.initializeRoomChoiceBox();
		this.initializeZoneChoiceBox();
		this.initializeArtChoiceBox();
		this.initializeTreeView();
		this.editPlanControl.CleanPlan();
		this.initializePlan();
		}
	
}
