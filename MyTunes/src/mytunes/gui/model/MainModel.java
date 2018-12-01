/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mytunes.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Song;

 /**
 *
 * @author Acer
 */

public class MainModel {
    
    private ObservableList<Song> songlist;
    
    public MainModel()
    {
        songlist = FXCollections.observableArrayList();
        songlist.add(new Song("In the End", "Linkin Park", "Rock", 210)); // temporary - later we will get songs from database
        songlist.add(new Song("Mamma mia", "ABBA", "Mom", 240));          // temporary - later we will get songs from database
        
    }
    
    public ObservableList<Song> getSongs()
    {
        return songlist;
    }
    
}
