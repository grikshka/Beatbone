/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mytunes.be.Playlist;
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
    private TableColumn<Song, String> colSongTitle;
    @FXML
    private TableColumn<Song, String> colSongArtist;
    @FXML
    private TableColumn<Song, String> colSongGenre;
    @FXML
    private TableColumn<Song, Integer> colSongTime;
    @FXML
    private TableView<Playlist> tblPlaylists;
    @FXML
    private TableColumn<Playlist, String> colPlaylistName;
    @FXML
    private TableColumn<Playlist, Integer> colPlaylistSongs;
    @FXML
    private TableColumn<Playlist, Integer> colPlaylistTime;
    @FXML
    private ListView<Song> lstPlaylistSongs;
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
        model = MainModel.createInstance();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        disableElements();
        loadData();
    }
    
    private void disableElements()
    {
        btnEditSong.setDisable(true);
        btnAddSongToPlaylist.setDisable(true);
        btnDeleteSongFromSongs.setDisable(true);
        btnEditPlaylist.setDisable(true);
        btnDeletePlaylist.setDisable(true);
        btnMoveUpOnPlaylist.setDisable(true);
        btnMoveDownOnPlaylist.setDisable(true);
        btnDeleteSongFromPlaylist.setDisable(true);
    }
    
    private void loadData()
    {
        //load playlists
        colPlaylistName.setCellValueFactory(new PropertyValueFactory("name"));
        colPlaylistSongs.setCellValueFactory(new PropertyValueFactory("numberOfSongs"));
        colPlaylistTime.setCellValueFactory(new PropertyValueFactory("time"));
        tblPlaylists.setItems(model.getPlaylists());
        
        //load songs
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
        if(tblSongs.getSelectionModel().getSelectedItem() != null)
        {
            btnEditSong.setDisable(false);
            btnDeleteSongFromSongs.setDisable(false);
            if(tblPlaylists.getSelectionModel().getSelectedItem() != null)
            {
                btnAddSongToPlaylist.setDisable(false);
            }
        }
    }

    @FXML
    private void clickOnPlaylists(MouseEvent event) {
        if(tblPlaylists.getSelectionModel().getSelectedItem() != null)
        {
            Playlist selectedPlaylist = tblPlaylists.getSelectionModel().getSelectedItem();
            enableButtonsForPlaylists();
            model.setPlaylistSongs(selectedPlaylist);
            lstPlaylistSongs.setItems(model.getPlaylistSongs());
            if(tblSongs.getSelectionModel().getSelectedItem() != null)
            {
                btnAddSongToPlaylist.setDisable(false);
            }
        }
    }

    @FXML
    private void clickOnPlaylistSongs(MouseEvent event) {
        if(lstPlaylistSongs.getSelectionModel().getSelectedItem() != null)
        {
            Playlist selectedPlaylist = tblPlaylists.getSelectionModel().getSelectedItem();
            Song selectedSong = lstPlaylistSongs.getSelectionModel().getSelectedItem();
            btnDeleteSongFromPlaylist.setDisable(false);
            if(model.getIndexOfSongInPlaylist(selectedPlaylist, selectedSong) == 0)
            {
                btnMoveUpOnPlaylist.setDisable(true);
            }
            else
            {
                btnMoveUpOnPlaylist.setDisable(false);
            }
            if(model.getIndexOfSongInPlaylist(selectedPlaylist, selectedSong) == selectedPlaylist.getNumberOfSongs() - 1)
            {
                btnMoveDownOnPlaylist.setDisable(true);  
            }
            else
            {
                btnMoveDownOnPlaylist.setDisable(false);  
            }
        }
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
    private void clickEditPlaylist(ActionEvent event) throws IOException {
        Playlist selectedPlaylist = tblPlaylists.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/PlaylistView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        PlaylistViewController controller = (PlaylistViewController) fxmlLoader.getController();
        controller.setElementsForEditing(selectedPlaylist);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit Playlist");
        stage.setScene(new Scene(root));  
        stage.show();
    }

    @FXML
    private void clickDeletePlaylist(ActionEvent event) {
        Playlist selectedPlaylist = tblPlaylists.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete \"" + selectedPlaylist.getName() + "\" from your playlists?");
        Optional<ButtonType> action = alert.showAndWait();
        if(action.get() == ButtonType.OK)
        {
            model.deletePlaylist(selectedPlaylist);
            tblPlaylists.getSelectionModel().clearSelection();
            model.clearPlaylistSongs();
            btnAddSongToPlaylist.setDisable(true);
            btnDeletePlaylist.setDisable(true);
            btnEditPlaylist.setDisable(true);
            btnMoveUpOnPlaylist.setDisable(true);
            btnMoveDownOnPlaylist.setDisable(true);
            btnDeleteSongFromPlaylist.setDisable(true);
        }
    }

    @FXML
    private void clickMoveUpOnPlaylist(ActionEvent event) {
        Playlist selectedPlaylist = tblPlaylists.getSelectionModel().getSelectedItem();
        Song selectedSong = lstPlaylistSongs.getSelectionModel().getSelectedItem();
        model.moveSongUpOnPlaylist(selectedPlaylist, selectedSong);
        lstPlaylistSongs.getSelectionModel().select(model.getIndexOfSongInPlaylist(selectedPlaylist, selectedSong));
        if(model.getIndexOfSongInPlaylist(selectedPlaylist, selectedSong) == 0)
        {
            btnMoveUpOnPlaylist.setDisable(true);
            btnMoveDownOnPlaylist.setDisable(false);
        }
        else
        {
            btnMoveUpOnPlaylist.setDisable(false);
            btnMoveDownOnPlaylist.setDisable(false);
        }
    }

    @FXML
    private void clickMoveDownOnPlaylist(ActionEvent event) {
        Playlist selectedPlaylist = tblPlaylists.getSelectionModel().getSelectedItem();
        Song selectedSong = lstPlaylistSongs.getSelectionModel().getSelectedItem();
        model.moveSongDownOnPlaylist(selectedPlaylist, selectedSong);
        lstPlaylistSongs.getSelectionModel().select(model.getIndexOfSongInPlaylist(selectedPlaylist, selectedSong));
        if(model.getIndexOfSongInPlaylist(selectedPlaylist, selectedSong) == selectedPlaylist.getNumberOfSongs()-1)
        {
            btnMoveDownOnPlaylist.setDisable(true);
            btnMoveUpOnPlaylist.setDisable(false);
        }
        else
        {
            btnMoveDownOnPlaylist.setDisable(false);
            btnMoveUpOnPlaylist.setDisable(false);            
        }
    }

    @FXML
    private void clickDeleteSongInPlaylist(ActionEvent event) {
        Song selectedSong = lstPlaylistSongs.getSelectionModel().getSelectedItem();
        Playlist selectedPlaylist = tblPlaylists.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete \"" + selectedSong.getTitle() + "\" from \"" + selectedPlaylist.getName() + "\"?");
        Optional<ButtonType> action = alert.showAndWait();
        if(action.get() == ButtonType.OK)
        {
            model.removeSongFromPlaylist(selectedPlaylist, selectedSong);
            tblPlaylists.getSelectionModel().select(model.getIndexOfPlaylist(selectedPlaylist));
        }
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
    private void clickEditSong(ActionEvent event) throws IOException {
        Song selectedSong = tblSongs.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/SongView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        SongViewController controller = (SongViewController) fxmlLoader.getController();
        controller.setElementsForEditing(selectedSong);
        controller.disableElementsForEditing();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit Song");
        stage.setScene(new Scene(root));  
        stage.show();  
    }

    @FXML
    private void clickDeleteSong(ActionEvent event) {
        Song selectedSong = tblSongs.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete \"" + selectedSong.getTitle() + "\" from your songs?");
        Optional<ButtonType> action = alert.showAndWait();
        if(action.get() == ButtonType.OK)
        {
            model.deleteSong(selectedSong);
        }
    }

    @FXML
    private void clickAddSongToPlaylist(ActionEvent event) {
        Song selectedSong = tblSongs.getSelectionModel().getSelectedItem();
        Playlist selectedPlaylist = tblPlaylists.getSelectionModel().getSelectedItem();
        if(!selectedPlaylist.getTracklist().contains(selectedSong))
        {
            model.addSongToPlaylist(selectedPlaylist, selectedSong);
            tblPlaylists.getSelectionModel().select(model.getIndexOfPlaylist(selectedPlaylist));
        }
        else
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Duplicate song");
            alert.setHeaderText(null);
            alert.setContentText("This song is already in your playlist, do you want to add it anyway?");
            Optional<ButtonType> action = alert.showAndWait();
            if(action.get() == ButtonType.OK)
            {
                model.addSongToPlaylist(selectedPlaylist, selectedSong);
                tblPlaylists.getSelectionModel().select(model.getIndexOfPlaylist(selectedPlaylist));
            }
        }
    }
    
    private void enableButtonsForPlaylists() 
    {
        btnEditPlaylist.setDisable(false);
        btnDeletePlaylist.setDisable(false);
    }
    
}
