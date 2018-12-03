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
import java.sql.Statement;
import mytunes.be.Playlist;
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
}
