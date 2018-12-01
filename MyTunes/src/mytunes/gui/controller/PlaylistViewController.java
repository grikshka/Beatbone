/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import mytunes.be.Playlist;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class PlaylistViewController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private Button btnSave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void keyNameTyped(KeyEvent event) {
    }

    @FXML
    private void clickSave(ActionEvent event) {
    }

    @FXML
    private void clickCancel(ActionEvent event) {
    }
    
    public void setElementsForEditing(Playlist playlist)
    {
        txtName.setText(playlist.getName());
    }
    
}
