package controllerView;

import java.io.IOException;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import museum.Art;
import museum.ArtType;
import museum.Author;
import museum.Room;

public class CuratorControl {
	
	/*  ---------------------------
	 * 
	 *          ATTRIBUTS
	 * 
	 *  --------------------------- */
	
	private Main mainController;
	// ligne sélectionnée dans la table des œuvres, par défaut aucune
	private int selectedArtLine = 0;
	private Stage notifWindow = new Stage();
	private Pane dialogArtSaved;
	private boolean updatingArt = false;
	private boolean addingArt = false;
	
	@FXML
	private Button createAction;
	@FXML
	private Button cancelCreatEdit;
	@FXML
	private Button confirmArtSaved;
	@FXML
	private Button editArt;
	@FXML
	private Button deleteArt;
	@FXML
	private Button saveArt;
	@FXML
	private ComboBox<Author> cbbAuthor;
	@FXML
	private ComboBox<ArtType> cbbArtType;
	@FXML
	private ImageView imgArt;
	@FXML
	private Label lblArtCreatEditTitle;
	@FXML
	private Label lblArtTitle;
	@FXML
	private Label lblArtCode;
	@FXML
	private Label lblArtDates;
	@FXML
	private Label lblMaterials;
	@FXML
	private Label lblArtX;
	@FXML
	private Label lblArtY;
	@FXML
	private Label lblArtZ;
	@FXML
	private Label lblAuthor;
	@FXML
	private Label lblArtType;
	@FXML
	private Label lblArtStatus;
	@FXML
	private Label lblNotification;
	@FXML
	private AnchorPane pneArtInfo;
	@FXML
	private AnchorPane pneArtCreatEdit;
	@FXML
	private TableView<Art> artTable;
	@FXML
	private TableColumn<Art, String> codeColumn;
	@FXML
	private TableColumn<Art, String> nameColumn;
	@FXML
	private TableColumn<Art, String> authorColumn;
	@FXML
	private TextField txtArtTitle;
	@FXML
	private TextField txtArtCode;
	@FXML
	private TextField txtArtDates;
	@FXML
	private TextField txtMaterials;
	@FXML
	private TextField txtDimX;
	@FXML
	private TextField txtDimY;
	@FXML
	private TextField txtDimZ;
	
	

	/**
	 * constructeur de la classe
	 */
	public CuratorControl() {
		super();
	}
	
	/**
	 * définit le contrôleur principal
	 * @param mainController
	 */
	public void setMainControl(Main mainController) {
		this.mainController = mainController;
		refreshData();
	}
	
	public void refreshData() {
		artTable.setItems(mainController.getArtData());
		cbbAuthor.setItems(mainController.getAuthorData());
		cbbArtType.setItems(mainController.getArtTypeData());
	}
	
	/**
	 * réinitialise la zone de création/modification d'œuvre
	 */
	private void resetArtCreateEdit() {
		txtArtTitle.setText("");
		txtArtCode.setText("");
		txtArtDates.setText("");
		txtMaterials.setText("");
		txtDimX.setText("");
		txtDimY.setText("");
		txtDimZ.setText("");
	}
	
	/**
	 * affiche la zone des informations de l'œuvre
	 */
	private void showArtInfo() {
		if (selectedArtLine != -1) {
			Art selectedArt = artTable.getItems().get(selectedArtLine);
			lblArtTitle.setText(selectedArt.getArt_title());
			lblArtCode.setText(selectedArt.getArt_code());
			lblArtDates.setText(selectedArt.getCreation_date());
			lblMaterials.setText(selectedArt.getMaterials());
			lblArtX.setText(selectedArt.getDim_x()+"");
			lblArtY.setText(selectedArt.getDim_y()+"");
			lblArtZ.setText(selectedArt.getDim_z()+"");
			lblAuthor.setText(selectedArt.getAuthor().getLast_name() + " " + selectedArt.getAuthor().getFirst_name());
			lblArtType.setText(selectedArt.getArt_type().getName());
			lblArtStatus.setText(selectedArt.getArt_status().getName());
			// TODO afficher l'illustration de cette oeuvre
			
			/*
			// Code non fonctionnel (+ tests) pour tenter de récupérer l'image dans la BD (mais toujours = null)
			// Les informations entrées dans la BD ne sont pas bonnes ? Pas le bon format ? Pas bien récupérées ? 
			
			Image image = null;
			try {
				Art selectedArt = artTable.getItems().get(selectedArtLine);
				System.out.println(selectedArt);
				System.out.println("image bytes : " + selectedArt.getImage().length);
				InputStream imgStream = new ByteArrayInputStream(selectedArt.getImage());
				System.out.println("imgStream = " + imgStream);
				BufferedImage img2 = ImageIO.read(imgStream);
				System.out.println("img2 = " + img2);
				ImageIO.write(img2, "jpg", new File("output.jpg"));
				URL imageURL = getClass().getClassLoader().getResource("output.jpg");
				image = new Image(imageURL.toExternalForm());			
				Image nouvelleImg = new Image(imgStream);
				// imgArt.setImage(nouvelleImg);			
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (image == null) {
				System.out.println("image vide");
			} else {
				System.out.println(image);
			}
			*/
			pneArtInfo.setVisible(true);
		}		
	}
	
	private void showArtEditingPane() {
		pneArtInfo.setVisible(false);
		pneArtCreatEdit.setVisible(true);
		artTable.setDisable(true);
	}
	
	private void hideArtEditingPane() {
		pneArtInfo.setVisible(false);
		pneArtCreatEdit.setVisible(false);
		artTable.setDisable(false);
	}
	
	/**
	 * demande au contrôleur principal d'ajouter une œuvre
	 */
	private void addArt() {
		try {
			String artTitle = txtArtTitle.getText();
			String artCode = txtArtCode.getText();
			String artDates = txtArtDates.getText();
			String artMaterials = txtMaterials.getText();			
			int artDimX = Integer.parseInt(txtDimX.getText());
			int artDimY = Integer.parseInt(txtDimY.getText());
			int artDimZ = Integer.parseInt(txtDimZ.getText());
			Author author = cbbAuthor.getValue();
			ArtType artType = cbbArtType.getValue();
			mainController.addArt(artCode, artTitle, artDates, artMaterials, artDimX, artDimY, artDimZ,
					null, author, null, artType);
		} catch (Exception e) {
			mainController.notifyFail();
		}
	}
	
	/**
	 * à la demande du contrôleur principal, affiche une notification
	 */
	public void notifyArtSaved(String message) {
		if (notifWindow.getModality() != Modality.APPLICATION_MODAL) {
			notifWindow.initModality(Modality.APPLICATION_MODAL);
		};		
		try {
			// lien avec la vue
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/NotifArt.fxml"));
			// passage de ce contrôleur à la vue
			loader.setController(this);
			dialogArtSaved = (Pane)loader.load();
			// réinitialisation et désactivation de la zone d'édition de salle
			resetArtCreateEdit();
			// réactivation de la liste d'oeuvres
			artTable.setDisable(false);
			// réinitialisation des drapeaux d'ajout/modification
			addingArt = false;
			updatingArt = false;
			// masque la fenêtre de modif, affiche la fenêtre d'info
			hideArtEditingPane();
			// affichage de la fenêtre pop-up
			lblNotification.setText(message);
			Scene scene = new Scene(dialogArtSaved);
			notifWindow.setScene(scene);
			notifWindow.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// récupération de la table mise à jour
		refreshData();
	}
	
	
	/*  ---------------------------
	 * 
	 *    MÉTHODES LIEES À LA VUE
	 * 
	 *  --------------------------- */
	
	/**
	 * TODO à l'ouverture de la fenêtre, initialise je sais pas quoi, à revoir
	 */
	@FXML
	private void initialize() {
		codeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArt_code()));
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArt_title()));
		authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor().getLast_name()));
	}
	
	/**
	 * event listener du bouton "Modifier" une œuvre
	 * @param e
	 */
	@FXML
	private void handleArtUpdate(ActionEvent event) {
		// la modification est possible seulement si une salle est sélectionnée
		if (selectedArtLine != -1) {
			try {
				Art selectedArt = artTable.getItems().get(selectedArtLine);
				updatingArt = true;
				lblArtCreatEditTitle.setText("Modification des informations de l'œuvre");
				txtArtTitle.setText(selectedArt.getArt_title());
				txtArtCode.setText(selectedArt.getArt_code());
				txtArtDates.setText(selectedArt.getCreation_date());
				txtMaterials.setText(selectedArt.getMaterials());				
				txtDimX.setText(selectedArt.getDim_x()+"");
				txtDimY.setText(selectedArt.getDim_y()+"");
				txtDimZ.setText(selectedArt.getDim_z()+"");
				cbbAuthor.setValue(selectedArt.getAuthor());
				cbbArtType.setValue(selectedArt.getArt_type());
				showArtEditingPane();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	/**
	 * event listener du bouton "Ajouter" une œuvre
	 * @param e
	 */
	@FXML
	private void handleArtAddition(ActionEvent e) {
		addingArt = true;
		lblArtCreatEditTitle.setText("Saisie d'une nouvelle œuvre");
		showArtEditingPane();
	}
	
	/**
	 * event listener du bouton "Annuler" l'ajout ou la modification d'une œuvre
	 * @param e
	 */
	@FXML
	private void handleCancelCreatEdit(ActionEvent e) {
		addingArt = false;
		updatingArt = false;
		resetArtCreateEdit();
		hideArtEditingPane();
		showArtInfo();
	}
	
	/**
	 * event listener du bouton "Sauvegarder" l'ajout ou la modification d'une œuvre
	 * @param e
	 */
	@FXML
	private void handleSaveArt(ActionEvent e) {
		// on vérifie d'abord que tous les champs sont remplis
		if (txtArtTitle.getText() != "" && txtArtCode.getText() != "" && txtArtDates.getText() != "" &&
			txtMaterials.getText() != "" && txtDimX.getText() != "" && txtDimY.getText() != "" &&
			txtDimZ.getText() != "" && cbbAuthor.getValue() != null && cbbArtType.getValue() != null) {
			if (addingArt) {
				addArt();
			}
			else if (updatingArt) {
				//updateArt();
			}
		} else {
			mainController.notifyFail();
		}
	}
	
	/**
	 * event listener du bouton "OK" du pop-up de notification
	 * @param e
	 */
	@FXML
	private void confirmArtSaved(ActionEvent e) {
		notifWindow.close();
	}
	
	/**
	 * event listener de la liste d'œuvres, permet de récupérer la ligne sélectionnée
	 */
	@FXML
	private void handleArtTableAction(MouseEvent event) {
		selectedArtLine = artTable.getSelectionModel().getSelectedIndex();
		showArtInfo();		
	}	
}
