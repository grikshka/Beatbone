/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.dal.daos.GenreDAO;
import mytunes.dal.daos.PlaylistDAO;
import mytunes.dal.daos.PlaylistSongsDAO;
import mytunes.dal.daos.SongDAO;

/**
 *
 * @author Acer
 */
public class DalController implements IDalFacade{
    
    private SongDAO songDao;
    private PlaylistDAO playlistDao;
    private PlaylistSongsDAO playlistSongsDao;
    private GenreDAO genreDao;
    
    public DalController()
    {
        songDao = new SongDAO();
        playlistDao = new PlaylistDAO();
        playlistSongsDao = new PlaylistSongsDAO();
        genreDao = new GenreDAO();
    }
    
    public Song createSong(String title, String artist, String genre, String path, int time)
    {
        Song createdSong = null;
        try
        {
            createdSong = songDao.createSong(title,artist,genre,path,time);
        }
        catch(SQLException e)
        {
            //TO DO
        }
        return createdSong;
    }

    @Override
    public Song updateSong(Song song, String title, String artist, String genre) {
        Song updatedSong = null;
        try
        {
            updatedSong = songDao.updateSong(song, title, artist, genre);
        }
        catch(SQLException e)
        {
            //TO DO
        }
        return updatedSong;
    }

    @Override
    public void deleteSong(Song song) {
        try
        {
            songDao.deleteSong(song);
        }
        catch(SQLException e)
        {
            //TO DO
        }
    }

    @Override
    public List<Song> getAllSongs() {
        List<Song> allSongs = null;
        try
        {
            allSongs = songDao.getAllSongs();
        }
        catch(SQLException e)
        {
            //TO DO
        }
        return allSongs;
    }

    @Override
    public Playlist createPlaylist(String name) {
        Playlist createdPlaylist = null;
        try
        {
            createdPlaylist = playlistDao.createPlaylist(name);
        }
        catch(SQLException e)
        {
            //TO DO
        }
        return createdPlaylist;
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist, String newName) {
        Playlist updatedPlaylist = null;
        try
        {
            updatedPlaylist = playlistDao.updatePlaylist(playlist, newName);
        }
        catch(SQLException e)
        {
            //TO DO
        }
        return updatedPlaylist;
    }

    @Override
    public void deletePlaylist(Playlist playlist) {
        try
        {
            playlistDao.deletePlaylist(playlist);
        }
        catch(SQLException e)
        {
            //TO DO
        }
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        List<Playlist> allPlaylists = null;
        try
        {
            allPlaylists = playlistDao.getAllPlaylists();
        }
        catch(SQLException e)
        {
            //TO DO
        }
        return allPlaylists;
    }

    @Override
    public Playlist switchSongsPlacesOnPlaylist(Playlist playlist, Song song1, Song song2)
    {
        Playlist updatedPlaylist = null;
        try
        {
            updatedPlaylist = playlistSongsDao.switchSongPlacesOnPlaylist(playlist, song1, song2);
        }
        catch(SQLException e)
        {
            //TO DO
        }
        return updatedPlaylist;
    }

    @Override
    public void deleteSongFromPlaylist(Playlist playlist, Song song) {
        try
        {
            playlistSongsDao.deleteSongFromPlaylist(playlist, song);
        }
        catch(SQLException e)
        {
            //TO DO
        }
    }

    @Override
    public Playlist addSongToPlaylist(Playlist playlist, Song song) {
        Playlist updatedPlaylist = null;
        try
        {
            updatedPlaylist = playlistSongsDao.addSongToPlaylist(playlist, song);
        }
        catch(SQLException e)
        {
            //TO DO
        }
        return updatedPlaylist;
    }
    
    @Override
    public List<String> getAllGenres()
    {
        List<String> allGenres = null;
        try
        {
            allGenres = genreDao.getAllGenres();
        }
        catch(SQLException e)
        {
            //TO DO
        }
        return allGenres;
    }

}
