/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Song;

/**
 *
 * @author Acer
 */
public interface IDalFacade {
    
    Song createSong(String title, String artist, String genre, String path, int time);
    
    Song updateSong(Song song, String title, String artist, String genre);
    
    void deleteSong(Song song);
    
    List<Song> getAllSongs();
    
    Playlist createPlaylist(String name);
    
    Playlist updatePlaylist(Playlist playlist, String newName);
    
    void deletePlaylist(Playlist playlist);
    
    List<Playlist> getAllPlaylists();
    
    Playlist moveSongUpOnPlaylist(Playlist playlist, Song song);
    
    Playlist moveSongDownOnPlaylist(Playlist playlist, Song song);
    
    void deleteSongFromPlaylist(Playlist playlist, Song song);
    
    Playlist addSongToPlaylist(Playlist playlist, Song song);
    
}
