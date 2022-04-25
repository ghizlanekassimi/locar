package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;



import application.ConnexionMysql;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class SingInController implements Initializable{

	Connection cnx;
	public PreparedStatement st;
	public ResultSet result ;

	@FXML
    private Button btn_passwordForgoten;

    @FXML
    private Button btn_seconnecter;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_userName;

    @FXML
    private VBox VBox;
    private Parent fxml;

    @FXML
    void openHome() {
    	String nom = txt_userName.getText();
    	String pass = txt_password.getText();
    	String sql = "select userName,password from admin";
    	try{
    		st = cnx.prepareStatement(sql);
    		result = st.executeQuery();
    		if(result.next()){
    			if(nom.equals(result.getString("userName"))&&pass.equals(result.getString("password"))){
    	    		System.out.println("nom et mot de pass sont correcte");
    	    		VBox.getScene().getWindow().hide();
    	    		Stage home = new Stage();
    	    		try {
    					fxml = FXMLLoader.load(getClass().getResource("/interfaces/Home.fxml"));
    					Scene scene = new Scene(fxml);
    					home.setScene(scene);
    					home.show();
    	    		} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}

    	    	}else{
    	    		Alert alert = new Alert(AlertType.ERROR,"nom de l'utulisatuer ou mot de pass est incorrecte",javafx.scene.control.ButtonType.OK);
    	    		alert.showAndWait();
    	    	}
    		}
    	}catch(SQLException el){
    		el.printStackTrace();
    	}


    }

    @FXML
    void sendPassword() {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cnx = ConnexionMysql.connexionDB();

	}

}
