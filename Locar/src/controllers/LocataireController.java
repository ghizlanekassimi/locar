package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;

import application.ConnexionMysql;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Locataire;

public class LocataireController implements Initializable {
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result ;
	@FXML
	private Button btn_add;


	  @FXML
	    private Button btn_delete;

	@FXML
	private Button btn_edit;

	    @FXML
	    private TableColumn<Locataire, String> cin_cin;

	    @FXML
	    private TableColumn<Locataire, Date> cin_date;

	    @FXML
	    private TableColumn<Locataire, Integer> cin_id;

	    @FXML
	    private TableColumn<Locataire, String> cin_nom;

	    @FXML
	    private TableColumn<Locataire, String> cin_tele;

	    @FXML
	    private DatePicker datePicker;

	    @FXML
	    private TableView<Locataire> table_locataire;

	    @FXML
	    private TextField txt_CIN;

	    @FXML
	    private TextField txt_nom;

	    @FXML
	    private TextField txt_searchCIN;

	    @FXML
	    private TextField txt_tele;
	    public ObservableList<Locataire> data = FXCollections.observableArrayList();

	    @FXML
	    void addLocataire() {
	    	String nom= txt_nom.getText();
	    	String tele = txt_tele.getText();
	    	String cin =txt_CIN.getText();

	    	String sql="insert into locataire(nomprenomL,datenaissL,teleL,CIN) values (?,?,?,?)";
	    	if (!nom.equals("")&& !tele.equals("")&& !cin.equals("") && !datePicker.getValue().equals(null)){
	    		try {
					st=cnx.prepareStatement(sql);
					st.setString(1, nom);
					java.util.Date date= java.util.Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
					Date sqlDate = new Date(date.getTime());
					st.setDate(2, sqlDate);
					st.setString(3, tele);
					st.setString(4, cin);
					st.execute();
					txt_CIN.setText("");
					txt_nom.setText("");
					txt_searchCIN.setText("");
					txt_tele.setText("");
					datePicker.setValue(null);
					Alert alert =new Alert(AlertType.CONFIRMATION,"Locataire ajouté avec succès ",javafx.scene.control.ButtonType.OK);
					alert.showAndWait();
					showLocataire();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	    	}
	    	else{
	    		Alert alert =new Alert(AlertType.WARNING,"Veuillez remplir tous les champs! ",javafx.scene.control.ButtonType.OK);
				alert.showAndWait();

	    	}


	    }

	    @FXML
	    void deleteLocataire() {
	    	String sql="delete from locataire where CIN ='"+txt_searchCIN.getText()+"'";
	    	try {
				st=cnx.prepareStatement(sql);
				st.executeUpdate();
				txt_CIN.setText("");
				txt_nom.setText("");
				txt_searchCIN.setText("");
				txt_tele.setText("");
				datePicker.setValue(null);
				Alert alert =new Alert(AlertType.CONFIRMATION,"Locataire supprimer avec succès ",javafx.scene.control.ButtonType.OK);
				alert.showAndWait();
				showLocataire();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }

	    @FXML
	    void editLocataire() {
	    	String nom= txt_nom.getText();
	    	String tele = txt_tele.getText();
	    	String cin =txt_CIN.getText();

	    	String sql ="update locataire set nomprenomL=?, datenaissL=?, teleL=?, CIN=? where CIN ='"+txt_searchCIN.getText()+"'";
	    	if (!nom.equals("")&& !tele.equals("")&& !cin.equals("") && !datePicker.getValue().equals(null)){
	    	try {
	    		st=cnx.prepareStatement(sql);
				st.setString(1, nom);
				java.util.Date date= java.util.Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
				Date sqlDate = new Date(date.getTime());
				st.setDate(2, sqlDate);
				st.setString(3, tele);
				st.setString(4, cin);
				st.executeUpdate();
				txt_CIN.setText("");
				txt_nom.setText("");
				txt_searchCIN.setText("");
				txt_tele.setText("");
				datePicker.setValue(null);
				Alert alert =new Alert(AlertType.CONFIRMATION,"Locataire modifié avec succès ",javafx.scene.control.ButtonType.OK);
				alert.showAndWait();
				showLocataire();

			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	}
	    	else{
	    		Alert alert =new Alert(AlertType.WARNING,"Veuillez remplir tous les champs! ",javafx.scene.control.ButtonType.OK);
				alert.showAndWait();

	    	}

	    }

	    @FXML
	    void searchLocataire() {
	    	String sql ="select nomprenomL,CIN,datenaissL,teleL from locataire where CIN ='"+txt_searchCIN.getText()+"'";
	    	int n=0;
	    	try {
				st=cnx.prepareStatement(sql);
				result = st.executeQuery();
				if(result.next()){
					txt_CIN.setText(result.getString("CIN"));
					txt_tele.setText(result.getString("teleL"));
					txt_nom.setText(result.getString("nomprenomL"));
					Date date= result.getDate("datenaissL");
					datePicker.setValue(date.toLocalDate());
					n=1;
				}


			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	if(n==0){
	    		Alert alert =new Alert(AlertType.ERROR,"Aucun locataire trouvé avec CIN ="+txt_searchCIN.getText()+"",javafx.scene.control.ButtonType.OK);
	    		alert.showAndWait();
	    	}
	    }
	    public void showLocataire(){
	    	table_locataire.getItems().clear();
	    	String sql="select * from locataire ";
	    	try {
				st=cnx.prepareStatement(sql);
				result =st.executeQuery();
				while(result.next()){
					data.add(new Locataire(result.getInt("idL"),result.getString("nomprenomL"),result.getDate("datenaissL"),result.getString("teleL"),result.getString("CIN")));
				}



			} catch (SQLException e) {
				e.printStackTrace();
			}
	    cin_cin.setCellValueFactory(new PropertyValueFactory<Locataire , String>("cin"));
	    cin_date.setCellValueFactory(new PropertyValueFactory<Locataire , Date>("dateNaiss"));
	    cin_id.setCellValueFactory(new PropertyValueFactory<Locataire , Integer>("id"));
	    cin_nom.setCellValueFactory(new PropertyValueFactory<Locataire , String>("nomprenom"));
	    cin_tele.setCellValueFactory(new PropertyValueFactory<Locataire , String>("tele"));
	    table_locataire.setItems(data);



	    }
	    @FXML
	    void tableLocataireEvent() {
	    	Locataire locataire =table_locataire.getSelectionModel().getSelectedItem();
	    	String sql ="select * from locataire where idL= ?";
	    	try {
				st=cnx.prepareStatement(sql);
				st.setInt(1, locataire.getId());
				result=st.executeQuery();
				if(result.next()){
					txt_CIN.setText(result.getString("CIN"));
					txt_tele.setText(result.getString("teleL"));
					txt_nom.setText(result.getString("nomprenomL"));
					Date date= result.getDate("datenaissL");
					datePicker.setValue(date.toLocalDate());
					txt_searchCIN.setText(result.getString("CIN"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

	    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cnx=ConnexionMysql.connexionDB();
		showLocataire();
	}

}