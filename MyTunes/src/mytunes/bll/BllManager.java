/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.dal.DalController;
import mytunes.bll.IBllFacade;
import mytunes.dal.IDalFacade;

/**
 *
 * @author Acer
 */
public class BllManager implements IBllFacade{
    
    private IDalFacade dalController;
    
    public BllManager()
    {
        dalController = new DalController();
    }

    @Override
    public Song createSong(String title, String artist, String genre, String path, int time) {
        return dalController.createSong(title, artist, genre, path, time);
    }

    @Override
    public Song updateSong(Song song, String title, String artist, String genre) {
        return dalController.updateSong(song, title, artist, genre);
    }

    @Override
    public void deleteSong(Song song) {
        dalController.deleteSong(song);
    }

    @Override
    public List<Song> getAllSongs() {
        return dalController.getAllSongs();
    }

    @Override
    public Playlist createPlaylist(String name) {
        return dalController.createPlaylist(name);
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist, String newName) {
        return dalController.updatePlaylist(playlist, newName);
    }

    @Override
    public void deletePlaylist(Playlist playlist) {
        dalController.deletePlaylist(playlist);
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return dalController.getAllPlaylists();
    }

    @Override
    public Playlist switchSongsPlacesOnPlaylist(Playlist playlist, Song song1, Song song2)
    {
        return dalController.switchSongsPlacesOnPlaylist(playlist, song1, song2);
    }

    @Override
    public void deleteSongFromPlaylist(Playlist playlist, Song song) {
        dalController.deleteSongFromPlaylist(playlist, song);
    }

    @Override
    public Playlist addSongToPlaylist(Playlist playlist, Song song) {
        return dalController.addSongToPlaylist(playlist, song);
    }
    
}
