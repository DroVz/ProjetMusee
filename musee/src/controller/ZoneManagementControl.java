package controller;
import java.util.List;

import application.Main;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import museum.Art;
import museum.Room;
import museum.Spot;
import museum.Zone;

public class ZoneManagementControl {
	// Tree View
	@FXML 
	private TreeView<?> globalTreeView;
	
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
	
	public ZoneManagementControl() {
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
		this.initializeZoneTableView();
		this.initializeSpotTableView();
		this.initializeRoomChoiceBox();
		this.initializeZoneChoiceBox();
		this.initializeArtChoiceBox();
		this.initializeTreeView();
	}
	
	//Button Actions 
	@FXML
	private void handleCreateZone(ActionEvent event) {
		zoneAnchorPane.setDisable(false);
	}
	@FXML
	private void handleDeleteZone(ActionEvent event) {
		ZoneControl zoneControl = ZoneControl.getInstance();
		
		Zone selectedZone = zoneTableView.getItems().get(selectedZoneLine);
		zoneControl.deleteZone(selectedZone);
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
		
		String zoneName = inputNameZone.getText();
		Room room = roomChoiceBox.getValue();
		int zoneDimX = Integer.parseInt(inputDimXZone.getText());
		int zoneDimY = Integer.parseInt(inputDimYZone.getText());
		int zonePosX = Integer.parseInt(inputPosXZone.getText());
		int zonePosY = Integer.parseInt(inputPosYZone.getText());
		
		Zone zone = new Zone(zoneName,zoneDimX,zoneDimY,zonePosX,zonePosY, room);
		ZoneControl.getInstance().createZone(zone);
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
		SpotControl.getInstance().createSpot(spot);
	}
	
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
		List<Room> rooms = RoomControl.getInstance().readAll();
		List<Zone> zones = ZoneControl.getInstance().readAll();
		List<Spot> spots = SpotControl.getInstance().readAll();
		
		TreeItem globalItem = new TreeItem("Room");
		TreeItem roomItem;
		
		// Ajout des Rooms 
		for(Room room : rooms) {
			roomItem = new TreeItem(room.getName());
			globalItem.getChildren().add(roomItem);
		}
		
		// Ajout des Zones 
		for(Zone zone : zones) {
			for(Object item : globalItem.getChildren().toArray()) {
				if (((TreeItem) item).getValue().toString().equals(zone.getRoom().getName())) {
					((TreeItem) item).getChildren().add(new TreeItem (zone.getName()));
					for(Spot spot : spots) {
						for(Object subItem : ((TreeItem) item).getChildren().toArray()) {
							if(spot.getZone().getName().equals(zone.getName())) {
								((TreeItem) subItem).getChildren().add(new TreeItem (spot.getName()));
							}
						}
					}
				}
			}
		}
		
		globalTreeView.setRoot(globalItem);
	}
	
	private void refreshWindow() {
		this.initializeZoneTableView();
		this.initializeSpotTableView();
		this.initializeRoomChoiceBox();
		this.initializeZoneChoiceBox();
		this.initializeArtChoiceBox();
		this.initializeTreeView();
	}
	
}
