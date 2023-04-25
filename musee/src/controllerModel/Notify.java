package controllerModel;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Notify {
	
	private static Notify instance = null;
	
	private Stage notifWindow = new Stage();   // "stage" des fenêtres de notification
	private Pane dialogFail;
	
	@FXML 
	private Button confirmButton;
	@FXML 
	private Label labelToPrint;

	private Notify() {
		
	}
	
	public static Notify GetInstance() {
		if(instance == null) {
			instance = new Notify();
		}
		return instance;
	}
	
	@FXML 
	private void acceptNotify() {
		notifWindow.close();
	}

	public void showNotif(String title, String message) {
		try {
		// lien avec la vue
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../view/Notify.fxml"));
		// passage de ce contrôleur à la vue
		loader.setController(this);
		dialogFail = (Pane)loader.load();
		// affichage de la fenêtre pop-up
		Scene scene = new Scene(dialogFail);
		notifWindow.setScene(scene);
		notifWindow.setTitle(title);
		labelToPrint.setText(message);
		notifWindow.show();
		} catch (Exception ex) {
			System.out.println(ex.getMessage() + " : Notify Error -> ShowNotif");
		}
		

	}
	
}