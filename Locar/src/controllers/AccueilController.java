package controllers;


import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import application.ConnexionMysql;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class AccueilController implements Initializable {

	Connection cnx;
	public PreparedStatement st;
	public ResultSet result ;

	   @FXML
	    private ImageView imagelog;

	   @FXML
	    private TextField txt_adr;

	    @FXML
	    private Label lab_nbr;

	    @FXML
	    private ImageView precedant;

	    @FXML
	    private ImageView suivant;

	    @FXML
	    private TextField txt_loyer;

	    @FXML
	    private TextField txt_region;

	    @FXML
	    private TextField txt_superficie;

	    @FXML
	    void showPrecedant() {
	    	String adr= txt_adr.getText();
	    	String sql7 ="select idLogement from logement where adrL ='"+adr+"'";
	    	int position=0;
	    	try {
				st=cnx.prepareStatement(sql7);
				result=st.executeQuery();
				if(result.next()){
					position = result.getInt("idLogement");

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	String sql8="select loyer ,superficie,nomRegion,adrL, image from logement ,region where region.idRegion=logement.region and  logement.idLogement < '"+position+"'";
	    	int loyer=0;
	    	int superficie=0;
	    	byte byteImg[];
	    	Blob blob;
	    	try {
				st=cnx.prepareStatement(sql8);
				result=st.executeQuery();
				if(result.next()){
					loyer=result.getInt("loyer");
					txt_loyer.setText(Integer.toString(loyer));
					superficie=result.getInt("superficie");
					txt_superficie.setText(Integer.toString(superficie));
					txt_region.setText(result.getString("nomRegion"));
					txt_adr.setText(result.getString("adrL"));
					blob=result.getBlob("image");
					byteImg=blob.getBytes(1, (int) blob.length());
					Image img = new Image(new ByteArrayInputStream(byteImg) ,imagelog.getFitWidth(),imagelog.getFitHeight(),true ,true);
					imagelog.setImage(img);

				}
				else{
					System.out.println("aucun");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}


	    }

	    @FXML
	    void showSuivant() {
	    	String adr= txt_adr.getText();
	    	String sql3 ="select idLogement from logement where adrL ='"+adr+"'";
	    	int position=0;
	    	try {
				st=cnx.prepareStatement(sql3);
				result=st.executeQuery();
				if(result.next()){
					position = result.getInt("idLogement");

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	String sql6="select loyer ,superficie,nomRegion,adrL,image from logement ,region where region.idRegion=logement.region and  logement.idLogement > '"+position+"'";
	    	int loyer=0;
	    	int superficie=0;
	    	byte byteImg[];
	    	Blob blob;
	    	try {
				st=cnx.prepareStatement(sql6);
				result=st.executeQuery();
				if(result.next()){
					loyer=result.getInt("loyer");
					txt_loyer.setText(Integer.toString(loyer));
					superficie=result.getInt("superficie");
					txt_superficie.setText(Integer.toString(superficie));
					txt_region.setText(result.getString("nomRegion"));
					txt_adr.setText(result.getString("adrL"));
					blob=result.getBlob("image");
					byteImg=blob.getBytes(1, (int) blob.length());
					Image img = new Image(new ByteArrayInputStream(byteImg) ,imagelog.getFitWidth(),imagelog.getFitHeight(),true ,true);
					imagelog.setImage(img);

				}
				else{
					System.out.println("aucun");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

	    }
	    public void showLogement(){
	    	String sql="select count(*) from logement where idLogement not in(select logement from location)";
	    	int i=0;
	    	try {
				st=cnx.prepareStatement(sql);
				result=st.executeQuery();
				if(result.next()){
		    		i=result.getInt(1);
		    	}
		    	lab_nbr.setText(Integer.toString(i));
			} catch (SQLException e) {

				e.printStackTrace();
			}

	    	String sql2="select loyer ,superficie,nomRegion,adrL,image from logement ,region where region.idRegion=logement.region and idLogement not in(select logement from location )";
	    	int loyer=0;
	    	int superficie=0;
	    	byte byteImg[];
	    	Blob blob;
	    	try {
				st=cnx.prepareStatement(sql2);
				result=st.executeQuery();
				if(result.next()){
					loyer=result.getInt("loyer");
					txt_loyer.setText(Integer.toString(loyer));
					superficie=result.getInt("superficie");
					txt_superficie.setText(Integer.toString(superficie));
					txt_region.setText(result.getString("nomRegion"));
					txt_adr.setText(result.getString("adrL"));
					blob=result.getBlob("image");
					byteImg=blob.getBytes(1, (int) blob.length());
					Image img = new Image(new ByteArrayInputStream(byteImg) ,imagelog.getFitWidth(),imagelog.getFitHeight(),true ,true);
					imagelog.setImage(img);

				}



			} catch (SQLException e) {
				e.printStackTrace();
			}

	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cnx=ConnexionMysql.connexionDB();
		showLogement();

	}



}
