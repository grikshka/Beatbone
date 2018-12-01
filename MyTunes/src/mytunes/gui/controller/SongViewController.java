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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import mytunes.be.Song;
import mytunes.gui.model.MainModel;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class SongViewController implements Initializable {
    
    private boolean editing;
    private MainModel model;

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtFile;
    @FXML
    private ComboBox<String> cmbGenre;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnChoosePath;
    
    public SongViewController()
    {
        editing = false;
        model = MainModel.createInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        disableElements();
        cmbGenre.getItems().addAll("Hip Hop", "Rap", "Blues", "Rock");  // temporary - later we will get genres from database
    }    

    @FXML
    private void keyTitleTyped(KeyEvent event) {
        checkInputs();
    }

    @FXML
    private void keyArtistTyped(KeyEvent event) {
        checkInputs();
    }

    @FXML
    private void clickGenrePicked(ActionEvent event) {
        checkInputs();
    }

    @FXML
    private void clickAddNewGenre(ActionEvent event) {
    }

    @FXML
    private void clickSave(ActionEvent event) {
    }

    @FXML
    private void clickChooseFilePath(ActionEvent event) {
    }

    @FXML
    private void clickCancel(ActionEvent event) {
    }
    
    private void disableElements()
    {
        btnSave.setDisable(true);
        txtTime.setDisable(true);
        txtFile.setDisable(true);
    }
    
    public void setElementsForEditing(Song song)
    {
        editing = true;
        btnChoosePath.setDisable(true);
        txtTime.setText(Integer.toString(song.getTime()));
    }
    
    private void checkInputs()
    {
        if(!(txtArtist.getText().isEmpty()) && !(txtTitle.getText().isEmpty()) && cmbGenre.getValue() != null)
        {
            btnSave.setDisable(false);
        }
        else
        {
            btnSave.setDisable(true);
        }
    }
    
}
