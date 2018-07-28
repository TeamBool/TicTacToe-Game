package de.htwsaar.tictactoe.gui;

import java.util.ResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class FXMLDocController {
    
    @FXML
    private Label lblstatus;
    @FXML
    private TextField txtpassword;
    @FXML
    private TextField txtusername;
    private Connection conection;
    private PreparedStatement statement;
    private ResultSet resultset;
    
    
    
    
    
   /** Buttons und Logik für die Gui Bedienung **/
    public void handlebutlog(ActionEvent event)throws Exception {
        
        {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("userinterface/log.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
           
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        
    }
    /** Testlogin für die Gui**/
    public void Login (ActionEvent event)throws Exception{
           if(txtusername.getText().equals("user")&& txtpassword.getText().equals("pass")){
               lblstatus.setText("login Success");
               Stage stage = new Stage();
               Parent root = FXMLLoader.load(getClass().getResource("/userinterface/main.fxml"));
               //Scene scene = new Scene(root,500,350);
               
               stage.setScene(new Scene(root, 600,570));
               stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
           }else {
                lblstatus.setText("Login Failed");
           }
    
    
    }


  public void signin(ActionEvent event)throws Exception {
       
        {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("userinterface/register.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Main Menue");
            stage.setScene(new Scene(root, 600, 350));
            stage.show();
            
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        
    }
  public void gamebutton(ActionEvent event)throws Exception {
       
        {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("userinterface/TicTacToe.fxml"));
            Stage stage = new Stage();
            stage.setTitle("TicTacToe");
            stage.setScene(new Scene(root, 600, 570));
            stage.show();
            
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        
    }
   public void gameroom(ActionEvent event)throws Exception {
       
        {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("userinterface/spielraum.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Main Menue");
            stage.setScene(new Scene(root, 600, 570));
            stage.show();
            
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        
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
        
    }
    public void backbutton2(ActionEvent event)throws Exception {
       
        {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("userinterface/main.fxml"));
            Stage stage = new Stage();
            stage.setTitle(" ");
            stage.setScene(new Scene(root, 600, 570));
            stage.show();
            
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        
    }
   
    
    public void handleCloseButtonAction(ActionEvent event) {
         ((Node)(event.getSource())).getScene().getWindow().hide();
            
}
    public void doExit(){
        Platform.exit();
    }
    
    
    
    /** Finaller Login mit Datenbank conection FEHLERHAFT **/
    
    
    private String getEmail(){
        String email = "";
        try{
       
        statement = conection.prepareStatement("");
        statement.setString(1,txtusername.getText());
        resultset = statement.executeQuery();
        if(resultset.next())
            email = resultset.getString(1);
        resultset.close();
                }catch(SQLException ex){
                Logger.getLogger(FXMLDocController.class.getName()).log(Level.SEVERE,null,ex);
                }
        return email;
    
    }
    
    private String getPassword(){
        String password = "";
        try{
       
        statement = conection.prepareStatement("");
        statement.setString(1,txtusername.getText());
        resultset = statement.executeQuery();
        if(resultset.next())
            password = resultset.getString(1);
        resultset.close();
                }catch(SQLException ex){
                Logger.getLogger(FXMLDocController.class.getName()).log(Level.SEVERE,null,ex);
                }
        return password;
    
    }
    public void handleLogin(ActionEvent event) throws IOException{
        if(txtusername.getText().equals(getEmail()) && txtpassword.getText().equals(getPassword())){
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("userinterface/main.fxml"));
        stage.setScene(new Scene(root, 600,570));
        stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }
}
    