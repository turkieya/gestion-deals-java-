package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Panier;
import models.Vente;

public class HomeClientController implements Initializable{
	@FXML
	private BorderPane mainpane;
	@FXML
	private ImageView panier;
	@FXML
	private Label utilisateur;

    @FXML
    private Label nbdeals;
    @FXML
    private Label nbfr;
    @FXML
    private Label nbventes;
	@FXML
	private TableView <Vente> tabventes;
	@FXML
	private TableColumn<Vente,Integer> colnum;
	@FXML
	private TableColumn<Vente,String> coldate;
	@FXML
	private TableColumn<Vente,String> coldeal;
	@FXML
	private TableColumn <Vente,Float>colprix;
	@FXML
	private TableColumn <Vente,String>colfr;
	@FXML
	private TableColumn<Vente,Integer> colcin;


	// Event Listener on Button.onAction
	@FXML
	public void deals(ActionEvent event) {
		// TODO Autogenerated
		FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("DealClt");
		mainpane.setCenter(view);
	}

	// Event Listener on Button.onAction
	@FXML
	public void achats(ActionEvent event) {
		// TODO Autogenerated
		FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("Commandes");
		mainpane.setCenter(view);
	}

    @FXML
    void profile(ActionEvent event) {
    	FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("Profile");
		mainpane.setCenter(view);
    }
	
	// Event Listener on Button.onAction
	@FXML
	public void quitter(ActionEvent event) throws IOException {
		// TODO Autogenerated

    	PreparedStatement pst;
    	String requete="DELETE FROM currentuser";
    	 try
         {	Connection conn = getConnection();
            pst = conn.prepareStatement(requete);
             pst.executeUpdate(requete);
             System.out.println("User supprim� avec succ�s !");
             
         } catch (SQLException ex) {
             System.out.println("Erreur de suppression !");
         }
    	 Stage stage = new Stage();
    	 mainpane.getScene().getWindow().hide();
         Parent root;
         root = FXMLLoader.load(getClass().getResource("Home.fxml"));
         Scene scene = new Scene(root,600,400);
         stage.setScene(scene);
         stage.show();
         stage.setResizable(false);
	}

	// Event Listener on ImageView[#panier].onMouseClicked
	@FXML
	public void afficher_panier(MouseEvent event) {
		// TODO Autogenerated
		FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("Achats");
		mainpane.setCenter(view);
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
	public void ventes_auj() {
		// TODO Autogenerated
		ObservableList<Vente> data = FXCollections.observableArrayList();
		int cinc=0;
		Connection conn = getConnection();
		Date date = new Date( System.currentTimeMillis() );
	    SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
 
	    	try {
	    		ResultSet rsci = conn.createStatement().executeQuery("SELECT CIN from currentuser");
	            while (rsci.next()) {
	            	 cinc=rsci.getInt("CIN");
	            }
	           ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM vente where date like '"+sdf.format( date )+"' and cin_clt ='"+cinc+"'");
	           while (rs.next()) {
	        	   data.add(new Vente(rs.getInt("num"),rs.getInt("cin_clt"),rs.getString("fournisseur"),rs.getString("date"),rs.getString("deal"),rs.getFloat("prixdeal")));

	           }

		} catch (SQLException ex) {
            System.out.println("Erreur d'ajout !");
            Logger.getLogger(VentesController.class.getName()).log(Level.SEVERE, null, ex);

        }
	
				colnum.setCellValueFactory(new PropertyValueFactory<>("num"));
	    		coldeal.setCellValueFactory(new PropertyValueFactory<>("deal"));
				colfr.setCellValueFactory(new PropertyValueFactory<>("fournisseur"));
				colprix.setCellValueFactory(new PropertyValueFactory<>("prixdeal"));
				coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
				colcin.setCellValueFactory(new PropertyValueFactory<>("cin_clt"));

				tabventes.setItems(data);

	}
	  

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		String nom = null,prenom = null;
		Connection conn = getConnection();
		try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT nom,prenom from currentuser");
            while (rs.next()) {
            	 nom=rs.getString("nom");
            	 prenom=rs.getString("prenom");
            }
            utilisateur.setText(nom+" "+prenom);
    	    }catch (SQLException ex) {
                Logger.getLogger(AchatsController.class.getName()).log(Level.SEVERE, null, ex);
    	    }
		
		int f=0,sp=0,v=0,r=0;
		 
    	try {
           ResultSet rsv = conn.createStatement().executeQuery("SELECT * FROM vente");
           while (rsv.next()) {
        	   v=v+1;
           }
           ResultSet rsf = conn.createStatement().executeQuery("SELECT * FROM fournisseurs");
           while (rsf.next()) {
        	   f=f+1;
           }
           ResultSet rsb = conn.createStatement().executeQuery("SELECT * FROM spabeauty");
           while (rsb.next()) {
        	   sp=sp+1;
           }
           ResultSet rsr = conn.createStatement().executeQuery("SELECT * FROM restauration");
           while (rsr.next()) {
        	   r=r+1;
           }

	} catch (SQLException ex) {
        Logger.getLogger(VentesController.class.getName()).log(Level.SEVERE, null, ex);

	}
    	nbfr.setText(Integer.toString(f));
    	nbdeals.setText(Integer.toString(sp+r));
    	nbventes.setText(Integer.toString(v));
    	
    	ventes_auj();
	}

}
