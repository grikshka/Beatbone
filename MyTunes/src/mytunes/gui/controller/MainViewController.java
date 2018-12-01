/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mytunes.be.Song;
import mytunes.gui.model.MainModel;

/**
 *
 * @author Acer
 */
public class MainViewController implements Initializable {
    
    MainModel model;

    @FXML
    private Button btnPlaySong;
    @FXML
    private Button btnNextSong;
    @FXML
    private Button btnPreviousSong;
    @FXML
    private TableView<Song> tblSongs;
    @FXML
    private TableColumn<?, ?> colSongTitle;
    @FXML
    private TableColumn<?, ?> colSongArtist;
    @FXML
    private TableColumn<?, ?> colSongGenre;
    @FXML
    private TableColumn<?, ?> colSongTime;
    @FXML
    private TableView<?> tblPlaylists;
    @FXML
    private TableColumn<?, ?> colPlaylistName;
    @FXML
    private TableColumn<?, ?> colPlaylistSongs;
    @FXML
    private TableColumn<?, ?> colPlaylistTime;
    @FXML
    private ListView<?> lstPlaylistSongs;
    @FXML
    private Button btnEditPlaylist;
    @FXML
    private Button btnDeletePlaylist;
    @FXML
    private Button btnMoveUpOnPlaylist;
    @FXML
    private Button btnMoveDownOnPlaylist;
    @FXML
    private Button btnDeleteSongFromPlaylist;
    @FXML
    private Button btnEditSong;
    @FXML
    private Button btnDeleteSongFromSongs;
    @FXML
    private Button btnAddSongToPlaylist;
    @FXML
    private Label labelCurrentSong;
   
    public MainViewController()
    {
        model = new MainModel();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }
    
    private void loadData()
    {
        colSongTitle.setCellValueFactory(new PropertyValueFactory("title"));
        colSongArtist.setCellValueFactory(new PropertyValueFactory("artist"));
        colSongGenre.setCellValueFactory(new PropertyValueFactory("genre"));
        colSongTime.setCellValueFactory(new PropertyValueFactory("time"));
        tblSongs.setItems(model.getSongs());
    }

    @FXML
    private void inputSearch(KeyEvent event) {
    }

    @FXML
    private void clickPlay(ActionEvent event) {
    }

    @FXML
    private void clickNextSong(ActionEvent event) {
    }

    @FXML
    private void clickPreviousSong(ActionEvent event) {
    }

    @FXML
    private void clickOnSongs(MouseEvent event) {
    }

    @FXML
    private void clickOnPlaylists(MouseEvent event) {
    }

    @FXML
    private void clickOnPlaylistSongs(MouseEvent event) {
    }

    @FXML
    private void clickNewPlaylist(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/PlaylistView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New Playlist");
        stage.setScene(new Scene(root));  
        stage.show();
    }

    @FXML
    private void clickEditPlaylist(ActionEvent event) {
    }

    @FXML
    private void clickDeletePlaylist(ActionEvent event) {
    }

    @FXML
    private void clickMoveUpOnPlaylist(ActionEvent event) {
    }

    @FXML
    private void clickMoveDownOnPlaylist(ActionEvent event) {
    }

    @FXML
    private void clickDeleteSongInPlaylist(ActionEvent event) {
    }

    @FXML
    private void clickNewSong(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/SongView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New Song");
        stage.setScene(new Scene(root));  
        stage.show();
    }

    @FXML
    private void clickEditSong(ActionEvent event) {
    }

    @FXML
    private void clickDeleteSong(ActionEvent event) {
    }

    @FXML
    private void clickAddSongToPlaylist(ActionEvent event) {
    }
    
}
