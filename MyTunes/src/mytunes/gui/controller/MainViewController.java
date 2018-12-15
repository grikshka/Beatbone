/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.util.TimeConverter;
import mytunes.gui.PlayingMode;
import mytunes.gui.model.MainModel;
import mytunes.gui.util.WarningDisplayer;
import mytunes.gui.util.WindowDecorator;

/**
 *
 * @author Acer
 */
public class MainViewController implements Initializable {
    
    private MainModel model;
    private MediaPlayer mediaPlayer;
    private boolean songTimeChanged = false;
    private double previousVolume;
    private Timeline stopPlayer;
    private long unixTime = System.currentTimeMillis();
    private WarningDisplayer warningDisplayer;
    private double xOffset;
    private double yOffset;

    @FXML
    private ToggleButton btnPlaySong;
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
    @FXML
    private ToggleButton btnRepeat;
    @FXML
    private Button btnNewPlaylist;
    @FXML
    private ToggleButton btnShuffle;
   
    public MainViewController()
    {
        model = MainModel.createInstance();
        warningDisplayer = new WarningDisplayer();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        disableElements();
        createSliderListeners();
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
        sldTime.setDisable(true);
        btnMute.setDisable(true);
    }
    
    public void createSliderListeners()
    {
        createVolumeSliderListener();
        createTimeSliderListener();
    }
    
    private void createVolumeSliderListener()
    {
        sldVolume.valueProperty().addListener(new ChangeListener()
            {
                @Override
                public void changed(ObservableValue arg0, Object arg1, Object arg2)
                {
                    if(btnMute.isSelected() && getVolume() != 0)
                    {
                        btnMute.setSelected(false);
                    }
                    else if(!btnMute.isSelected() && getVolume() == 0)
                    {
                        previousVolume=0;
                        btnMute.setSelected(true);
                    }
                    if(mediaPlayer != null)
                    {
                        mediaPlayer.setVolume(getVolume());
                    }
                }
            }     
        );
    }
    
    private void createTimeSliderListener()
    {
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
            }
            else
            {
                Stage currentStage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
                warningDisplayer.displayError(currentStage, "Cannot play a song", "Your list of songs is empty");
            }
            
        }
        else if(mediaPlayer.getStatus().equals(Status.PLAYING))
        {
            stopSong();
        }
        else
        {
            resumeSong();
        }
    }
    
    private void stopSong()
    {
        stopPlayer.play();
        btnPlaySong.setText("⏵");
    }
    
    private void resumeSong()
    {
        Timeline resumePlayer = new Timeline(
            new KeyFrame(Duration.seconds(0.30),
                new KeyValue(mediaPlayer.volumeProperty(), getVolume())));
        mediaPlayer.play();
        resumePlayer.play();
        btnPlaySong.setText("||");
    }

    @FXML
    private void clickNextSong(ActionEvent event) {    
        if(isPlayable())
        {
            Song songToPlay = model.getNextSong();
            playSong(songToPlay, model.getCurrentPlayingMode());
        }
    }

    @FXML
    private void clickPreviousSong(ActionEvent event) {
        if(isPlayable())
        {
            Song songToPlay = model.getPreviousSong();
            playSong(songToPlay, model.getCurrentPlayingMode());
        }
    }
    
    @FXML
    private void clickShuffle(ActionEvent event) {
        model.switchShuffling();
    }
    
    @FXML
    private void clickMute(ActionEvent event) {
        if(btnMute.isSelected())
        {
            previousVolume = getVolume();
            setVolume(0);
        }
        else
        {
            if(getVolume() == 0)
            {
                btnMute.setSelected(true);
            }
            setVolume(previousVolume);
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
            }
            else
            {
                enableButtonsForSongs();
                if(tblPlaylists.getSelectionModel().getSelectedItem() != null)
                {
                    btnAddSongToPlaylist.setDisable(false);
                }
            }
        }
    }

    @FXML
    private void clickOnPlaylists(MouseEvent event) {
        disableButtonsForPlaylistSongs();
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
            }
            else
            {                
                btnDeleteSongFromPlaylist.setDisable(false);
                if(selectedPlaylist.getPositionOfSong(selectedSong) == 0)
                {
                    btnMoveUpOnPlaylist.setDisable(true);
                }
                else
                {
                    btnMoveUpOnPlaylist.setDisable(false);
                }
                if(selectedPlaylist.getPositionOfSong(selectedSong) == selectedPlaylist.getNumberOfSongs() - 1)
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
        Stage currentStage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        WindowDecorator.fadeOutStage(currentStage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/PlaylistView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New Playlist");
        stage.setScene(new Scene(root));  
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
        WindowDecorator.fadeInStage(currentStage);
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
        Stage currentStage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        Optional<ButtonType> action = warningDisplayer.displayConfirmation(currentStage, "Confirmation", 
                "Are you sure you want to delete \"" + selectedPlaylist.getName() + "\" from your playlists?");
        if(action.get() == ButtonType.OK)
        {
            model.deletePlaylist(selectedPlaylist);
            tblPlaylists.getSelectionModel().clearSelection();
            model.clearPlaylistSongs();
            btnAddSongToPlaylist.setDisable(true);
            disableButtonsForPlaylists();
            disableButtonsForPlaylistSongs();
        }
    }

    @FXML
    private void clickMoveUpOnPlaylist(ActionEvent event) {
        Playlist selectedPlaylist = tblPlaylists.getSelectionModel().getSelectedItem();
        Song selectedSong = lstPlaylistSongs.getSelectionModel().getSelectedItem();
        model.moveSongUpOnPlaylist(selectedPlaylist, selectedSong);
        lstPlaylistSongs.getSelectionModel().select(selectedPlaylist.getPositionOfSong(selectedSong));
        if(selectedPlaylist.getPositionOfSong(selectedSong) == 0)
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
        lstPlaylistSongs.getSelectionModel().select(selectedPlaylist.getPositionOfSong(selectedSong));
        if(selectedPlaylist.getPositionOfSong(selectedSong) == selectedPlaylist.getNumberOfSongs()-1)
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
        Stage currentStage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        Optional<ButtonType> action = warningDisplayer.displayConfirmation(currentStage, "Confirmation", 
                "Are you sure you want to delete \"" + selectedSong.getTitle() + "\" from \"" + selectedPlaylist.getName() + "\"?");;
        if(action.get() == ButtonType.OK)
        {
            model.deleteSongFromPlaylist(selectedPlaylist, selectedSong);
            tblPlaylists.getSelectionModel().select(selectedPlaylist);
        }
    }

    @FXML
    private void clickNewSong(ActionEvent event) throws IOException {
        Stage currentStage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        WindowDecorator.fadeOutStage(currentStage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/SongView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New Song");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
        WindowDecorator.fadeInStage(currentStage);
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
        Stage currentStage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        Optional<ButtonType> action = warningDisplayer.displayConfirmation(currentStage, "Confirmation", 
                "Are you sure you want to delete \"" + selectedSong.getTitle() + "\" from your songs?");;
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
        if(!selectedPlaylist.isSongOnTracklist(selectedSong))
        {
            model.addSongToPlaylist(selectedPlaylist, selectedSong);
            tblPlaylists.getSelectionModel().select(selectedPlaylist);
        }
        else
        {
            Stage currentStage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
            warningDisplayer.displayError(currentStage, "Duplicate song", "This song is already in your playlist");
        }
    }
    
    private void playSong(Song songToPlay, PlayingMode mode)
    {
        if(mediaPlayer != null && mediaPlayer.getStatus().equals(Status.PLAYING))
        {
            mediaPlayer.stop();
        }
        try
        {
            File fileSong = new File(songToPlay.getPath());
            Media song = new Media(fileSong.toURI().toString());
            if(mediaPlayer == null)
            {
                enablePlayingButtons();
            }
            mediaPlayer = new MediaPlayer(song);
            setMediaPlayer(songToPlay, mode);
            mediaPlayer.play();
        }
        catch(MediaException e)
        {
            Stage currentStage = (Stage) btnPlaySong.getScene().getWindow();
            warningDisplayer.displayError(currentStage, "Cannot play a song", "Could not find path to song \"" + songToPlay.getTitle() + "\"");
        }
    }    
    
    public void setMediaPlayer(Song songToPlay, PlayingMode mode)
    {
        setMediaPlayerSettings(songToPlay);  
        model.setCurrentlyPlaying(songToPlay, mode);
        mediaPlayer.setVolume(getVolume());
        lblSongCurrentTime.setText("00:00");
        lblSongEndTime.setText(songToPlay.getTimeInString());
        sldTime.setMax(songToPlay.getTime());
        labelCurrentSong.setText("Now playing: " + songToPlay.getTitle());
        btnPlaySong.setText("||");
        selectPlayedSong(songToPlay, mode);
    }
    
    
    private void setMediaPlayerSettings(Song songToPlay)
    {
        setAutoplay();
        setTimeListener();
        setFadingForStopping();
    }
    
    private void setAutoplay()
    {
        mediaPlayer.setOnEndOfMedia(new Runnable()
            {
                @Override
                public void run()
                {
                    if(btnRepeat.isSelected())
                    {
                        mediaPlayer.seek(Duration.ZERO);
                    }
                    else
                    {
                        btnNextSong.fire();
                    }
                }
            }      
        );
    }
    
    private void setTimeListener()
    {
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
    
    private void setFadingForStopping()
    {
        stopPlayer = new Timeline(
            new KeyFrame(Duration.seconds(0.30),
                new KeyValue(mediaPlayer.volumeProperty(), 0)));
        stopPlayer.setOnFinished(new EventHandler()
            {
                @Override
                public void handle(Event event)
                {
                    mediaPlayer.pause();
                }
            }       
        );
    }
    
    private void selectPlayedSong(Song playedSong, PlayingMode mode)
    {
        if(mode == PlayingMode.PLAYLIST)
        {

        }
        else
        {
            tblSongs.getSelectionModel().select(playedSong);
        }
    }
    
    private boolean isPlayable()
    {
        if(System.currentTimeMillis() - unixTime < 200)
        {
            return false;
        }
        unixTime =  System.currentTimeMillis();
        return true;
    }
    
    private void enableButtonsForSongs() {
        btnEditSong.setDisable(false);
        btnDeleteSongFromSongs.setDisable(false);
    }
    
    private void enableButtonsForPlaylists() 
    {
        btnEditPlaylist.setDisable(false);
        btnDeletePlaylist.setDisable(false);
    }
    
    private void enablePlayingButtons() 
    {
        btnPreviousSong.setDisable(false);
        btnNextSong.setDisable(false);
        sldTime.setDisable(false);
        btnMute.setDisable(false);
    }
    
    private void disableButtonsForPlaylists() 
    {
        btnEditPlaylist.setDisable(true);
        btnDeletePlaylist.setDisable(true);
    }
    
    private void disableButtonsForPlaylistSongs()
    {
        btnDeleteSongFromPlaylist.setDisable(true);
        btnMoveUpOnPlaylist.setDisable(true);
        btnMoveDownOnPlaylist.setDisable(true);
    }

    public void setVolume(double volume)
    {
        sldVolume.setValue(volume*10);
    }
    public double getVolume()
    {
        return sldVolume.getValue()/10;
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
