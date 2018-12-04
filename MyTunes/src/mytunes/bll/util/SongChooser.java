/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll.util;

import java.util.List;
import mytunes.be.Song;

/**
 *
 * @author Acer
 */
public class SongChooser {
    
    public static Song getFirstSong(List<Song> songList)
    {
        if(songList.isEmpty())
        {
            return null;
        }
        return songList.get(0);
    }
    
    public static Song getNextSong(List<Song> songList, Song currentSong)
    {
        int index = songList.indexOf(currentSong);
        if(index == songList.size()-1)
        {
            return getFirstSong(songList);
        }
        else
        {
            return songList.get(index+1);
        }
    }
    
    public static Song getPreviousSong(List<Song> songList, Song currentSong)
    {
        int index = songList.indexOf(currentSong);
        if(index == 0)
        {
            return currentSong;
        }
        else
        {
            return songList.get(index-1);
        }
    }
    
}
