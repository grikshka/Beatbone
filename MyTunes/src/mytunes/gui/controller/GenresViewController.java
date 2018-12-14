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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mytunes.gui.model.GenresViewModel;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class GenresViewController implements Initializable {
    
    private GenresViewModel genresModel;

    @FXML
    private ListView<String> lstGenres;
    @FXML
    private Button btnSave;
    
    public GenresViewController()
    {
        genresModel = GenresViewModel.createInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genresModel.clearSelectedGenre();
        lstGenres.setItems(genresModel.getAllGenres());
        btnSave.setDisable(true);
    }   
    
    
    @FXML
    private void clickSaveGenre(ActionEvent event) {
        genresModel.setSelectedGenre(lstGenres.getSelectionModel().getSelectedItem());
        Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickOnGenres(MouseEvent event) {
        if(lstGenres.getSelectionModel().getSelectedItem() != null)
        {
            btnSave.setDisable(false);
        }
    }

    @FXML
    private void clickCancel(ActionEvent event) {
        Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void clickClose(ActionEvent event) {
        Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        stage.close();
    }

    
}
