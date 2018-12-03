/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mytunes.gui.model;

import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.BllManager;
import mytunes.bll.util.MusicSearcher;
import mytunes.bll.util.TimeConverter;
import mytunes.gui.PlayingMode;
import mytunes.bll.IBllFacade;

 /**
 *
 * @author Acer
 */

public class MainModel {
    
    private ObservableList<Song> songlist;
    private ObservableList<Playlist> playlists;
    private ObservableList<Song> playlistSongs;
    private static MainModel instance;
    private Song currentlyPlaying;
    private Playlist currentPlaylist;
    private IBllFacade bllManager;
    
    private MainModel()
    {
        bllManager = new BllManager();
        songlist = FXCollections.observableArrayList(bllManager.getAllSongs());
        playlists = FXCollections.observableArrayList(bllManager.getAllPlaylists());
        playlistSongs = FXCollections.observableArrayList();
        
    }
    
    public static MainModel createInstance()
    {
        if(instance == null)
        {
            instance = new MainModel();
        }
        return instance;
    }
    
    public ObservableList<Song> getSongs()
    {
        return songlist;
    }
    
    public void deleteSong(Song song)
    {
        songlist.remove(song);
    }
    
    public void createSong(String title, String artist, String genre, String path, int time)
    {
        Song song = bllManager.createSong(title, artist, genre, path, time);
        songlist.add(song);
    }
    
    public void updateSong(Song song, String title, String artist, String genre)
    {
        Song updatedSong = bllManager.updateSong(song, title, artist, genre);
        updateListOfSongs(updatedSong);
    }
    
    private void updateListOfSongs(Song song)
    {
        int index = getIndexOfSong(song);
        songlist.set(index, song);
    }
    
    public Song getFirstSong()
    {
        if(songlist.isEmpty())
        {
            return null;
        }
        return songlist.get(0);
    }
    
    public int getIndexOfSong(Song song)
    {
        return songlist.indexOf(song);
    }
    
    public ObservableList<Playlist> getPlaylists()
    {
        return playlists;
    }
    
    public void deletePlaylist(Playlist playlist)
    {
        bllManager.deletePlaylist(playlist);
        playlists.remove(playlist);
    }
    
    public void createPlaylist(String name)
    {
        Playlist playlist = bllManager.createPlaylist(name);
        playlists.add(playlist);
    }
    
    public void updatePlaylist(Playlist playlist, String name)
    {
        Playlist updatedPlaylist = bllManager.updatePlaylist(playlist, name);
        updateListOfPlaylists(playlist);
    }
    
    public Song getFirstSongFromPlaylist(Playlist playlist)
    {
        if(playlist.getTracklist().isEmpty())
        {
            return null;
        }
        return playlist.getTracklist().get(0);
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
    
    public void removeSongFromPlaylist(Playlist playlist, Song song)
    {
        playlist.removeSong(song);
        updateListOfPlaylists(playlist);     
    }
    
    public ObservableList<Song> getPlaylistSongs()
    {
        return playlistSongs;
    }
    
    public void setPlaylistSongs(Playlist playlist)
    {
        playlistSongs.setAll(playlist.getTracklist());
    }
    
    public void clearPlaylistSongs()
    {
        playlistSongs.clear();
    }
    
    public void addSongToPlaylist(Playlist playlist, Song song)
    {
        Playlist updatedPlaylist = bllManager.addSongToPlaylist(playlist, song);       
        updateListOfPlaylists(updatedPlaylist);
    }
    
    public int getIndexOfPlaylist(Playlist playlist)
    {
        return playlists.indexOf(playlist);
    }
    
    private void updateListOfPlaylists(Playlist playlist)
    {
        int index = getIndexOfPlaylist(playlist);
        playlists.set(index, playlist);
        setPlaylistSongs(playlist);
    }

    public void setCurrentlyPlaying(Song playedSong)
    {
        currentlyPlaying = playedSong;
    }
    
    public void setCurrentPlaylist(Playlist playlist)
    {
        currentPlaylist = playlist;
    }
    
    public Song getNextSong(PlayingMode mode)
    {
        if(mode == PlayingMode.PLAYLIST)
        {
            int index = currentPlaylist.getTracklist().indexOf(currentlyPlaying);
            if(index == currentPlaylist.getNumberOfSongs()-1)
            {
                return getFirstSongFromPlaylist(currentPlaylist);
            }
            else
            {
                return currentPlaylist.getTracklist().get(index+1);
            }
        }
        else
        {
            int index = songlist.indexOf(currentlyPlaying);
            if(index == songlist.size()-1)
            {
                return getFirstSong();
            }
            else
            {
                return songlist.get(index+1);
            }
        }
    }
    
    public Song getPreviousSong(PlayingMode mode)
    {
        if(mode == PlayingMode.PLAYLIST)
        {
            int index = currentPlaylist.getTracklist().indexOf(currentlyPlaying);
            if(index == 0)
            {
                return currentlyPlaying;
            }
            else
            {
                return currentPlaylist.getTracklist().get(index-1);
            }
        }
        else
        {
            int index = songlist.indexOf(currentlyPlaying);
            if(index == 0)
            {
                return currentlyPlaying;
            }
            else
            {
                return songlist.get(index-1);
            }
        }
    }
    
    public ObservableList<Song> getFilteredSongs(String filter)
    {
        ObservableList<Song> filteredList = FXCollections.observableArrayList(MusicSearcher.searchSongs(songlist, filter));
        return filteredList;
    }
    
    public ObservableList<Playlist> getFilteredPlaylists(String filter)
    {
        ObservableList<Playlist> filteredList = FXCollections.observableArrayList(MusicSearcher.searchPlaylists(playlists, filter));
        return filteredList;
    }
    
    public String getTimeInString(int timeInSeconds)
    {
        return TimeConverter.convertToString(timeInSeconds);
    }
    
    public int getTimeInInt(String timeInString)
    {
        return TimeConverter.convertToInt(timeInString);
    }
    
}
