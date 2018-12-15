/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mytunes.be.User;
import mytunes.gui.model.UserModel;
import mytunes.gui.util.WarningDisplayer;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class CreateUserViewController implements Initializable {
    
    private UserModel model;
    private WarningDisplayer warningDisplayer;
    
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtRepeatPassword;
    
    public CreateUserViewController()
    {
        model = UserModel.createInstance();
        warningDisplayer = new WarningDisplayer();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clickCreate(ActionEvent event) {
        Stage currentStage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        if(!isEmailCorrect(txtEmail.getText()))
        {
            warningDisplayer.displayError(currentStage, "Cannot create user", "Address email is invalid");
        }       
        else
        {
            List<String> faults = getPasswordFaults(txtPassword.getText());
            if(!faults.isEmpty())
            {
                String errorText = "";
                for(String s : faults)
                {
                    errorText += "- " + s + "\n";
                }
                warningDisplayer.displayError(currentStage, "Cannot create user", errorText);
            }
            else if(!txtPassword.getText().equals(txtRepeatPassword.getText()))
            {
                warningDisplayer.displayError(currentStage, "Cannot create user", "Your passwords are not the same");
            }
            else
            {
                User user = model.createUser(txtEmail.getText(), txtPassword.getText());
                if(user == null)
                {
                    warningDisplayer.displayError(currentStage, "Cannot create user", "This adres e-mail is already taken");
                }
                else
                {
                    Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
                    stage.close();
                }
            }
        }
    }
    
    private boolean isEmailCorrect(String email)
    {
        if(email.length()<5 || !email.contains("@") || !email.contains("."))
        {
            return false;
        }
        return true;
    }
    
    private List<String> getPasswordFaults(String password)
    {
        List<String> faults = new ArrayList();
        if(password.length()<7)
        {
            faults.add("Your password need o have at least 8 characters");
        }
        if(password.equals(password.toLowerCase()) || password.equals(password.toUpperCase()))
        {
            faults.add("Your password need to contain small and big letters");
        }
        if(!password.matches(".*\\d+.*"))
        {
            faults.add("Your password need to contain numbers");
        }
        if(password.matches("[a-zA-Z0-9]*"))
        {
            faults.add("Your password need to contain special characters");
        }
        return faults;

    }
    
    @FXML
    private void clickClose(ActionEvent event) {
        Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        stage.close();
    }
 
}
