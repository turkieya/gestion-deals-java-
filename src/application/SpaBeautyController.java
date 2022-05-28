package application;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Exceptions.ClientException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import models.Deal;
import models.Panier;


public class SpaBeautyController implements Initializable {

	@FXML
	private Label txtnom;
	@FXML
	private Label txtfr;
    @FXML 
    private TextArea txtdescription;
    @FXML
    private TextArea txtdurree;
    @FXML
    private TextArea txtadresse;
    @FXML
    private TextArea txthoraire;
    @FXML
    private TextArea txtcontact;
    @FXML
    private Label txtprix;
	@FXML
	private ImageView precedent;
	@FXML
	private ImageView next;
    @FXML
    private ImageView imagesb;
   // private int idp=0;
   // private List<String> deals= new ArrayList <String>();
    
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
	public void afficher() {
		  try {
	        	Connection conn = getConnection();
	        	byte byteImg[];
	        	Blob blob;
	            ResultSet rs = conn.createStatement().executeQuery ("SELECT * FROM spabeauty s, fournisseurs f where f.nom_commercial like s.fournisseur ");
            	//ResultSet rs_fr = conn.createStatement().executeQuery ("SELECT adresse,tel,horraire,email FROM fournisseurs f,spabeauty s where s.fournisseur like f.nom_commercial ");

	            while(rs.next())
	            { 
	            	txtnom.setText(rs.getString("nom"));
	            	txtprix.setText("A "+Float.toString(rs.getFloat("prix_deal"))+"DT au lieu de "+Float.toString(rs.getFloat("prix_initial"))+"DT");
	            	txtfr.setText(rs.getString("fournisseur"));
	            	txtdurree.setText("Offre valable du "+rs.getString("date_deb")+" au "+rs.getString("date_fin"));
	            	txtdescription.setText(rs.getString("description"));
	            	txtadresse.setText(rs.getString("adresse"));
	            	txthoraire.setText(rs.getString("horraire"));
	            	txtcontact.setText("Tel : "+Integer.toString(rs.getInt("tel"))+"\nEmail : "+rs.getString("email"));
	            	
	            	blob=rs.getBlob("image");
	            	byteImg=blob.getBytes(1, (int) blob.length());
	            	Image img=new Image(new ByteArrayInputStream(byteImg),imagesb.getFitWidth(),imagesb.getFitHeight(),true,true);
	            	imagesb.setImage(img); 
	            }
	            
	           
	        } catch (SQLException ex) {
	            Logger.getLogger(SpaBeautyController.class.getName()).log(Level.SEVERE, null, ex);
	        } 
	}

	// Event Listener on ImageView[#precedent].onMouseClicked
	@FXML
	public void showPrecedent(MouseEvent event) {
		// TODO Autogenerated
		Connection conn = getConnection();
		String nom=txtnom.getText();
		int pos = 0;
		try {
            ResultSet rs = conn.createStatement().executeQuery ("select id from spabeauty where nom='"+nom+"'");
            if(rs.next())
            { 
            	pos=rs.getInt("id");
            }
          // System.out.print(pos);
            try { 
	        	byte byteImg[]; 
	        	Blob blob;
	        	ResultSet rsn = conn.createStatement().executeQuery ("SELECT * FROM spabeauty s,fournisseurs f where s.id < '"+pos+"' and f.nom_commercial like s.fournisseur ");
	         	//ResultSet rs_fr = conn.createStatement().executeQuery ("SELECT adresse,tel,horraire,email FROM fournisseurs f,spabeauty s where s.fournisseur like f.nom_commercial");
	      
	         	while(rsn.next())
	            { 
	            	txtnom.setText(rsn.getString("nom"));
	            	txtprix.setText("A "+Float.toString(rsn.getFloat("prix_deal"))+"DT au lieu de "+Float.toString(rsn.getFloat("prix_initial"))+"DT");
	            	txtfr.setText(rsn.getString("fournisseur"));
	            	txtdurree.setText("Offre valable du "+rsn.getString("date_deb")+" au "+rsn.getString("date_fin"));
	            	txtdescription.setText(rsn.getString("description"));
	            	txtadresse.setText(rsn.getString("adresse"));
	            	txthoraire.setText(rsn.getString("horraire"));
	            	txtcontact.setText("Tel : "+Integer.toString(rsn.getInt("tel"))+"\nEmail : "+rsn.getString("email"));
	            	
	            	blob=rsn.getBlob("image");
	            	byteImg=blob.getBytes(1, (int) blob.length());
	            	Image img=new Image(new ByteArrayInputStream(byteImg),imagesb.getFitWidth(),imagesb.getFitHeight(),true,true);
	            	imagesb.setImage(img); 
	            }
	            
	           
	        } catch (SQLException ex) {
	            Logger.getLogger(SpaBeautyController.class.getName()).log(Level.SEVERE, null, ex);
	        } 
			
			
			
		} catch (SQLException ex) {
            Logger.getLogger(SpaBeautyController.class.getName()).log(Level.SEVERE, null, ex);
        } 
	}
	// Event Listener on ImageView[#next].onMouseClicked
	@FXML
	public void showSuivant(MouseEvent event) {
		// TODO Autogenerated
		Connection conn = getConnection();
		String nom=txtnom.getText();
		int pos = 0;
		try {
            ResultSet rs = conn.createStatement().executeQuery ("select id from spabeauty where nom='"+nom+"'");
            if(rs.next())
            { 
            	pos=rs.getInt("id");
            }
        //   System.out.print(pos);
            try {
	        	byte byteImg[]; 
	        	Blob blob;
	        	ResultSet rsn = conn.createStatement().executeQuery ("SELECT * FROM spabeauty s,fournisseurs f where s.id = '"+(pos+1)+"' and f.nom_commercial like s.fournisseur");
	      
	         	while(rsn.next())
	            { 
	            	txtnom.setText(rsn.getString("nom"));
	            	txtprix.setText("A "+Float.toString(rsn.getFloat("prix_deal"))+"DT au lieu de "+Float.toString(rsn.getFloat("prix_initial"))+"DT");
	            	txtfr.setText(rsn.getString("fournisseur"));
	            	txtdurree.setText("Offre valable du "+rsn.getString("date_deb")+" au "+rsn.getString("date_fin"));
	            	txtdescription.setText(rsn.getString("description"));
	            	txtadresse.setText(rsn.getString("adresse"));
	            	txthoraire.setText(rsn.getString("horraire"));
	            	txtcontact.setText("Tel : "+Integer.toString(rsn.getInt("tel"))+"\nEmail : "+rsn.getString("email"));
	            	
	            	blob=rsn.getBlob("image");
	            	byteImg=blob.getBytes(1, (int) blob.length());
	            	Image img=new Image(new ByteArrayInputStream(byteImg),imagesb.getFitWidth(),imagesb.getFitHeight(),true,true);
	            	imagesb.setImage(img); 
	            }
	            
	            
	           
	        } catch (SQLException ex) {
	            Logger.getLogger(SpaBeautyController.class.getName()).log(Level.SEVERE, null, ex);
	        } 
	
		} catch (SQLException ex) {
            Logger.getLogger(SpaBeautyController.class.getName()).log(Level.SEVERE, null, ex);
        }  
	}
	/*Panier p = new Panier(idp+1);
	// Event Listener on ImageView[#btnajoutSB].onMouseClicked
	@FXML
	  void ajouterSB(MouseEvent event) throws IOException, DealException {
		//Connection conn = getConnection();
		String nom=txtnom.getText();
		if (!deals.add(nom)) {
			throw new DealException("Le Deal est r�serv� d�j� !");
	}
		 Alert alert = new Alert(AlertType.CONFIRMATION,"Deal ajout� avec succ�s",javafx.scene.control.ButtonType.OK);
	  	alert.showAndWait();
		
		
    }*/
	/*  @FXML
	    void afficherpanier(MouseEvent event) throws IOException {
			Stage stage = new Stage();
			AnchorPane root = new AnchorPane();
			Scene scene = new Scene(root,500,700); 
			stage.setScene(scene);
			stage.show();
			stage.setResizable(false);
			for (int i=0;i<deals.size();i++)
			{ 
				VBox hb = new VBox(2);
				root.getChildren().addAll(hb);
				Label nom_deal = new Label("Deal");
				nom_deal.setFont(Font.font(" SansSerif", FontWeight.SEMI_BOLD, 18));
				Label prix_deal = new Label("Prix Deal");
				prix_deal.setFont(Font.font(" SansSerif", FontWeight.SEMI_BOLD, 18));
				hb.setPadding(new Insets (20,5,5,50));
				hb.getChildren().addAll(nom_deal,prix_deal);
				
			}
			Iterator <String> lt = deals.iterator();
			System.out.println("liste des deals:");
			while(lt.hasNext()){
				System.out.print(lt.next()+" ");}
	    }*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		afficher();
		
	}


}
