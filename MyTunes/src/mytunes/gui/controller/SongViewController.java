/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.File;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mytunes.be.Song;
import mytunes.bll.util.TimeConverter;
import mytunes.gui.model.MainModel;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class SongViewController implements Initializable {
    
    private boolean editing;
    private Song editingSong;
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
        if(!editing)
        {
            model.createSong(txtTitle.getText(), txtArtist.getText(), cmbGenre.getValue(), 
                    txtFile.getText(), model.getTimeInInt(txtTime.getText()));
        }
        else
        {
            model.updateSong(editingSong, txtTitle.getText(), txtArtist.getText(), cmbGenre.getValue());
        }
        Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clickChooseFilePath(ActionEvent event)
    {
        FileChooser songChooser = createSongChooser();
        File selectedFile = songChooser.showOpenDialog(null);
        if(selectedFile != null)
        {
            txtFile.setText(selectedFile.getPath());
            setTimeField(selectedFile);
            checkInputs();
        }
    }
    
    private FileChooser createSongChooser()
    {
        FileChooser songChooser = new FileChooser();
        songChooser.setTitle("Select song");
        FileChooser.ExtensionFilter generalFilter = new FileChooser.ExtensionFilter("All Music Files", "*.mp3", "*.wav", "*.mp4", "*.m4a", "*.m4v");
        FileChooser.ExtensionFilter mp3Filter = new FileChooser.ExtensionFilter("MP3 (*.mp3)", "*.mp3");
        FileChooser.ExtensionFilter mp4Filter = new FileChooser.ExtensionFilter("MP4 (*.mp4, *.m4a, *.m4v)","*.mp4", "*.m4a", "*.m4v");
        FileChooser.ExtensionFilter wavFilter = new FileChooser.ExtensionFilter("WAV (*.wav)","*.wav");
        songChooser.getExtensionFilters().add(generalFilter);
        songChooser.getExtensionFilters().add(mp3Filter);
        songChooser.getExtensionFilters().add(mp4Filter);
        songChooser.getExtensionFilters().add(wavFilter);       
        return songChooser;
    }

    @FXML
    private void clickCancel(ActionEvent event) {
        Stage stage = (Stage)((Node)((EventObject) event).getSource()).getScene().getWindow();
        stage.close();
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
        editingSong = song;
        btnChoosePath.setDisable(true);
        txtTitle.setText(editingSong.getTitle());
        txtArtist.setText(editingSong.getArtist());
        cmbGenre.setValue(editingSong.getGenre());
        txtFile.setText(editingSong.getPath());
        txtArtist.setFocusTraversable(false);
        txtTitle.setFocusTraversable(false);
        txtTime.setText(Integer.toString(editingSong.getTime()));
    }
    
    private void checkInputs()
    {
        if(!(txtArtist.getText().isEmpty()) && !(txtTitle.getText().isEmpty()) && cmbGenre.getValue() != null && !(txtFile.getText().isEmpty()))
        {
            btnSave.setDisable(false);
        }
        else
        {
            btnSave.setDisable(true);
        }
    }
    
    private void setTimeField(File selectedFile) 
    {
        Media mediaFile = new Media(selectedFile.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(mediaFile);
        mediaPlayer.setOnReady(new Runnable()
            {
                @Override
                public void run()
                {
                    int timeOfSong = (int) mediaFile.getDuration().toSeconds();
                    txtTime.setText(model.getTimeInString(timeOfSong));
                }
            }      
        );
    }
    
}
