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
import java.util.ArrayList;
import java.util.List;
import mytunes.dal.DbConnectionProvider;

/**
 *
 * @author Acer
 */
public class GenreDAO {
    
    private DbConnectionProvider connector;
    
    public GenreDAO()
    {
        connector = new DbConnectionProvider();
    }
    
    public List<String> getAllGenres() throws SQLException
    {
        List<String> allGenres = new ArrayList();
        String sqlStatement = "SELECT * FROM Genres";
        try(Connection con = connector.getConnection();
                PreparedStatement statement = con.prepareStatement(sqlStatement))
        {
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                allGenres.add(rs.getString("name"));
            }
            return allGenres;
        }
    }
    
}
