package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import models.SpaBeauty;
import models.Vente;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;

public class CommandesController  implements Initializable {
	@FXML
	private TextField txtdate;
	@FXML
	private TableView<Vente> tabachats;
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
	private TextField num;
	@FXML
	private TextField date;
	@FXML
	private TextField deal;
	@FXML
	private TextField prix;
	@FXML
	private TextField fournisseur;
	
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
	

	// Event Listener on Button.onAction
	@FXML
	public void imprimer(ActionEvent event) {
		// TODO Autogenerated
		int cin=0;
		String nom = null;
		String prenom = null;
		int numFact = tabachats.getSelectionModel().getSelectedItem().getNum();
		Connection conn = getConnection();
		
		try {
		    ResultSet rsc = conn.createStatement().executeQuery("SELECT CIN,nom,prenom FROM currentuser ");
		    while (rsc.next()) {
		        cin=rsc.getInt("CIN");
		        nom=rsc.getString("nom");
		        prenom=rsc.getString("prenom");
		    }
		} catch (SQLException ex) {
	            System.out.println("Donn�es introuvables");
	            Logger.getLogger(CommandesController.class.getName()).log(Level.SEVERE, null, ex);
	     	}
		 String date=tabachats.getSelectionModel().getSelectedItem().getDate();
		 String deal=tabachats.getSelectionModel().getSelectedItem().getDeal();
		 String four=tabachats.getSelectionModel().getSelectedItem().getFournisseur();
		 Float prix=tabachats.getSelectionModel().getSelectedItem().getPrixdeal();

	     Document document = new Document();
	        try{
	            PdfWriter.getInstance(document, new FileOutputStream("Facture N."+numFact+".pdf"));
	            document.open();

	            Font f=new Font(FontFactory.getFont(FontFactory.TIMES_BOLD,24,Font.UNDERLINE));
	            f.setColor(241,0,119);
	            
	            Font f2=new Font(FontFactory.getFont(FontFactory.TIMES_BOLD,18,Font.BOLD));
	            f2.setColor(194,0,76);
	            
	            Paragraph p1 = new Paragraph("Bon Commande N� :  "+ numFact ,f);
	            p1.setAlignment(Paragraph.ALIGN_CENTER);
	            Paragraph p0 = new Paragraph("     ");
	                 
	            Paragraph p2 = new Paragraph("____________________________________________________________________________");
	            Paragraph p3 = new Paragraph("      Cin du Client :  "+"  "+cin+"      Nom du Client :  "+"  "+nom+" "+prenom);
	            Paragraph p4 = new Paragraph("      Deal Achet� :   " +"  "+deal+"      Fournisseur :   " +"  "+four);
	            Paragraph p5 = new Paragraph("      Montant :   " +"  "+prix);
	            Paragraph p6 = new Paragraph("      Date Achat :  "+"  "+date);
	            Paragraph p9 = new Paragraph("Bienvenue au Best Deal ",f2);
	            p9.setAlignment(Paragraph.ALIGN_RIGHT);
	            
		    
	            document.add(p0);
	            document.add(p1);
	            document.add(p0);
	            document.add(p2);
	            document.add(p0);
	            document.add(p0);
	            document.add(p6);
	            document.add(p0);
	            
	            document.add(p3);
	            document.add(p0);
	            
	            document.add(p4);
	            document.add(p0);
	            
	            document.add(p5);
	            document.add(p0);
	            
	            document.add(p2);
	            document.add(p0);
	            document.add(p0);
	            document.add(p9);
	            
	               
	        }
	        catch(DocumentException | FileNotFoundException e){
	            System.out.println(e);
	        }
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Facture ( "+ numFact + " ) du PATIENT  ( " + nom+" "+prenom + " )   g�n�r� avec succ�s !");
            alert.showAndWait();
	        document.close();
	    }

	// Event Listener on Button.onAction
	@FXML
	public void chercherAchat(ActionEvent event) {
		// TODO Autogenerated
		ObservableList<Vente> data = FXCollections.observableArrayList();
		int cin=0;
		int cinclt=0;
		if ( !txtdate.getText().equals("")) {
			Connection conn = getConnection();
			try {
		           ResultSet rsc = conn.createStatement().executeQuery("SELECT CIN FROM currentuser ");
		           while (rsc.next()) {
		        	   cin=rsc.getInt("CIN");		           }
			} catch (SQLException ex) {
	            System.out.println("Cin introuvable");
	            Logger.getLogger(CommandesController.class.getName()).log(Level.SEVERE, null, ex);

	        }
			try { 
		           ResultSet rscl= conn.createStatement().executeQuery("SELECT CIN FROM clients where CIN =' "+cin+"'");
		           while (rscl.next()) {
		        	   cinclt=rscl.getInt("CIN");
		        	   } 
			} catch (SQLException ex) {
	            System.out.println("Cin introuvable");
	            Logger.getLogger(CommandesController.class.getName()).log(Level.SEVERE, null, ex);

	        } 
	    	try {
	           ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM vente where cin_clt = '"+cinclt+"' and date like '"+txtdate.getText()+"'");
	           if (rs.next()) {
	        	   data.add(new Vente(rs.getInt("num"),rs.getInt("cin_clt"),rs.getString("fournisseur"),rs.getString("date"),rs.getString("deal"),rs.getFloat("prixdeal")));
	           }
	           else {
	        	   Alert alert = new Alert(AlertType.WARNING,"Aucune commande trouv� pour le "+txtdate.getText()+" !",javafx.scene.control.ButtonType.OK);
				 	alert.showAndWait(); 
	           }
		} catch (SQLException ex) {
            System.out.println("Erreur d'ajout !");
            Logger.getLogger(AjouterSBController.class.getName()).log(Level.SEVERE, null, ex);

        }
				colnum.setCellValueFactory(new PropertyValueFactory<>("num"));
	    		coldeal.setCellValueFactory(new PropertyValueFactory<>("deal"));
				colfr.setCellValueFactory(new PropertyValueFactory<>("fournisseur"));
				colprix.setCellValueFactory(new PropertyValueFactory<>("prixdeal"));
				coldate.setCellValueFactory(new PropertyValueFactory<>("date"));

				tabachats.setItems(data);
 
		}
		 else {
			 	Alert alert = new Alert(AlertType.WARNING,"Veuillez choisir une date !",javafx.scene.control.ButtonType.OK);
			 	alert.showAndWait(); 
	       } 
	}
	private void setcellvalue(){
		tabachats.setOnMouseClicked(e-> { 
            Vente v = tabachats.getItems().get(tabachats.getSelectionModel().getSelectedIndex());
            deal.setText(v.getDeal());
            fournisseur.setText(v.getFournisseur());
            prix.setText(Float.toString(v.getPrixdeal()));
            date.setText(v.getDate());
            num.setText(Integer.toString(v.getNum()));
         
        });
   }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setcellvalue();
	}
}
