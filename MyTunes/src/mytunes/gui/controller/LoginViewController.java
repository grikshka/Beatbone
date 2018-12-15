/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mytunes.be.User;
import mytunes.gui.model.MainModel;
import mytunes.gui.model.UserModel;
import mytunes.gui.util.WarningDisplayer;
import mytunes.gui.util.WindowDecorator;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class LoginViewController implements Initializable {
    
    private WarningDisplayer warningDisplayer;  
    private UserModel model;
    private double xOffset;
    private double yOffset;
    
    @FXML
    private Button btnLogin;
    
    public LoginViewController()
    {
        model = UserModel.createInstance();
        warningDisplayer = new WarningDisplayer();
    }

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        createTextFieldListeners();
    }    
    
    public void createTextFieldListeners()
    {
        txtEmail.setOnKeyPressed(new EventHandler<KeyEvent>()
                {
                    @Override
                    public void handle(KeyEvent key) 
                    {
                        if(key.getCode() == KeyCode.ENTER)
                        {
                            txtPassword.requestFocus();
                        }
                    }
                    
                }
            );
        txtPassword.setOnKeyPressed(new EventHandler<KeyEvent>()
                {
                    @Override
                    public void handle(KeyEvent key) 
                    {
                        if(key.getCode() == KeyCode.ENTER)
                        {
                            btnLogin.fire();
                        }
                    }
                    
                }
            );
    }

    @FXML
    private void clickLogin(ActionEvent event) throws IOException {
        User user = model.getUser(txtEmail.getText(), txtPassword.getText());
        if(user != null)
        {
            Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
            stage.hide();
            MainModel.setUser(user);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/MainView.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(root));  
            stage.centerOnScreen();
            stage.show();
        }
        else
        {
            Stage currentStage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
            warningDisplayer.displayError(currentStage, "Cannot log in", "Invalid email or password");
        }
    }

    @FXML
    private void clickCreateAccount(ActionEvent event) throws IOException {
        Stage currentStage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        WindowDecorator.fadeOutStage(currentStage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/CreateUserView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Sign In");
        stage.setScene(new Scene(root));  
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
        WindowDecorator.fadeInStage(currentStage);
    }
    
    @FXML
    private void clickClose(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void clickMinimalize(ActionEvent event) {
        Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void clickMouseDragged(MouseEvent event) {
        Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }

    @FXML
    private void clickMousePressed(MouseEvent event) {
        Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }
    
}
