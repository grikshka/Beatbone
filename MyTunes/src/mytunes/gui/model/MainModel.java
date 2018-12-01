/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mytunes.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;
import mytunes.be.Song;

 /**
 *
 * @author Acer
 */

public class MainModel {
    
    private ObservableList<Song> songlist;
    private ObservableList<Playlist> playlists;
    private ObservableList<Song> playlistSongs;
    
    public MainModel()
    {
        songlist = FXCollections.observableArrayList();
        Song song1 = new Song("In the End", "Linkin Park", "Rock", 210);    // temporary - later we will get songs from database
        Song song2 = new Song("Mamma mia", "ABBA", "Mom", 240);             // temporary - later we will get songs from database
        songlist.add(song1);                                                // temporary - later we will get songs from database
        songlist.add(song2);                                                // temporary - later we will get songs from database
        
        Playlist pl = new Playlist("Nice playlist");                        // temporary - later we will get playlists from database
        pl.addSong(song1);                                                  // temporary - later we will get playlists from database
        pl.addSong(song2);                                                  // temporary - later we will get playlists from database
        playlists = FXCollections.observableArrayList();                    // temporary - later we will get playlists from database
        playlists.add(pl);
        
        playlistSongs = FXCollections.observableArrayList();
        
    }
    
    public ObservableList<Song> getSongs()
    {
        return songlist;
    }
    
    public void deleteSong(Song song)
    {
        songlist.remove(song);
    }
    
    public void createSong(String title, String artist, String genre, int time)
    {
        Song song = new Song(title,artist,genre,time);
        songlist.add(song);
    }
    
    public ObservableList<Playlist> getPlaylists()
    {
        return playlists;
    }
    
    public void deletePlaylist(Playlist playlist)
    {
        playlists.remove(playlist);
    }
    
    public void createPlaylist(String name)
    {
        Playlist pl = new Playlist(name);
        playlists.add(pl);
    }
    
    public ObservableList<Song> getPlaylistSongs()
    {
        return playlistSongs;
    }
    
    public void setPlaylistSongs(Playlist playlist)
    {
        playlistSongs.setAll(playlist.getTracklist());
    }
    
    public int getIndexOfSongInPlaylist(Playlist playlist, Song song)
    {
        return playlist.getTracklist().indexOf(song);
    }
    
}
