package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import museum.Door;
import museum.Role;
import museum.Room;
import museum.User;
import controller.ArchitectControl;
import controller.LoginControl;
import dao.DoorDAO;
import dao.RoleDAO;
import dao.RoomDAO;
import dao.UserDAO;

public class Main extends Application {
	
	private Stage mainWindow;			// "stage" principal
	private BorderPane mainWindowRoot;	// fenêtre principale
	
	// sous-fenêtres
	private AnchorPane loginPane = null;
	private AnchorPane architectPane = null;
	
	// sous-contrôleurs des différentes sous-fenêtres
	private LoginControl loginCtrl = null;
	private ArchitectControl architectCtrl = null;
	
	// observableLists pour manipuler les données
	private ObservableList<Door> doorData = FXCollections.observableArrayList();	
	private ObservableList<Role> roleData = FXCollections.observableArrayList();
	private ObservableList<Room> roomData = FXCollections.observableArrayList();
	private ObservableList<User> userData = FXCollections.observableArrayList();	
	
	public Main() {
		super();
		this.roleData = getRoleData();
		this.userData = getUserData();
	}
	
	/*  ---------------------------
	 * 
	 *   MÉTHODES LIEES AU MODÈLE
	 * 
	 *  --------------------------- */
	
	public ObservableList<Door> getDoorData() {
		doorData = FXCollections.observableArrayList();
		List<Door> doors = DoorDAO.getInstance().readAll();
		for (Door door : doors) {
			doorData.add(door);
		}
		return doorData;
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
	
	public void addRoom(String name, int floor, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y) {
		Room room = new Room(name, floor, dim_x, dim_y, dim_z, pos_x, pos_y);
		if (RoomDAO.getInstance().create(room)) {
			architectCtrl.notifyRoomSaved("La salle a bien été enregistrée");
		}		
	}
	
	public void updateRoom(int id_room, String name, int floor, int dim_x, int dim_y, int dim_z, int pos_x, int pos_y) {
		Room room = new Room(id_room, name, floor, dim_x, dim_y, dim_z, pos_x, pos_y);
		if (RoomDAO.getInstance().update(room)) {
			architectCtrl.notifyRoomSaved("La salle a été modifiée");
		}		
	}
	
	public void deleteRoom(int id_room) {
		Room room = RoomDAO.getInstance().read(id_room);
		if (RoomDAO.getInstance().delete(room)) {
			architectCtrl.notifyRoomSaved("La salle a été supprimée");
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
	 * point d'entrée du programme, utilise les arguments spécifiés dans Run Configurations
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
