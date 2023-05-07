package controllerView;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import application.Main;
import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import museum.Notify;
import museum.User;


public class LoginControl {
	
	private Main mainControler;
	
	@FXML
	private TextField txtLogin;
	@FXML
	private TextField txtPassword;
	
	public LoginControl() {
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
	 * event listener du bouton "Connexion"
	 * @param event
	 */
	@FXML
	private void handleConnectionClick(ActionEvent event) {
		User user = this.AuthorizeConnection();
		if(user != null) {
			// Curator
			if (user.getRole().getId_role() == 1) {
				mainControler.showCuratorPane();
			}
			// Architect
			else if (user.getRole().getId_role() == 2) {
				mainControler.showArchitectPane();
			}
			else {
				Notify.getInstance().showAlerte("Erreur Rôle","Erreur Rôle","Rôle inconnue !");
			}			
		} else {
			Notify.getInstance().showAlerte("Erreur de Connexion","Erreur de Connexion","Vérifier vôtre identifiant ou votre mot de passe");
		}
	}
	
	/**
	 * Vérifie si l'utisateur peut se connecter.
	 * @return un 'user' qui correspond au login, mot de passe.
	 */
	private User AuthorizeConnection() {
		User userAuthorize = null;
		String passwordSHA256 = this.hashPassword(this.txtPassword.getText());
		
		for (User user : UserDAO.getInstance().readAll()) {
			if(this.txtLogin.getText().equals(user.getLogin())){
				if(passwordSHA256.equals(user.getPassword())){
					userAuthorize = user;
				}
			}
		}
		return userAuthorize;
	}
	
	/**
	 * 
	 * @param Password = textFiled 
	 * @return un mot de passe hash en SHA256
	 */
	  private String hashPassword(String Password) {
	        try {
	            MessageDigest digest = MessageDigest.getInstance("SHA-256");
	            byte[] hashBytes = digest.digest(Password.getBytes(StandardCharsets.UTF_8));
	            return bytesToHex(hashBytes);
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException("Erreur lors du calcul du hash SHA-256", e);
	        }
	    }
	  
	    private String bytesToHex(byte[] bytes) {
	        StringBuilder hexString = new StringBuilder();
	        for (byte b : bytes) {
	            String hex = Integer.toHexString(0xff & b);
	            if (hex.length() == 1) {
	                hexString.append('0');
	            }
	            hexString.append(hex);
	        }
	        return hexString.toString();
	    }
}
