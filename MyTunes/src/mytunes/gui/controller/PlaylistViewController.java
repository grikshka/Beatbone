/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.gui.model.MainModel;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class PlaylistViewController implements Initializable {
    
    private MainModel model;
    private boolean editing;
    private Playlist editingPlaylist;

    @FXML
    private TextField txtName;
    @FXML
    private Button btnSave;
    
    public PlaylistViewController()
    {
        editing = false;
        model = MainModel.createInstance();      
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnSave.setDisable(true);
    }    

    @FXML
    private void keyNameTyped(KeyEvent event) {
        String name = txtName.getText().trim();
        if(!name.isEmpty())
        {
            btnSave.setDisable(false);
        }
        else
        {
            btnSave.setDisable(true);
        }
    }

    @FXML
    private void clickSave(ActionEvent event) {
        String name = txtName.getText().trim();
        if(!editing)
        {
            model.createPlaylist(name);
        }
        else
        {
            model.updatePlaylist(editingPlaylist, name);
        }
        Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickCancel(ActionEvent event) {
        Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        stage.close();
    }
    
    public void setElementsForEditing(Playlist playlist)
    {
        editing = true;
        editingPlaylist = playlist;
        txtName.setText(editingPlaylist.getName());
    }
    
}
