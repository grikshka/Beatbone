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
import mytunes.dal.daos.PlaylistDAO;
import mytunes.dal.daos.SongDAO;

/**
 *
 * @author Acer
 */
public class DalController implements IDalFacade{
    
    private SongDAO songDao;
    private PlaylistDAO playlistDao;
    
    public DalController()
    {
        songDao = new SongDAO();
        playlistDao = new PlaylistDAO();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletePlaylist(Playlist playlist) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Playlist moveSongUpOnPlaylist(Playlist playlist, Song song) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Playlist moveSongDownOnPlaylist(Playlist playlist, Song song) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteSongFromPlaylist(Playlist playlist, Song song) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Playlist addSongToPlaylist(Playlist playlist, Song song) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
