/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll.util;

import java.util.ArrayList;
import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Song;

/**
 *
 * @author Acer
 */
public class MusicSearcher {
    
    public static List<Song> searchSongs(List<Song> allSongs, String filter)
    {
        filter = filter.toLowerCase();
        List<Song> filteredSongs = new ArrayList();
        for(Song s : allSongs)
        {
            if(filter.length() <= s.getTitle().length() && filter.equals(s.getTitle().toLowerCase().substring(0, filter.length())))
            {
                filteredSongs.add(s);
            }
            else if(filter.length() <= s.getArtist().length() && filter.equals(s.getArtist().toLowerCase().substring(0, filter.length())))
            {
                filteredSongs.add(s);
            }
        }
        return filteredSongs;
    }
    
    public static List<Playlist> searchPlaylists(List<Playlist> allPlaylists, String filter)
    {
        filter = filter.toLowerCase();
        List<Playlist> filteredPlaylists = new ArrayList();
        for(Playlist p : allPlaylists)
        {
            if(filter.length() <= p.getName().length() && filter.equals(p.getName().toLowerCase().substring(0, filter.length())))
            {
                filteredPlaylists.add(p);
            }
        }
        return filteredPlaylists;
        
    }
    
}
