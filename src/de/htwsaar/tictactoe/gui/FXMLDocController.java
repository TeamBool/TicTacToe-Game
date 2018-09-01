package userinterface;

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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;



public class FXMLDocController {
    
    @FXML
    private Label lblstatus;
   
   /** Buttons und Logik fÃ¼r die Gui Bedienung
     * @param event **/
    
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
    
    
  
}