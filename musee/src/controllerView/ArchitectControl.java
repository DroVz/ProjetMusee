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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import museum.Area;
import museum.Floor;
import museum.Notify;
import museum.Room;

public class ArchitectControl {
	
		// Tree View
		@FXML 
		private TreeView<String> globalTreeView;
		
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
		
		// Label
		@FXML
		private Label floorLabel;
		
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
		private ChoiceBox<Floor> SelectedFloorChoiceBox;
		@FXML
		private ChoiceBox<Room> roomChoiceBox;


		// Private property 
		private Main mainControler;
		private EditPlanControl editPlanControl;
		private boolean updateRoomButtonSelected = false;
		private boolean updateFloorButtonSelected = false;
		
		private Floor selectedFloor;
	
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
			
			/**
			 * Initialisation des éléments FXML
			 */
			@FXML 
			private void initialize() {
				this.initializeSelectedFloorChoiceBox();
				this.initializeTreeView();
				this.initializeFloorTableView();
				
				// Chargement du plan 2D
				this.editPlanControl = new EditPlanControl(this.drawSection);
			}
			
			
			//------------------//
			//----> Handle <----//
			//------------------//
			
			/**
			 * Event : Création d'un étage
			 * @param event
			 */
			@FXML
			private void handleCreateFloor(ActionEvent event) {
				this.updateFloorButtonSelected = false;
				floorAnchorPane.setDisable(false);
			}
			
			/**
			 * Event : Suppression d'un étage
			 * @param event
			 */
			@FXML
			private void handleDeleteFloor(ActionEvent event) {
				try {
					Floor selectedFloor = floorTableView.getSelectionModel().getSelectedItem();
					FloorDAO.getInstance().delete(selectedFloor);
					this.floorTableView.getItems().remove(selectedFloor);
					
					this.initializeTreeView();
					this.initializeSelectedFloorChoiceBox();
					
					this.editPlanControl.CleanPlan();
				} catch (Exception ex) {
					Notify.getInstance().showAlerte("Alerte", "Aucun étage sélectionné", "Merci de sélectionner un étage dans la table ci-dessus.");
				}
			}
			
			/**
			 * Event : Modification d'un étage
			 * @param event
			 */
			@FXML
			private void handleUpdateFloor(ActionEvent event) {
				try {
					Floor selectedFloor = floorTableView.getSelectionModel().getSelectedItem();		
					inputNameFloor.setText(selectedFloor.getName());
					inputDimXFloor.setText(""+selectedFloor.getDim_x());
					inputDimYFloor.setText(""+selectedFloor.getDim_y());
					inputDimZFloor.setText(""+selectedFloor.getDim_z());
					
					this.updateFloorButtonSelected = true;
					floorAnchorPane.setDisable(false);
				} catch( Exception ex){
					Notify.getInstance().showAlerte("Alerte", "Aucun étage sélectionné", "Merci de sélectionner un étage dans la table ci-dessus.");
				}
			}
			
			/**
			 * Event : Confirme les valeurs inscrites pour la création ou modification d'un étage
			 * @param event
			 */
			@FXML
			private void handleConfirmFloor(ActionEvent event) {
				if(this.updateFloorButtonSelected == false) {
					this.addFloor();
				}else { 
					this.updateFloor();
				}
				
				this.resetFloorTextField();
				floorAnchorPane.setDisable(true);
			}
			
			/**
			 * Event : Annule la création ou la modification d'un étage
			 * @param event
			 */
			@FXML
			private void handleCancelFloor(ActionEvent event) {
				this.resetFloorTextField();
				floorAnchorPane.setDisable(true);
			}
			
			/**
			 * Event : Création d'une salle
			 * @param event
			 */
			@FXML
			private void handleCreateRoom(ActionEvent event) {
			this.updateRoomButtonSelected = false;
			roomAnchorPane.setDisable(false);
			}
			
			/**
			 * Event : Suppression d'une salle 
			 * @param event
			 */
			@FXML
			private void handleDeleteRoom(ActionEvent event) {	
				try {
					museum.Room selectedRoom = roomTableView.getSelectionModel().getSelectedItem();	
					RoomDAO.getInstance().delete(selectedRoom);
					this.roomTableView.getItems().remove(selectedRoom);
					this.selectedFloor.getRooms().remove(selectedRoom);
					
					this.initializeTreeView();
					this.initializePlan();
				} catch(Exception ex) {
					Notify.getInstance().showAlerte("Alerte", "Aucune salle sélectionnée", "Merci de sélectionner une salle dans la table ci-dessus.");
				}
			}
			
			/**
			 * Event : Modification d'une salle
			 * @param event
			 */
			@FXML
			private void handleUpdateRoom(ActionEvent event) {
				try {
					Room selectedRoom = roomTableView.getSelectionModel().getSelectedItem();
					inputNameRoom.setText(selectedRoom.getName());
					inputDimXRoom.setText(""+selectedRoom.getDim_x());
					inputDimYRoom.setText(""+selectedRoom.getDim_y());
					inputDimZRoom.setText(""+selectedRoom.getDim_z());
					inputPosXRoom.setText(""+selectedRoom.getPos_x());
					inputPosYRoom.setText(""+selectedRoom.getPos_y());
					
					this.updateRoomButtonSelected = true ; 
					roomAnchorPane.setDisable(false);
				} catch(Exception ex) {
					Notify.getInstance().showAlerte("Alerte", "Aucune salle sélectionnée", "Merci de sélectionner une salle dans la table ci-dessus.");
				}
			}
			
			/**
			 * Event : Confirme les valeurs inscrites pour la création ou modification d'une salle
			 * @param event
			 */
			@FXML
			private void handleConfirmRoom(ActionEvent event) {
				if( this.updateRoomButtonSelected == false) {
					this.addRoom();
				} else {
					this.updateRoom();
				}
				
				this.resetRoomTextField();
				roomAnchorPane.setDisable(true);
			}
			
			/**
			 * Event : Annule la création ou la modification d'une salle
			 * @param event
			 */
			@FXML
			private void handleCancelRoom(ActionEvent event) {
				this.resetRoomTextField();
				roomAnchorPane.setDisable(true);
			}

			private void addFloor() {
				try {
					String floorName = inputNameFloor.getText();
					int floorDimX = Integer.parseInt(inputDimXFloor.getText());
					int floorDimY = Integer.parseInt(inputDimYFloor.getText());
					int floorDimZ = Integer.parseInt(inputDimZFloor.getText());
					
					Floor floor = new Floor(floorName,floorDimX,floorDimY,floorDimZ);
					FloorDAO.getInstance().create(floor);
					this.floorTableView.getItems().add(floor);
					
					this.initializeTreeView();
					this.initializeSelectedFloorChoiceBox();
				}
				catch (NumberFormatException numberException) {
					Notify.getInstance().showAlerte("Alerte", "Valeurs inscrites incorectes", "Merci de vérifier les valeurs inscrites");
				} 
			}
			
			
			/**
			 * Permet la modification d'un étage
			 */
			private void updateFloor() {
				try {
					Floor selectedFloor = floorTableView.getSelectionModel().getSelectedItem();	
			
					if(selectedFloor.getRooms().size() == 0) {
						selectedFloor.setName(inputNameFloor.getText());
						selectedFloor.setDim_x(Integer.parseInt(inputDimXFloor.getText()));
						selectedFloor.setDim_y(Integer.parseInt(inputDimYFloor.getText()));
						selectedFloor.setDim_z(Integer.parseInt(inputDimZFloor.getText()));
					
						FloorDAO.getInstance().update(selectedFloor);
						this.initializePlan();
					} else {
						Notify.getInstance().showAlerte("Alerte","Valeurs Incorrectes", "Les dimensions ou le positionnement de la salle est incorrecte !");
					}
				} catch(Exception ex) {
					Notify.getInstance().showAlerte("Alerte","Aucun étage sélectionné", "Merci de sélectionner un étage ci-dessus");
				}
			}
			
			/**
			 * Permet la création d'une nouvelle salle
			 */
			private void addRoom() {
				try {
					String roomName = inputNameRoom.getText();
					Floor floor = this.selectedFloor;
					int roomDimX = Integer.parseInt(inputDimXRoom.getText());
					int roomDimY = Integer.parseInt(inputDimYRoom.getText());
					int roomDimZ = Integer.parseInt(inputDimZRoom.getText());
					int roomPosX = Integer.parseInt(inputPosXRoom.getText());
					int roomPosY = Integer.parseInt(inputPosYRoom.getText());
					
					Room room = new Room(roomName, floor, roomDimX,roomDimY,roomDimZ, roomPosX, roomPosY);
					List<Area> checkArea = new ArrayList<Area>(this.selectedFloor.getRooms());
					
					if (!room.overlaps(checkArea) && room.insideParent()) {
						RoomDAO.getInstance().create(room);
						
						this.roomTableView.getItems().add(room);
						this.selectedFloor.getRooms().add(room);
						
						this.initializeTreeView();
						this.initializePlan();
					} else {
						this.resetRoomTextField();
						this.setRoomError();
						Notify.getInstance().showAlerte("Alerte","Valeurs Incorectes", "Les dimensions ou le positionnement de la salle est incorrecte !");
					}
				}catch(Exception ex) {
					Notify.getInstance().showAlerte("Alerte","Aucune valeurs a été inscrites", "Merci d'inscrire des valeurs dans les champs texts ci-dessus");
				}
			}
			
			/**
			 * Permet la modification d'une salle
			 */
			private void updateRoom() {
				try {
					Room selectedRoom = roomTableView.getSelectionModel().getSelectedItem();			
					
					String roomName = selectedRoom.getName();
					Floor floor = selectedRoom.getFloor();
					int roomDimX = selectedRoom.getDim_x();
					int roomDimY = selectedRoom.getDim_y();
					int roomDimZ = selectedRoom.getDim_z();
					int roomPosX = selectedRoom.getPos_x();
					int roomPosY = selectedRoom.getPos_y();
					
					selectedRoom.setName(inputNameRoom.getText());
					selectedRoom.setFloor(this.selectedFloor);
					selectedRoom.setDim_x(Integer.parseInt(inputDimXRoom.getText()));
					selectedRoom.setDim_y(Integer.parseInt(inputDimYRoom.getText()));
					selectedRoom.setDim_z(Integer.parseInt(inputDimZRoom.getText()));
					selectedRoom.setPos_x(Integer.parseInt(inputPosXRoom.getText()));
					selectedRoom.setPos_y(Integer.parseInt(inputPosYRoom.getText()));
					
					 
					List<Area> checkArea = new ArrayList<Area>(this.selectedFloor.getRooms());
					checkArea.remove(selectedRoom);
					if (!selectedRoom.overlaps(checkArea) && selectedRoom.insideParent()) {
						RoomDAO.getInstance().update(selectedRoom);
						this.initializePlan();
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
						
						Notify.getInstance().showAlerte("Alerte","Valeurs Incorrectes", "Les dimensions ou le positionnement de la salle est incorrecte !");
					}
				} catch(Exception ex) {
					Notify.getInstance().showAlerte("Alerte","Aucune valeurs a été inscrites", "Merci d'inscrire des valeurs dans les champs texts ci-dessus");
				}	
			}
			
			/**
			 * Remet les TextFields liés à la création ou la modification d'un étage à vide
			 */
			private void resetFloorTextField() {
				inputNameFloor.setText("");
				inputDimXFloor.setText("");
				inputDimYFloor.setText("");
				inputDimZFloor.setText("");
			}
			
			/**
			 * Remet les TextFields liés à la création ou la modification d'une salle à vide
			 */
			private void resetRoomTextField() {
				inputNameRoom.setText("");
				inputDimXRoom.setText("");
				inputDimYRoom.setText("");
				inputDimZRoom.setText("");
				inputPosXRoom.setText("");
				inputPosYRoom.setText("");
			}
			
			/**
			 * Attribut la valeur "Erreur" aux TextFields liés à la création ou la modification d'une salle
			 */
			private void setRoomError() {
				inputDimXRoom.setText("Erreur");
				inputDimYRoom.setText("Erreur");
				inputPosXRoom.setText("Erreur");
				inputPosYRoom.setText("Erreur");	
			}
			
			/**
			 * Active ou non les boutons create,delete,update room
			 * @param disable
			 */
			private void roomButtonSetDisable(boolean disable) {
				this.createRoom.setDisable(disable);
				this.deleteRoom.setDisable(disable);
				this.updateRoom.setDisable(disable);
			}
			
			
			//----------------------//
			//----> Initialize <----//
			//----------------------//
			
			/**
			 * Initialisation du composant floor TableView 
			 */
			private void initializeFloorTableView() {
					this.floorTableView.getItems().setAll(FloorDAO.getInstance().readAll());
					idFloorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId_floor()+""));
					nameFloorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()+""));
			}
			
			/**
			 * Initialisation du composant room TableView 
			 */
			private void initializeRoomTableView() {
				this.roomTableView.getItems().setAll(this.selectedFloor.getRooms());
				idRoomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId_room()+""));
				nameRoomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()+""));
			}
			
			/**
			 * Initialisation du composant floor ChoiceBox 
			 */
			private void initializeFloorLabel() {
				 this.floorLabel.setText(this.selectedFloor.getName());;
			}
			
			/**
			 * Initialisation du composant selectedFloor ChoiceBox 
			 */
			private void initializeSelectedFloorChoiceBox() {
				List<Floor> floors = FloorDAO.getInstance().readAll();
				if(floors.size() != 0) {
					this.SelectedFloorChoiceBox.getItems().clear();
					this.SelectedFloorChoiceBox.getItems().setAll(floors);
					this.roomButtonSetDisable(true);
				}
			}
	
			/**
			 * Initialisation du composant TreeView 
			 */
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
				this.editPlanControl.CleanPlan();
				this.editPlanControl.setRatioFitPage(this.selectedFloor);
				this.editPlanControl.drawFloorOn(this.selectedFloor);
				this.editPlanControl.drawRoomsOn(this.selectedFloor.getRooms());
			}
			
	
			
		}
