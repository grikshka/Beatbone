/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.util.TimeConverter;
import mytunes.gui.PlayingMode;
import mytunes.gui.model.MainModel;

/**
 *
 * @author Acer
 */
public class MainViewController implements Initializable {
    
    private MainModel model;
    private MediaPlayer mediaPlayer;
    private boolean songTimeChanged = false;
    private double previousVolume;

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
    @FXML
    private Slider sldVolume;
    @FXML
    private TextField txtSearchSongs;
    @FXML
    private TextField txtSearchPlaylists;
    @FXML
    private Slider sldTime;
    @FXML
    private Label lblSongEndTime;
    @FXML
    private Label lblSongCurrentTime;
    @FXML
    private ToggleButton btnMute;
   
    public MainViewController()
    {
        model = MainModel.createInstance();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        disableElements();
        createSliderListener();
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
        btnPreviousSong.setDisable(true);
        btnNextSong.setDisable(true);
    }
    
    public void createSliderListener()
    {
        sldVolume.valueProperty().addListener(new ChangeListener()
            {
                @Override
                public void changed(ObservableValue arg0, Object arg1, Object arg2)
                {
                    if(mediaPlayer != null)
                    {
                        if(btnMute.isSelected() && sldVolume.getValue() != 0)
                        {
                            btnMute.setSelected(false);
                        }
                        else if(!btnMute.isSelected() && sldVolume.getValue() == 0)
                        {
                            previousVolume=0;
                            btnMute.setSelected(true);
                        }
                        mediaPlayer.setVolume(sldVolume.getValue());
                    }
                }
            }     
        );
        sldTime.valueProperty().addListener(new ChangeListener()
            {
                @Override
                public void changed(ObservableValue arg0, Object arg1, Object arg2)
                {
                    lblSongCurrentTime.setText(TimeConverter.convertToString((int)sldTime.getValue()));
                }
            }     
        );
    }
    
    private void loadData()
    {
        //load playlists
        colPlaylistName.setCellValueFactory(new PropertyValueFactory("name"));
        colPlaylistSongs.setCellValueFactory(new PropertyValueFactory("numberOfSongs"));
        colPlaylistTime.setCellValueFactory(new PropertyValueFactory("timeInString"));
        tblPlaylists.setItems(model.getPlaylists());
        
        //load songs
        colSongTitle.setCellValueFactory(new PropertyValueFactory("title"));
        colSongArtist.setCellValueFactory(new PropertyValueFactory("artist"));
        colSongGenre.setCellValueFactory(new PropertyValueFactory("genre"));
        colSongTime.setCellValueFactory(new PropertyValueFactory("timeInString"));
        tblSongs.setItems(model.getSongs());
    }

    @FXML
    private void inputSearchSongs(KeyEvent event) {
        String filter = txtSearchSongs.getText().trim();
        if(filter.isEmpty())
        {
            tblSongs.setItems(model.getSongs());
        }
        else
        {
            tblSongs.setItems(model.getFilteredSongs(filter));
        }
    }
    
    @FXML
    private void inputSearchPlaylists(KeyEvent event) {
        String filter = txtSearchPlaylists.getText().trim();
        if(filter.isEmpty())
        {
            tblPlaylists.setItems(model.getPlaylists());
        }
        else
        {
            tblPlaylists.setItems(model.getFilteredPlaylists(filter));
        }
    }
    

    @FXML
    private void clickPlay(ActionEvent event) {
        if(mediaPlayer == null)
        {
            Song songToPlay = model.getFirstSong();
            if(songToPlay != null)
            {
                playSong(songToPlay, PlayingMode.SONG_LIST);
                labelCurrentSong.setText("Now playing: " + songToPlay.getTitle());
                btnPlaySong.setText("||");
            }
            else
            {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Cannot play a song");
                alert.setHeaderText(null);
                alert.setContentText("Your list of songs is empty");
                Optional<ButtonType> action = alert.showAndWait();
            }
            
        }
        else if(mediaPlayer.getStatus().equals(Status.PLAYING))
        {
            mediaPlayer.pause();
            btnPlaySong.setText("⏵");
        }
        else
        {
            mediaPlayer.play();
            btnPlaySong.setText("||");
        }
    }

    @FXML
    private void clickNextSong(ActionEvent event) {
        Song songToPlay = model.getNextSong();
        playSong(songToPlay, model.getCurrentPlayingMode());
        labelCurrentSong.setText("Now playing: " + songToPlay.getTitle());
        btnPlaySong.setText("||");
    }

    @FXML
    private void clickPreviousSong(ActionEvent event) {
        Song songToPlay = model.getPreviousSong();
        playSong(songToPlay, model.getCurrentPlayingMode());
        labelCurrentSong.setText("Now playing: " + songToPlay.getTitle());
        btnPlaySong.setText("||");
    }
    
    @FXML
    private void clickShuffle(ActionEvent event) {
        model.switchShuffling();
    }
    
    @FXML
    private void clickMute(ActionEvent event) {
        if(btnMute.isSelected())
        {
            previousVolume = sldVolume.getValue();
            sldVolume.setValue(0);
        }
        else
        {
            if(sldVolume.getValue() == 0)
            {
                btnMute.setSelected(true);
            }
            sldVolume.setValue(previousVolume);
        }
    }

    
    @FXML
    private void dropTimeSlider(MouseEvent event) {
        mediaPlayer.seek(Duration.seconds(sldTime.getValue()));
        songTimeChanged=true;
    }

    
    @FXML
    private void clickOnSongs(MouseEvent event) {
        if(tblSongs.getSelectionModel().getSelectedItem() != null)
        {
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2)
            {
                Song selectedSong = tblSongs.getSelectionModel().getSelectedItem();
                playSong(selectedSong, PlayingMode.SONG_LIST);
                labelCurrentSong.setText("Now playing: " + selectedSong.getTitle());
                btnPlaySong.setText("||");
            }
            else
            {
                btnEditSong.setDisable(false);
                btnDeleteSongFromSongs.setDisable(false);
                if(tblPlaylists.getSelectionModel().getSelectedItem() != null)
                {
                    btnAddSongToPlaylist.setDisable(false);
                }
            }
        }
    }

    @FXML
    private void clickOnPlaylists(MouseEvent event) {
        if(tblPlaylists.getSelectionModel().getSelectedItem() != null)
        {
            Playlist selectedPlaylist = tblPlaylists.getSelectionModel().getSelectedItem();
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2)
            {
                Song songToPlay = model.getFirstSongFromPlaylist(selectedPlaylist);
                if(songToPlay != null)
                {
                    playSong(songToPlay, PlayingMode.PLAYLIST);
                    model.setCurrentPlaylist(selectedPlaylist);
                    labelCurrentSong.setText("Now playing: " + songToPlay.getTitle());
                    btnPlaySong.setText("||");
                }
            }
            else
            {
                enableButtonsForPlaylists();
                lstPlaylistSongs.setItems(model.getPlaylistSongs(selectedPlaylist));
                if(tblSongs.getSelectionModel().getSelectedItem() != null)
                {
                    btnAddSongToPlaylist.setDisable(false);
                }
            }
        }
    }

    @FXML
    private void clickOnPlaylistSongs(MouseEvent event) {
        if(lstPlaylistSongs.getSelectionModel().getSelectedItem() != null)
        {
            Playlist selectedPlaylist = tblPlaylists.getSelectionModel().getSelectedItem();
            Song selectedSong = lstPlaylistSongs.getSelectionModel().getSelectedItem();
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2)
            {              
                playSong(selectedSong, PlayingMode.PLAYLIST);
                model.setCurrentPlaylist(selectedPlaylist);
                labelCurrentSong.setText("Now playing: " + selectedSong.getTitle());
                btnPlaySong.setText("||");
            }
            else
            {                
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
            model.deleteSongFromPlaylist(selectedPlaylist, selectedSong);
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
            tblSongs.getSelectionModel().clearSelection();
            btnAddSongToPlaylist.setDisable(true);
            btnEditSong.setDisable(true);
            btnDeleteSongFromSongs.setDisable(true);
        }
    }

    @FXML
    private void clickAddSongToPlaylist(ActionEvent event) {
        Song selectedSong = tblSongs.getSelectionModel().getSelectedItem();
        Playlist selectedPlaylist = tblPlaylists.getSelectionModel().getSelectedItem();
        if(!model.checkIfPlaylistContainsSong(selectedPlaylist,selectedSong))
        {
            model.addSongToPlaylist(selectedPlaylist, selectedSong);
            tblPlaylists.getSelectionModel().select(model.getIndexOfPlaylist(selectedPlaylist));
        }
        else
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Duplicate song");
            alert.setHeaderText(null);
            alert.setContentText("This song is already in your playlist");
            Optional<ButtonType> action = alert.showAndWait();
        }
    }
    
    private void playSong(Song songToPlay, PlayingMode mode)
    {
        if(mediaPlayer == null)
        {
            enableChangeSongButtons();
        }
        else if(mediaPlayer.getStatus().equals(Status.PLAYING))
        {
            mediaPlayer.stop();
        }
        File fileSong = new File(songToPlay.getPath());
        Media song = new Media(fileSong.toURI().toString());
        mediaPlayer = new MediaPlayer(song);
        setMediaPlayer(songToPlay);  
        model.setCurrentlyPlaying(songToPlay, mode);
        mediaPlayer.play();
    }    
    
    private void setMediaPlayer(Song songToPlay)
    {
        mediaPlayer.setVolume(sldVolume.getValue());
        lblSongCurrentTime.setText("00:00");
        lblSongEndTime.setText(songToPlay.getTimeInString());
        sldTime.setMax(songToPlay.getTime());
        mediaPlayer.setOnEndOfMedia(new Runnable()
            {
                @Override
                public void run()
                {
                    btnNextSong.fire();
                }
            }      
        );
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>()
            {
                @Override
                public void changed(ObservableValue arg0, Duration arg1, Duration arg2)
                {
                    if(!sldTime.isPressed() && !songTimeChanged)
                    {
                        sldTime.setValue(arg2.toSeconds());
                    }
                    else if(songTimeChanged)
                        songTimeChanged=false;
                }
            }
        );
    }
    
    private void enableButtonsForPlaylists() 
    {
        btnEditPlaylist.setDisable(false);
        btnDeletePlaylist.setDisable(false);
    }
    
    private void enableChangeSongButtons() 
    {
        btnPreviousSong.setDisable(false);
        btnNextSong.setDisable(false);
    }
    
}
