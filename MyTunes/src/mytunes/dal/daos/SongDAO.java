/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal.daos;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mytunes.be.Song;
import mytunes.dal.DbConnectionProvider;

/**
 *
 * @author Acer
 */
public class SongDAO {
    
    private DbConnectionProvider connector;
    private PlaylistSongsDAO playlistSongsDao;
    
    public SongDAO()
    {
        connector = new DbConnectionProvider();
        playlistSongsDao = new PlaylistSongsDAO();
    }
    
    public Song createSong(String title, String artist, String genre, String path, int time) throws SQLException
    {
        String sqlStatement = "INSERT INTO Songs(title, artist,genre,path,time) values(?,?,?,?,?)";
        try(Connection con = connector.getConnection();
                PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS))
        {
            statement.setString(1, title);
            statement.setString(2, artist);
            statement.setString(3, genre);
            statement.setString(4, path);
            statement.setInt(5, time);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            return new Song(id, title,artist,genre,path,time);
        }
    }
    
    public Song updateSong(Song song, String newTitle, String newArtist, String newGenre) throws SQLException
    {
        String sqlStatement = "UPDATE Songs SET title=?, artist=?, genre=? WHERE id=?";
        try(Connection con = connector.getConnection();
                PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            statement.setString(1, newTitle);
            statement.setString(2, newArtist);
            statement.setString(3, newGenre);
            statement.setInt(4, song.getId());
            statement.execute();
            song.setTitle(newTitle);
            song.setArtist(newArtist);
            song.setGenre(newGenre);
            return song;
        }
    }
    
    public List<Song> getAllSongs() throws SQLException
    {
        String sqlStatement = "SELECT * FROM Songs";
        List<Song> allSongs = new ArrayList();
        try(Connection con = connector.getConnection();
                PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String artist = rs.getString("artist");
                String genre = rs.getString("genre");
                String path = rs.getString("path");
                int time = rs.getInt("time");
                allSongs.add(new Song(id,title,artist,genre,path,time));
            }
        }
        return allSongs;
    }
    
    public void deleteSong(Song song) throws SQLException
    {
        playlistSongsDao.deleteSongFromAllPlaylists(song);
        String sqlStatement = "DELETE FROM Songs WHERE id=?";
        try(Connection con = connector.getConnection();
                PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            statement.setInt(1, song.getId());
            statement.execute();
        }
    }
    
}
