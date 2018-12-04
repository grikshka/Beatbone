/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal.daos;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.dal.DbConnectionProvider;

/**
 *
 * @author Acer
 */
public class PlaylistDAO {
    
    private DbConnectionProvider connector;
    
    public PlaylistDAO()
    {
        connector = new DbConnectionProvider();
    }
    
    public Playlist createPlaylist(String name) throws SQLException
    {
        String sqlStatement = "INSERT INTO Playlists(name, time, numberOfSongs) values(?, ?, ?)";
        try(Connection con = connector.getConnection();
                PreparedStatement statement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS))
        {
            statement.setString(1, name);
            statement.setInt(2, 0);
            statement.setInt(3, 0);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            return new Playlist(id, name);
        }
    }
    
    public Playlist updatePlaylist(Playlist playlist, String newName) throws SQLException
    {
        String sqlStatement = "UPDATE Playlists SET name=? WHERE id=?";
        try(Connection con = connector.getConnection();
                PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            statement.setString(1, newName);
            statement.setInt(2, playlist.getId());
            statement.execute();
            playlist.setName(newName);
            return playlist;
        }
    }
    
    public List<Playlist> getAllPlaylists() throws SQLException
    {
        String sqlStatement = "SELECT * FROM Playlists";
        List<Playlist> allPlaylists = new ArrayList();
        try(Connection con = connector.getConnection();
                PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                allPlaylists.add(new Playlist(id, name));
            }
            addSongsToPlaylists(allPlaylists);
        }
        return allPlaylists;
    }
    
    public void deletePlaylist(Playlist playlist) throws SQLException
    {
        String sqlStatement = "DELETE FROM Playlists WHERE id=?";
        try(Connection con = connector.getConnection();
                PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            statement.setInt(1, playlist.getId());
            statement.execute();
        }
    }
    
    private void addSongsToPlaylists(List<Playlist> allPlaylists) throws SQLException
    {
        String sqlStatement = "SELECT Playlists.id as playlistId, Songs.* FROM PlaylistSongs " +
                                        "INNER JOIN Playlists on PlaylistSongs.playlistId=Playlists.id " +
                                        "INNER JOIN Songs on PlaylistSongs.songId=Songs.id";
        try(Connection con = connector.getConnection();
                PreparedStatement songsStatement = con.prepareStatement(sqlStatement))
        {
        ResultSet rs = songsStatement.executeQuery();
        rs.next();
        for(Playlist p : allPlaylists)
            {
                while(!rs.isAfterLast() && rs.getInt("playlistId") == p.getId())
                {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String artist = rs.getString("artist");
                    String genre = rs.getString("genre");
                    String path = rs.getString("path");
                    int time = rs.getInt("time");
                    p.addSong(new Song(id, title, artist, genre, path, time));
                    rs.next();
                }
            }
        }
    }
}
