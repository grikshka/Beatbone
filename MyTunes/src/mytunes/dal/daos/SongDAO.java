/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mytunes.be.Song;
import mytunes.dal.DbConnectionProvider;

/**
 *
 * @author Acer
 */
public class SongDAO {
    
    private DbConnectionProvider connector;
    
    public SongDAO()
    {
        connector = new DbConnectionProvider();
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
    
}
