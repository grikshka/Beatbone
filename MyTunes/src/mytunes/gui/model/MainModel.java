/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mytunes.gui.model;

import java.util.Collections;
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
        Song song3 = new Song("Gucci Gang", "Lil Pump", "Rap", 420);            // temporary - later we will get songs from database
        Song song4 = new Song("Will He", "Joji", "R&B", 170);            // temporary - later we will get songs from database
        Song song5 = new Song("Still D.R.E.", "Snoop Dogg", "Rap", 870);            // temporary - later we will get songs from database
        Song song6 = new Song("Awful Things", "Lil Peep", "Emo-Rap", 430);            // temporary - later we will get songs from database
        Song song7 = new Song("Chasing Faith", "Underachievers", "Rap", 150);            // temporary - later we will get songs from database
        Song song8 = new Song("Hotline Bling", "Drake", "Rap", 340);            // temporary - later we will get songs from database
        songlist.add(song3);                                                // temporary - later we will get songs from database
        songlist.add(song4);                                                // temporary - later we will get songs from database
        songlist.add(song5);                                                // temporary - later we will get songs from database
        songlist.add(song6);                                                // temporary - later we will get songs from database
        songlist.add(song7);                                                // temporary - later we will get songs from database
        songlist.add(song8);                                                // temporary - later we will get songs from database
        
        Playlist pl = new Playlist("Nice playlist");                        // temporary - later we will get playlists from database
        pl.addSong(song1);                                                  // temporary - later we will get playlists from database
        pl.addSong(song2);                                                  // temporary - later we will get playlists from database  
        pl.addSong(song3);                                                  // temporary - later we will get playlists from database  
        pl.addSong(song4);                                                  // temporary - later we will get playlists from database  
        pl.addSong(song5);                                                  // temporary - later we will get playlists from database 
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
            
    public int getIndexOfSongInPlaylist(Playlist playlist, Song song)
    {
        return playlist.getTracklist().indexOf(song);
    }
    
    public void moveSongUpOnPlaylist(Playlist playlist, Song song)
    {
        int id = playlistSongs.indexOf(song);
        if(id!=0)
        {
            Collections.swap(playlistSongs, id, id-1);
            Collections.swap(playlist.getTracklist(), id, id-1);
        }
    }
    
    public void moveSongDownOnPlaylist(Playlist playlist, Song song)
    {
        int id = playlistSongs.indexOf(song);
        if(id!= playlist.getNumberOfSongs()-1)
        {
            Collections.swap(playlistSongs, id, id+1);
            Collections.swap(playlist.getTracklist(), id, id+1);
        }
    }
    
    public ObservableList<Song> getPlaylistSongs()
    {
        return playlistSongs;
    }
    
    public void setPlaylistSongs(Playlist playlist)
    {
        playlistSongs.setAll(playlist.getTracklist());
    }
    
    public void addSongToPlaylist(Playlist playlist, Song song)
    {
        if(!playlist.getTracklist().contains(song))
        {
            playlist.addSong(song);
            playlists.remove(playlist);
            playlists.add(playlist);
            setPlaylistSongs(playlist);
        }
    }
    
    public int getIndexOfPlaylist(Playlist playlist)
    {
        return playlists.indexOf(playlist);
    }

    
}
