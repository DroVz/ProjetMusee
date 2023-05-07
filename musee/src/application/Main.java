package application;
	
import java.io.IOException;
import java.util.List;

import museum.Notify;
import controllerView.ArchitectControl;
import controllerView.CuratorControl;
import controllerView.LoginControl;
import controllerView.ShowRoomControl;
import dao.ArtDAO;
import dao.ArtStatusDAO;
import dao.ArtTypeDAO;
import dao.AuthorDAO;
import dao.DoorDAO;
import dao.FloorDAO;
import dao.RoleDAO;
import dao.RoomDAO;
import dao.UserDAO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import museum.Art;
import museum.ArtStatus;
import museum.ArtType;
import museum.Author;
import museum.Door;
import museum.Floor;
import museum.Role;
import museum.Room;
import museum.User;

public class Main extends Application {
	
	private Stage mainWindow;			// "stage" principal
	private BorderPane mainWindowRoot;	// fenêtre principale
	private Stage notifWindow = new Stage();   // "stage" des fenêtres de notification
	private Pane dialogFail;
	
	// sous-fenêtres
	private AnchorPane loginPane = null;
	private AnchorPane architectPane = null;
	private AnchorPane curatorPane = null;
	private AnchorPane showRoomPane = null;
	
	// sous-contrôleurs des différentes sous-fenêtres
	private LoginControl loginCtrl = null;
	private ArchitectControl architectCtrl = null;
	private CuratorControl curatorCtrl = null;
	private ShowRoomControl showRoomCtrl = null;
	
	// observableLists pour manipuler les données
	private ObservableList<Art> artData = FXCollections.observableArrayList();
	private ObservableList<Art> availableArtData = FXCollections.observableArrayList();
	private ObservableList<ArtType> artTypeData = FXCollections.observableArrayList();
	private ObservableList<ArtStatus> artStatusData = FXCollections.observableArrayList();
	private ObservableList<Author> authorData = FXCollections.observableArrayList();
	private ObservableList<Door> doorData = FXCollections.observableArrayList();
	private ObservableList<Floor> floorData = FXCollections.observableArrayList();
	private ObservableList<Role> roleData = FXCollections.observableArrayList();
	private ObservableList<Room> roomData = FXCollections.observableArrayList();
	private ObservableList<User> userData = FXCollections.observableArrayList();
	
	@FXML
	private Button btnQuit;

	
	public Main() {
		super();
		this.roleData = getRoleData();
		this.userData = getUserData();
	}
	
	/**
	 * à la demande d'un des sous-contrôleurs, affiche une notification "échec de l'enregistrement"
	 */
	public void notifyFail(String messageToShow) {
		if (notifWindow.getModality() != Modality.APPLICATION_MODAL) {
			notifWindow.initModality(Modality.APPLICATION_MODAL);
			notifWindow.setTitle(messageToShow);
		};		
		try {
			// lien avec la vue
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/NotifFail.fxml"));
			// passage de ce contrôleur à la vue
			loader.setController(this);
			dialogFail = (Pane)loader.load();
			// affichage de la fenêtre pop-up
			Scene scene = new Scene(dialogFail);
			notifWindow.setScene(scene);
			notifWindow.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/*  ---------------------------
	 * 
	 *   MÉTHODES LIEES AU MODÈLE
	 * 
	 *  --------------------------- */
	
	public ObservableList<ArtType> getArtTypeData() {
		artTypeData = FXCollections.observableArrayList();
		List<ArtType> artTypes = ArtTypeDAO.getInstance().readAll();
		for (ArtType artType : artTypes) {
			artTypeData.add(artType);
		}
		return artTypeData;
	}
	
	public ObservableList<ArtStatus> getArtStatusData() {
		artStatusData = FXCollections.observableArrayList();
		List<ArtStatus> artStatuses = ArtStatusDAO.getInstance().readAll();
		for (ArtStatus artStatus : artStatuses) {
			artStatusData.add(artStatus);
		}
		return artStatusData;
	}
	
	public ObservableList<Art> getArtData() {
		artData = FXCollections.observableArrayList();
		List<Art> arts = ArtDAO.getInstance().readAll();
		for (Art art : arts) {
			artData.add(art);
		}
		return artData;
	}
	
	public ObservableList<Art> getAvailableArtData() {
		availableArtData = FXCollections.observableArrayList();
		List<Art> arts = ArtDAO.getInstance().readAllAvailable();
		for (Art art : arts) {
			availableArtData.add(art);
		}
		return availableArtData;
	}
	
	public ObservableList<Author> getAuthorData() {
		authorData = FXCollections.observableArrayList();
		List<Author> authors = AuthorDAO.getInstance().readAll();
		for (Author author : authors) {
			authorData.add(author);
		}
		return authorData;
	}
	
	public ObservableList<Door> getDoorData() {
		doorData = FXCollections.observableArrayList();
		List<Door> doors = DoorDAO.getInstance().readAll();
		for (Door door : doors) {
			doorData.add(door);
		}
		return doorData;
	}
	
	public ObservableList<Floor> getFloorData() {
		floorData = FXCollections.observableArrayList();
		List<Floor> floors = FloorDAO.getInstance().readAll();
		for (Floor floor : floors) {
			floorData.add(floor);
		}
		return floorData;
	}
	
	public ObservableList<Role> getRoleData() {
		roleData = FXCollections.observableArrayList();
		List<Role> roles = RoleDAO.getInstance().readAll();
		for (Role role : roles) {
			roleData.add(role);
		}
		return roleData;
	}
	
	public ObservableList<Room> getRoomData() {
		roomData = FXCollections.observableArrayList();
		List<Room> rooms = RoomDAO.getInstance().readAll();
		for (Room room : rooms) {
			roomData.add(room);
		}
		return roomData;
	}
	
	public ObservableList<User> getUserData() {
		userData = FXCollections.observableArrayList();
		List<User> users = UserDAO.getInstance().readAll();
		for (User user : users) {
			userData.add(user);
		}
		return userData;
	}
	
	public void addRoom(String name, Floor floor, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y) {
		Room room = new Room(name, floor, dim_x, dim_y, dim_z, pos_x, pos_y);
		if (RoomDAO.getInstance().create(room)) {
			Notify.getInstance().showNotif("Création","La salle a bien été enregistrée");
		}		
	}	
	public void updateRoom(int id_room, String name,  Floor floor, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y) {
		Room room = new Room(id_room, name, floor, dim_x, dim_y, dim_z, pos_x, pos_y);
		if (RoomDAO.getInstance().update(room)) {
			Notify.getInstance().showNotif("Modification","La salle a bien été modifiée");
		}		
	}
	
	public void deleteRoom(int id_room) {
		Room room = RoomDAO.getInstance().read(id_room);
		if (RoomDAO.getInstance().delete(room)) {
			Notify.getInstance().showNotif("Suppression", "La salle a bien été supprimée");
		}
	}
	
	public void addFloor(String name, int dim_x, int dim_y, int dim_z) {
		Floor floor = new Floor(name, dim_x, dim_y, dim_z);
		if (FloorDAO.getInstance().create(floor)) {
			Notify.getInstance().showNotif("Création", "L'étage a bien été enregistrée");
		}		
	}	
	public void updateFloor(int id_floor, String name, int dim_x, int dim_y, int dim_z) {
		Floor floor = new Floor(id_floor, name, dim_x, dim_y, dim_z);
		if (FloorDAO.getInstance().update(floor)) {
			Notify.getInstance().showNotif("Modification", "L'étage a bien été modifiée");
		}		
	}
	
	public void deleteFloor(int id_floor) {
		Floor floor = FloorDAO.getInstance().read(id_floor);
		if (FloorDAO.getInstance().delete(floor)) {
			Notify.getInstance().showNotif("Suppression", "L'étage a bien été supprimée");
		}
	}
	
	public void addArt(String art_code, String art_title, String creation_date,
			String materials, int dim_x, int dim_y, int dim_z, byte[] image, Author author,
			ArtStatus art_status, ArtType art_type, boolean owner) {
		// par défaut, une œuvre est placée "En réserve" (id_art_status = 1)
		ArtStatus artStatus = ArtStatusDAO.getInstance().read(1);
		Art art = new Art(art_code, art_title, creation_date, materials, dim_x, dim_y, dim_z, image,
				author, artStatus, art_type, owner);
		if (ArtDAO.getInstance().create(art)) {
			curatorCtrl.notifyArtSaved("L'œuvre a été ajoutée à la base de données");
		}		
	}
	
	public Art getFullArtData(int id_art) {
		Art fullArt = ArtDAO.getInstance().read(id_art);
		return fullArt;
	}
	
	public void updateArt(int art_id, String art_code, String art_title, String creation_date,
			String materials, int dim_x, int dim_y, int dim_z, byte[] image, Author author,
			ArtStatus art_status, ArtType art_type, boolean owner) {
		Art art = new Art(art_id, art_code, art_title, creation_date, materials, dim_x, dim_y, dim_z, image,
				author, art_status, art_type, owner);
		if (ArtDAO.getInstance().update(art)) {
			curatorCtrl.notifyArtSaved("L'œuvre a été modifiée");
		}		
	}		
	
	public void addAuthor(String last_name, String first_name, String additional_name, String dates) {
		Author author = new Author(last_name, first_name, additional_name, dates);
		if (AuthorDAO.getInstance().create(author)) {
			curatorCtrl.notifyAuthorSaved("Auteur enregistré");
		}		
	}
	
	public void updateAuthor(int id_author, String last_name, String first_name, String additional_name, String dates) {
		Author author = new Author(id_author, last_name, first_name, additional_name, dates);
		if (AuthorDAO.getInstance().update(author)) {
			curatorCtrl.notifyAuthorSaved("Auteur enregistré");
		}		
	}
	
	/*  ---------------------------
	 * 
	 *    MÉTHODES LIEES À LA VUE
	 * 
	 *  --------------------------- */
	
	@Override
	public void start(Stage primaryStage) {
		this.mainWindow = primaryStage;
		this.mainWindow.setTitle("Gestion de musée");
		// initialisation de la fenêtre principale
		initWindowRoot();
		// affichage de la sous-fenêtre de connexion
		showLoginPane();
	}
	
	/**
	 * affiche la fenêtre principale de l'application
	 */
	public void initWindowRoot() {
		try {
			// lien avec la vue
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/MainWindow.fxml"));
			// passage du contrôleur principal (this) à la vue
			loader.setController(this);
			mainWindowRoot = (BorderPane)loader.load();
			// affichage de la fenêtre principale
			Scene scene = new Scene(mainWindowRoot);
			mainWindow.setMaximized(true);
			mainWindow.setScene(scene);
			mainWindow.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * affiche la sous-fenêtre de connexion
	 */
	public void showLoginPane() {
		try {
			if (loginPane==null) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("../view/Login.fxml"));
				loginPane = (AnchorPane)loader.load();
				// récupération du contrôleur de la vue
				this.loginCtrl = loader.getController();
				// passage du contrôleur principal (this) au sous-contrôleur
				this.loginCtrl.setMainControl(this);
			}
			// positionnement de cette sous-fenêtre au milieu de la fenêtre principale
			mainWindowRoot.setCenter(loginPane);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * affiche la sous-fenêtre du rôle "architecte"
	 */
	public void showArchitectPane() {
		try {
			if (architectPane==null) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("../view/Architect.fxml"));
				architectPane = (AnchorPane)loader.load();
				// récupération du contrôleur de la vue
				this.architectCtrl = loader.getController();
				// passage du contrôleur principal (this) au sous-contrôleur
				this.architectCtrl.setMainControl(this);
			}
			// positionnement de cette sous-fenêtre au milieu de la fenêtre principale
			mainWindowRoot.setCenter(architectPane);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * affiche la sous-fenêtre du rôle "conservateur"
	 */
	public void showCuratorPane() {
		try {
			if (curatorPane==null) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("../view/Curator.fxml"));
				curatorPane = (AnchorPane)loader.load();
				// TEST largeur de sous-fenêtre
				curatorPane.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
				// récupération du contrôleur de la vue
				this.curatorCtrl = loader.getController();
				// passage du contrôleur principal (this) au sous-contrôleur
				this.curatorCtrl.setMainControl(this);
			}
			// positionnement de cette sous-fenêtre au milieu de la fenêtre principale
			mainWindowRoot.setCenter(curatorPane);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showShowRoomPane() {
		try {
			if (showRoomPane==null) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("../view/ShowRoom.fxml"));
				showRoomPane = (AnchorPane)loader.load();
				// récupération du contrôleur de la vue
				this.showRoomCtrl = loader.getController();
				// passage du contrôleur principal (this) au sous-contrôleur
				this.showRoomCtrl.setMainControl(this);
			}
			// positionnement de cette sous-fenêtre au milieu de la fenêtre principale
			mainWindowRoot.setCenter(showRoomPane);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * event listener du bouton "OK" du pop-up de notification d'échec
	 * @param e
	 */
	@FXML
	private void confirmFail(ActionEvent e) {
		notifWindow.close();
	}
	
	@FXML
	private void handleQuitBtn(ActionEvent e) {
		Platform.exit();
	}
			
	/**
	 * point d'entrée du programme, utilise les arguments spécifiés dans Run Configurations
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
