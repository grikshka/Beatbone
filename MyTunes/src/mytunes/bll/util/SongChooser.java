/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll.util;

import java.util.List;
import java.util.Random;
import java.util.Stack;
import mytunes.be.Song;

/**
 *
 * @author Acer
 */
public class SongChooser {
    
    private static Stack<Song> previousSongs = new Stack();
    
    public static Song getFirstSong(List<Song> songList)
    {
        if(songList.isEmpty())
        {
            return null;
        }
        return songList.get(0);
    }
    
    public static Song getRandomSong(List<Song> songList)
    {
        Random rand = new Random();
        int songId = rand.nextInt(songList.size());
        return songList.get(songId);
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
    
    public static Song getNextRandomSong(List<Song> songList, Song currentSong)
    {
        Random rand = new Random();
        int currentSongId = songList.indexOf(currentSong);
        int nextSongId = currentSongId;
        while(currentSongId == nextSongId)
        {
            nextSongId = rand.nextInt(songList.size());
        }
        previousSongs.add(currentSong);
        return songList.get(nextSongId);
    }
    
    public static Song getPreviousRandomSong(Song currentSong)
    {
        if(previousSongs.isEmpty())
        {
            return currentSong;
        }
        else
        {
            return previousSongs.pop();
        }
    }
    
    public static void clearPreviousRandomSongs()
    {
        previousSongs = new Stack();
    }
    
}
