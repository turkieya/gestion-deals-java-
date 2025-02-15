package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

public class ProfilController implements Initializable{
	@FXML
	private TextField txtcin;
	@FXML
	private TextField txtnom;
	@FXML
	private TextField txtprenom;
	@FXML
	private TextField txtemail;
	@FXML
	private PasswordField txtmdp;
	@FXML
	private TextField txttel;
	@FXML
	private TextField txtadresse;

	// Event Listener on Button.onAction
	@FXML
	public void modifier(ActionEvent event) {
		// TODO Autogenerated
		int cin=0;
    	PreparedStatement pst;
    	Connection conn = getConnection();
    	try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT CIN from currentuser");
            while (rs.next()) {
            	 cin=rs.getInt("CIN");
            }
    	    }catch (SQLException ex) {
                Logger.getLogger(AchatsController.class.getName()).log(Level.SEVERE, null, ex);
    	    }
    	String requete="UPDATE clients set CIN='"+txtcin.getText()+"',nom='"+txtnom.getText()+"',prenom='"+txtprenom.getText()+"',email='"+txtemail.getText()+"',mdp='"+txtmdp.getText()+"',tel='"+txttel.getText()+"',adresse='"+txtadresse.getText()+"' where CIN='"+cin+"'";
    	String requete2="UPDATE currentuser set CIN='"+txtcin.getText()+"',nom='"+txtnom.getText()+"',prenom='"+txtprenom.getText()+"',email='"+txtemail.getText()+"',mdp='"+txtmdp.getText()+"',tel='"+txttel.getText()+"',adresse='"+txtadresse.getText()+"' where CIN='"+cin+"'";

    	if (!txtcin.getText().equals("") && !txtnom.getText().equals("") && !txtprenom.getText().equals("") && !txtemail.getText().equals("") && !txtmdp.getText().equals("") && !txttel.getText().equals("") && !txtadresse.getText().equals("")) 
        { 
         try
         {	
            pst = conn.prepareStatement(requete);
             pst.executeUpdate(requete);
             pst = conn.prepareStatement(requete2);
             pst.executeUpdate(requete2);
             System.out.println("Client modifi� avec succ�s !");
             
             
         } catch (SQLException ex) {
             System.out.println("Erreur de modification !");
         }
 		
 		Alert alert = new Alert(AlertType.INFORMATION,"Profil modifi� avec succ�s",javafx.scene.control.ButtonType.OK);
		 alert.showAndWait();
        }
        else {
     	   Alert alert = new Alert(AlertType.WARNING,"Veuillez remplir tous les champs !",javafx.scene.control.ButtonType.OK);
   		 alert.showAndWait();
        } 
	}

	public Connection getConnection() {
		Connection  conn;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_deals","root","");
			return conn;
		} catch(Exception ex) {
			System.out.print("Error: "+ex.getMessage());
			return null; 
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * from currentuser");
            while (rs.next()) {
            	txtcin.setText(Integer.toString(rs.getInt("CIN")));
            	 txtnom.setText(rs.getString("nom"));
            	 txtprenom.setText(rs.getString("prenom"));
            	 txtemail.setText(rs.getString("email"));
            	 txtmdp.setText(rs.getString("mdp"));
            	 txtadresse.setText(rs.getString("adresse")); 
            	 txttel.setText(rs.getString("tel"));
            }
    	    }catch (SQLException ex) {
                Logger.getLogger(AchatsController.class.getName()).log(Level.SEVERE, null, ex);
    	    }
		
	}
}
