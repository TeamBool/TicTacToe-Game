package de.htwsaar.tictactoe.gui;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
 public LoginModel logmdl = new LoginModel();
 
 
   @FXML
   private Label isConnected;

   @FXML
   private TextField txtusername;

   @FXML
   private TextField txtpassword;
   
   
   
 @Override
 public void initialize(URL location, ResourceBundle resources) {

  if (logmdl.isDbConnected()) {
   isConnected.setText("Connected");
  } else {

   isConnected.setText("Not Connected");}
  }
 
  public void Login(ActionEvent event)  {
	  try {
		  	if (logmdl.isLogin(txtusername.getText(), txtpassword.getText())) {
		  		isConnected.setText("Login erfolgreich");
		  		Stage primaryStage = new Stage();
		  		Parent root = FXMLLoader.load(getClass().getResource("/userinterface/main.fxml"));
		  	    Scene scene = new Scene(root);
		  	    primaryStage.setScene(scene);
		  	    primaryStage.show();
	  }else {
		  isConnected.setText("Username oder Password falsch");
	  }
	  }catch (SQLException e) {
	  isConnected.setText("Username oder Password falsch");
	  e.printStackTrace();
	  } catch (IOException e) {
		e.printStackTrace();
	}
  }

  public void doExit(){
      Platform.exit();
  }
  
public void backbutton(ActionEvent event)throws Exception {
    
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("userinterface/startside.fxml"));
        Stage stage = new Stage();
        stage.setTitle(" ");
        stage.setScene(new Scene(root, 600, 350));
        stage.show();
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
}}