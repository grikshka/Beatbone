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
import mytunes.bll.util.SongChooser;

 /**
 *
 * @author Acer
 */

public class MainModel {
    
    private ObservableList<Song> songlist;
    private ObservableList<Playlist> playlists;
    private ObservableList<Song> playlistSongs;
    private static MainModel instance;
    private PlayingMode mode;
    private Song currentlyPlaying;
    private Playlist currentPlaylist;
    private boolean shuffle;
    private IBllFacade bllManager;
       
    private MainModel()
    {
        bllManager = new BllManager();
        songlist = FXCollections.observableArrayList(bllManager.getAllSongs());
        playlists = FXCollections.observableArrayList(bllManager.getAllPlaylists());
        playlistSongs = FXCollections.observableArrayList();
        shuffle = false;
        
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
        bllManager.deleteSong(song);
        deleteSongFromAllPlaylists(song);
        songlist.remove(song);
    }
    
    public void createSong(String title, String artist, String genre, String path, int time)
    {
        Song song = bllManager.createSong(title, artist, genre, path, time);
        songlist.add(song);
    }
    
    public void updateSong(Song song, String title, String artist, String genre)
    {
        updateSongOnAllPlaylists(song, title, artist, genre);
        Song updatedSong = bllManager.updateSong(song, title, artist, genre);
        updateListOfSongs(updatedSong);
    }
    
    private void updateListOfSongs(Song song)
    {
        int index = songlist.indexOf(song);
        songlist.set(index, song);
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

    public void moveSongUpOnPlaylist(Playlist playlist, Song song)
    {
        int id = playlistSongs.indexOf(song);
        if(id!=0)
        {
            Song secondSong = playlistSongs.get(id-1);
            bllManager.switchSongsPlacesOnPlaylist(playlist, song, secondSong);
            Collections.swap(playlistSongs, id, id-1);
            Collections.swap(playlist.getTracklist(), id, id-1);
        }
    }
    
    public void moveSongDownOnPlaylist(Playlist playlist, Song song)
    {
        int id = playlistSongs.indexOf(song);
        if(id!= playlist.getNumberOfSongs()-1)
        {
            Song secondSong = playlistSongs.get(id+1);
            bllManager.switchSongsPlacesOnPlaylist(playlist, song, secondSong);
            Collections.swap(playlistSongs, id, id+1);
            Collections.swap(playlist.getTracklist(), id, id+1);
        }
    }
    
    public void deleteSongFromPlaylist(Playlist playlist, Song song)
    {
        bllManager.deleteSongFromPlaylist(playlist, song);
        playlist.removeSong(song);
        updateListOfPlaylists(playlist);     
    }
    
    public ObservableList<Song> getPlaylistSongs(Playlist playlist)
    {
        playlistSongs.setAll(playlist.getTracklist());
        return playlistSongs;
    }
    
    private void setPlaylistSongs(Playlist playlist)
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
    
    private void deleteSongFromAllPlaylists(Song song)
    {
        for(int i = 0; i < playlists.size(); i++)
        {
            for(int j = 0; j < playlists.get(i).getTracklist().size(); j++)
            {
                if(song.getId() == playlists.get(i).getTracklist().get(j).getId())
                {
                    playlists.get(i).removeSong(playlists.get(i).getTracklist().get(j));
                    updateListOfPlaylists(playlists.get(i));
                }
            }
        }
    }
    
    private void updateSongOnAllPlaylists(Song song, String title, String artist, String genre)
    {
        for(int i = 0; i < playlists.size(); i++)
        {
            for(int j = 0; j < playlists.get(i).getTracklist().size(); j++)
            {
                if(song.getId() == playlists.get(i).getTracklist().get(j).getId())
                {
                    Song songToUpdate = playlists.get(i).getTracklist().get(j);
                    songToUpdate.setTitle(title);
                    songToUpdate.setArtist(artist);
                    songToUpdate.setGenre(genre);
                    updateListOfPlaylists(playlists.get(i));
                }
            }
        }
    }
    
    private int getIndexOfPlaylist(Playlist playlist)
    {
        return playlists.indexOf(playlist);
    }
    
    private void updateListOfPlaylists(Playlist playlist)
    {
        int index = getIndexOfPlaylist(playlist);
        playlists.set(index, playlist);
        setPlaylistSongs(playlist);
    }

    public void setCurrentlyPlaying(Song playedSong, PlayingMode mode)
    {
        if(mode!=this.mode)
        {
            SongChooser.clearPreviousRandomSongs();
        }
        this.mode = mode;
        currentlyPlaying = playedSong;
    }
    
    public void setCurrentPlaylist(Playlist playlist)
    {
        currentPlaylist = playlist;
    }
    
    public void switchShuffling()
    {
        SongChooser.clearPreviousRandomSongs();
        shuffle = !shuffle;
    }
    
    public Song getFirstSong()
    {
        if(shuffle)
        {
            return SongChooser.getRandomSong(songlist);
        }
        else
        {
            return SongChooser.getFirstSong(songlist);
        }
    }
    
    public Song getFirstSongFromPlaylist(Playlist playlist)
    {
        if(shuffle)
        {
            return SongChooser.getRandomSong(playlist.getTracklist());
        }
        else
        {
            return SongChooser.getFirstSong(playlist.getTracklist());
        }
    }
        
    public Song getNextSong()
    {
        if(shuffle)
        {
            if(mode == PlayingMode.PLAYLIST)
            {
                return SongChooser.getNextRandomSong(currentPlaylist.getTracklist(), currentlyPlaying);
            }
            else
            {
                return SongChooser.getNextRandomSong(songlist, currentlyPlaying);
            }
        }
        else
        {
            if(mode == PlayingMode.PLAYLIST)
            {
                return SongChooser.getNextSong(currentPlaylist.getTracklist(), currentlyPlaying);
            }
            else
            {
                return SongChooser.getNextSong(songlist, currentlyPlaying);
            }
        }
    }
    
    public PlayingMode getCurrentPlayingMode()
    {
        return mode;
    }
    
    public Song getPreviousSong()
    {
        if(shuffle)
        {
            return SongChooser.getPreviousRandomSong(currentlyPlaying);
        }
        else
        {
            if(mode == PlayingMode.PLAYLIST)
            {
                return SongChooser.getPreviousSong(currentPlaylist.getTracklist(), currentlyPlaying);
            }
            else
            {
                return SongChooser.getPreviousSong(songlist, currentlyPlaying);
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
