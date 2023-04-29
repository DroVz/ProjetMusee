package controllerView;

import java.util.ArrayList;
import java.util.List;

import application.Main;
import controller.EditPlanControl;
import dao.FloorDAO;
import dao.RoomDAO;
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
import museum.Floor;
import museum.Notify;
import museum.Room;

public class ArchitectControl {
	
	// Tree View
		@FXML 
		private TreeView<String> globalTreeView;
		
		private Main mainControler;
		// Table View
		@FXML 
		private TableView<Floor> floorTableView;
		@FXML 
		private TableView<Room> roomTableView;
		
		//Table Column
		@FXML 
		private TableColumn<Floor, String> idFloorColumn;
		@FXML 
		private TableColumn<Floor, String> nameFloorColumn;
		@FXML 
		private TableColumn<Room, String> idRoomColumn;
		@FXML 
		private TableColumn<Room, String> nameRoomColumn;
		
		// Anchor Pane
		@FXML 
		private AnchorPane floorAnchorPane;
		@FXML 
		private AnchorPane roomAnchorPane;
		@FXML
		private AnchorPane drawSection;

		// TextField 
		@FXML 
		private TextField inputNameFloor;
		@FXML 
		private TextField inputDimXFloor;
		@FXML 
		private TextField inputDimYFloor;
		@FXML 
		private TextField inputDimZFloor;
		
		
		@FXML 
		private TextField inputNameRoom;
		@FXML 
		private TextField inputDimXRoom;
		@FXML 
		private TextField inputDimYRoom;
		@FXML 
		private TextField inputDimZRoom;
		@FXML 
		private TextField inputPosXRoom;
		@FXML 
		private TextField inputPosYRoom;
		@FXML 
		private TextField inputPosZRoom;
		
		//Button 
		@FXML 
		private Button createFloor;
		@FXML 
		private Button deleteFloor;
		@FXML 
		private Button updateFloor;
		@FXML 
		private Button confirmFloor;
		@FXML 
		private Button cancelFloor;
		
		@FXML 
		private Button createRoom;
		@FXML 
		private Button deleteRoom;
		@FXML 
		private Button updateRoom;
		@FXML 
		private Button confirmRoom;
		@FXML 
		private Button cancelRoom;
		
		//Choice Box
		@FXML
		private ChoiceBox<Floor> floorChoiceBox;
		
		@FXML
		private ChoiceBox<Floor> SelectedFloorChoiceBox;
		
		@FXML
		private ChoiceBox<Room> roomChoiceBox;


		// Private property 
		private int selectedFloorLine = 0; 
		private int selectedRoomLine = 0; 
		private EditPlanControl editPlanControl;
		private boolean updateRoomButtonSelected = false;
		private boolean updateFloorButtonSelected = false;
	
			public ArchitectControl() {
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
				this.initializeFloorTableView();
				this.initializeRoomTableView();
				this.initializeFloorChoiceBox();
				this.initializeSelectedFloorChoiceBox();
				this.initializeTreeView();
				// Chargement du plan 2D
				this.editPlanControl = new EditPlanControl(this.drawSection);
//				Floor floor = FloorControl.getInstance().readAll().get(0);
//				if (!(floor == null)) {
//					this.editPlanControl.setRatioFitPage(FloorControl.getInstance().readAll().get(0));
//					this.initializePlan();
//				}
//				
			}
			
			//Button Actions 
			@FXML
			private void handleCreateFloor(ActionEvent event) {
				this.updateFloorButtonSelected = false;
				floorAnchorPane.setDisable(false);
			}
			@FXML
			private void handleDeleteFloor(ActionEvent event) {
				
				Floor selectedFloor = floorTableView.getItems().get(selectedFloorLine);
				FloorDAO.getInstance().delete(selectedFloor);
				selectedFloorLine = 0;
				
				this.refreshWindow();
			}
			@FXML
			private void handleUpdateFloor(ActionEvent event) {
				Floor selectedFloor = floorTableView.getItems().get(selectedFloorLine);
				floorAnchorPane.setDisable(false);
				inputNameFloor.setText(selectedFloor.getName());
				inputDimXFloor.setText(""+selectedFloor.getDim_x());
				inputDimYFloor.setText(""+selectedFloor.getDim_y());
				inputDimZFloor.setText(""+selectedFloor.getDim_z());
				this.updateFloorButtonSelected = true;
			}
			@FXML
			private void handleConfirmFloor(ActionEvent event) {
				
				if(this.updateFloorButtonSelected == false) {
					this.addFloor();
				}else { 
					this.updateFloor();
					
				}
				this.refreshWindow();
				this.resetFloorTextField();
				floorAnchorPane.setDisable(true);
			}
			@FXML
			private void handleCancelFloor(ActionEvent event) {
				this.resetFloorTextField();
				floorAnchorPane.setDisable(true);
			}
			
			private void resetFloorTextField() {
				inputNameFloor.setText("");
				inputDimXFloor.setText("");
				inputDimYFloor.setText("");
				inputDimZFloor.setText("");
				
			}
			private void setFloorError() {
				inputDimXFloor.setText("Erreur");
				inputDimYFloor.setText("Erreur");
				inputDimZFloor.setText("Erreur");
			}
			
			@FXML
			private void handleCreateRoom(ActionEvent event) {
			this.updateRoomButtonSelected = false;
			roomAnchorPane.setDisable(false);
			}
			@FXML
			private void handleDeleteRoom(ActionEvent event) {
				
				museum.Room selectedRoom = roomTableView.getItems().get(selectedRoomLine);
				RoomDAO.getInstance().delete(selectedRoom);
				selectedRoomLine = 0;
				
				this.refreshWindow();
			}
			@FXML
			private void handleUpdateRoom(ActionEvent event) {
				Room selectedRoom = roomTableView.getItems().get(selectedRoomLine);
				roomAnchorPane.setDisable(false);
				inputNameRoom.setText(selectedRoom.getName());
				inputDimXRoom.setText(""+selectedRoom.getDim_x());
				inputDimYRoom.setText(""+selectedRoom.getDim_y());
				inputDimZRoom.setText(""+selectedRoom.getDim_z());
				inputPosXRoom.setText(""+selectedRoom.getPos_x());
				inputPosYRoom.setText(""+selectedRoom.getPos_y());
				floorChoiceBox.getSelectionModel().select(selectedRoom.getFloor());
				this.updateRoomButtonSelected = true ; 
			}
			@FXML
			private void handleConfirmRoom(ActionEvent event) {
				if( this.updateRoomButtonSelected == false) {
					this.addRoom();
				} else {
					this.updateRoom();
				}
				this.refreshWindow();
				this.resetRoomTextField();
				roomAnchorPane.setDisable(true);
				
			}
			@FXML
			private void handleCancelRoom(ActionEvent event) {
				this.resetRoomTextField();
				roomAnchorPane.setDisable(true);
			}
			private void resetRoomTextField() {
				inputNameRoom.setText("");
				inputDimXRoom.setText("");
				inputDimYRoom.setText("");
				inputDimZRoom.setText("");
				inputPosXRoom.setText("");
				inputPosYRoom.setText("");
			}
			private void setRoomError() {
				inputDimXRoom.setText("Erreur");
				inputDimYRoom.setText("Erreur");
				inputPosXRoom.setText("Erreur");
				inputPosYRoom.setText("Erreur");
				
			}
			//Clicked Actions 
			@FXML 
			private void handleSelectFloorItem(MouseEvent event) {
				selectedFloorLine = floorTableView.getSelectionModel().getSelectedIndex();
			}
			@FXML 
			private void handleSelectRoomItem(MouseEvent event) {
				selectedRoomLine = roomTableView.getSelectionModel().getSelectedIndex();
			}
			
			
		

			// private sub and function 
			private void addFloor() {
				try {
					String floorName = inputNameFloor.getText();
					int floorDimX = Integer.parseInt(inputDimXFloor.getText());
					int floorDimY = Integer.parseInt(inputDimYFloor.getText());
					int floorDimZ = Integer.parseInt(inputDimZFloor.getText());
					
					Floor floor = new Floor(floorName,floorDimX,floorDimY,floorDimZ);
					FloorDAO.getInstance().create(floor);
				}
				catch (NumberFormatException numberException) {
					mainControler.notifyFail("Valeurs inscrites incorectes");
				} 
			}
			
			private void updateFloor() {
				Floor selectedFloor = floorTableView.getItems().get(selectedFloorLine);	
				System.out.println(!(selectedFloor.getRooms().size() >= 1));
				if(!(selectedFloor.getRooms().size() >= 1)) {
					selectedFloor.setName(inputNameFloor.getText());
				selectedFloor.setDim_x(Integer.parseInt(inputDimXFloor.getText()));
				selectedFloor.setDim_y(Integer.parseInt(inputDimYFloor.getText()));
				selectedFloor.setDim_z(Integer.parseInt(inputDimZFloor.getText()));
				
				FloorDAO.getInstance().update(selectedFloor);
				
				} else {
					
					Notify.getInstance().showAlerte("Valeurs Incorrectes","Valeurs Incorrectes", "La dimension ou le positionnement de la salle est incorrecte !");
				}
			
			}
			private void addRoom() {
				
				String roomName = inputNameRoom.getText();
				Floor floor = floorChoiceBox.getValue();
				int roomDimX = Integer.parseInt(inputDimXRoom.getText());
				int roomDimY = Integer.parseInt(inputDimYRoom.getText());
				int roomDimZ = Integer.parseInt(inputDimZRoom.getText());
				int roomPosX = Integer.parseInt(inputPosXRoom.getText());
				int roomPosY = Integer.parseInt(inputPosYRoom.getText());
				
				Room room = new Room(roomName, floor, roomDimX,roomDimY,roomDimZ, roomPosX, roomPosY);
				 
				List<Area> checkArea = new ArrayList<Area>(RoomDAO.getInstance().readAll());
				System.out.println(room.getFloor().getDim_x() + " : " + room.getFloor().getDim_y());
				System.out.println(room.overlaps(checkArea));
				System.out.println(room.insideParent());
				if (!room.overlaps(checkArea) && room.insideParent()) {
					RoomDAO.getInstance().create(room);
				} else {
					this.resetRoomTextField();
					this.setRoomError();
					Notify.getInstance().showAlerte("Valeurs Incorectes","Valeurs Incorectes", "La dimension ou le positionnement de la salle est incorrecte !");
				}
			}
			
			private void updateRoom() {
				Room selectedRoom = roomTableView.getItems().get(selectedRoomLine);			
				
				String roomName = selectedRoom.getName();
				Floor floor = selectedRoom.getFloor();
				int roomDimX = selectedRoom.getDim_x();
				int roomDimY = selectedRoom.getDim_y();
				int roomDimZ = selectedRoom.getDim_z();
				int roomPosX = selectedRoom.getPos_x();
				int roomPosY = selectedRoom.getPos_y();
				
				selectedRoom.setName(inputNameRoom.getText());
				selectedRoom.setFloor(floorChoiceBox.getValue());
				selectedRoom.setDim_x(Integer.parseInt(inputDimXRoom.getText()));
				selectedRoom.setDim_y(Integer.parseInt(inputDimYRoom.getText()));
				selectedRoom.setDim_z(Integer.parseInt(inputDimZRoom.getText()));
				selectedRoom.setPos_x(Integer.parseInt(inputPosXRoom.getText()));
				selectedRoom.setPos_y(Integer.parseInt(inputPosYRoom.getText()));
				
				 
				List<Area> checkArea = new ArrayList<Area>(RoomDAO.getInstance().readAll());
				checkArea.remove(selectedRoom);
				if (!selectedRoom.overlaps(checkArea) && selectedRoom.insideParent()) {
					RoomDAO.getInstance().update(selectedRoom);
				} else {
					this.resetRoomTextField();
					this.setRoomError();
					
					selectedRoom.setName(roomName);
					selectedRoom.setFloor(floor);
					selectedRoom.setDim_x(roomDimX);
					selectedRoom.setDim_y(roomDimY);
					selectedRoom.setDim_z(roomDimZ);
					selectedRoom.setPos_x(roomPosX);
					selectedRoom.setPos_y(roomPosY);
					
					Notify.getInstance().showAlerte("Valeurs Incorrectes","Valeurs Incorrectes", "La dimension ou le positionnement de la salle est incorrecte !");
				}
				
			}
			
			// Initialisation des éléments 
			private void initializeFloorTableView() {
					this.floorTableView.getItems().setAll(FloorDAO.getInstance().readAll());
					idFloorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId_floor()+""));
					nameFloorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()+""));
			}
			private void initializeRoomTableView() {
				this.roomTableView.getItems().setAll(RoomDAO.getInstance().readAll());
				idRoomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId_room()+""));
				nameRoomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()+""));
		}
			
			private void initializeFloorChoiceBox() {
				 floorChoiceBox.getItems().setAll(FloorDAO.getInstance().readAll());
			}
			
			private void initializeSelectedFloorChoiceBox() {
				 this.SelectedFloorChoiceBox.getItems().setAll(FloorDAO.getInstance().readAll());
			}
			
			
			private void initializeTreeView() {
				// Récupère les éléments du musée 
				List<Floor> floors = FloorDAO.getInstance().readAll();
				List<Room> rooms = RoomDAO.getInstance().readAll();
				// Liste pour retrouver l'adresse mémoire des objets créés
				ArrayList<TreeItem<String>> floorItems = new ArrayList<TreeItem<String>>();
				ArrayList<TreeItem<String>> roomItems = new ArrayList<TreeItem<String>>();
				// Arbre éditable
				TreeItem<String> globalItem = new TreeItem<String>("Floor");
				TreeItem<String>  treeItem;
				
				// Ajout des Floors 
				for(Floor floor : floors) {
					treeItem = new TreeItem<String>(floor.getName());
					globalItem.getChildren().add(treeItem);
					floorItems.add(treeItem);
				}
				
				// Ajout des Rooms
				for(TreeItem<String> item : floorItems) {
					for(Room room : rooms) {
						if (item.getValue().equals(room.getFloor().getName())) {
							treeItem = new TreeItem<String>(room.getName());
							item.getChildren().add(treeItem);
							roomItems.add(treeItem);
						}
					}
				}
				
			
				globalTreeView.setRoot(globalItem);
			}
			
			private void initializePlan() {
				this.editPlanControl.setRatioFitPage(FloorDAO.getInstance().readAll().get(0));
				this.editPlanControl.drawFloorOn(FloorDAO.getInstance().readAll().get(0));
				this.editPlanControl.drawRoomsOn(RoomDAO.getInstance().readAll());
			}
			
			private void refreshWindow() {
				this.initializeFloorTableView();
				this.initializeRoomTableView();
				this.initializeFloorChoiceBox();
				this.initializeTreeView();
				this.editPlanControl.CleanPlan();
				this.initializePlan();
				}
			
		}
