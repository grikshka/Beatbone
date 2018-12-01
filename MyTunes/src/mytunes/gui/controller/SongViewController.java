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

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class SongViewController implements Initializable {

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtFile;
    @FXML
    private ComboBox<?> cmbGenre;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnChoosePath;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void keyTitleTyped(KeyEvent event) {
    }

    @FXML
    private void keyArtistTyped(KeyEvent event) {
    }

    @FXML
    private void clickGenrePicked(ActionEvent event) {
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
    
}
