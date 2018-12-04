/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.dal.DbConnectionProvider;

/**
 *
 * @author Acer
 */
public class PlaylistSongsDAO {
    
    private DbConnectionProvider connector;
    
    public PlaylistSongsDAO()
    {
        connector = new DbConnectionProvider();
    }
    
    public Playlist addSongToPlaylist(Playlist playlist, Song song) throws SQLException
    {
        String sqlStatement = "INSERT INTO PlaylistSongs(playlistId, songId) values(?,?);"
                + "UPDATE Playlists SET time=?, numberOfSongs=? WHERE id=?";
        try(Connection con = connector.getConnection();
                PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            statement.setInt(1, playlist.getId());
            statement.setInt(2, song.getId());
            statement.setInt(3, playlist.getTime() + song.getTime());
            statement.setInt(4, playlist.getNumberOfSongs()+1);
            statement.setInt(5, playlist.getId());
            statement.execute();
            playlist.addSong(song);
            return playlist;
        }
    }
    
    public void addAllSongsToAllPlaylists(List<Playlist> allPlaylists) throws SQLException
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
    
    public void switchSongPlacesOnPlaylist(Playlist playlist, Song song1, Song song2) throws SQLException
    {
        String sqlStatement = "UPDATE PlaylistSongs SET playlistId=?, songId=? WHERE playlistId=? and songId=?;" +
                    "UPDATE PlaylistSongs SET playlistId=?, songId=? WHERE playlistId=? and songId=?;";
        try(Connection con = connector.getConnection();
                PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            statement.setInt(1, playlist.getId());
            statement.setInt(2, song2.getId());
            statement.setInt(3, playlist.getId());
            statement.setInt(4, song1.getId());
            statement.setInt(5, playlist.getId());
            statement.setInt(6, song1.getId());
            statement.setInt(7, playlist.getId());
            statement.setInt(8, song2.getId());
            statement.execute();
        }
    }
    
    public void deleteSongFromPlaylist(Playlist playlist, Song song) throws SQLException
    {
        String sqlStatement = "DELETE FROM PlaylistSongs WHERE playlistId=? and songId=?";
        try(Connection con = connector.getConnection();
                PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            statement.setInt(1, playlist.getId());
            statement.setInt(2, song.getId());
            statement.execute();
        }
    }
    
}
