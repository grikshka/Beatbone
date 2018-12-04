/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Song;

/**
 *
 * @author Acer
 */
public interface IBllFacade {
    
    Song createSong(String title, String artist, String genre, String path, int time);
    
    Song updateSong(Song song, String title, String artist, String genre);
    
    void deleteSong(Song song);
    
    List<Song> getAllSongs();
    
    Playlist createPlaylist(String name);
    
    Playlist updatePlaylist(Playlist playlist, String newName);
    
    void deletePlaylist(Playlist playlist);
    
    List<Playlist> getAllPlaylists();
    
    void switchSongsPlacesOnPlaylist(Playlist playlist, Song song1, Song song2);
    
    void deleteSongFromPlaylist(Playlist playlist, Song song);
    
    Playlist addSongToPlaylist(Playlist playlist, Song song);
    
    
}
