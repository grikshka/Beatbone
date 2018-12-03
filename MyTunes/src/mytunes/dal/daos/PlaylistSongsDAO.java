/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    
}
