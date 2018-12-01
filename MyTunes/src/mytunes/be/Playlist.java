/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mytunes.be;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */

public class Playlist {
    
    private String name;
    private int time;
    private int numberOfSongs;
    private List<Song> tracklist;
    
    public Playlist(String name)
    {
        this.name = name;
        time = 0;
        numberOfSongs = 0;
        tracklist = new ArrayList();
    }
    
    public void addSong(Song song)
    {
        tracklist.add(song);
        time += song.getTime();
        numberOfSongs++;
    }
    
    public void removeSong(Song song)
    {
        tracklist.remove(song);
        time -= song.getTime();
        numberOfSongs--;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public List<Song> getTracklist() 
    {
        return tracklist;
    }
    
    public void setTracklist(List<Song> songs) 
    {
        tracklist.clear();
        tracklist.addAll(songs);
    }

    public int getTime() 
    {
        return time;
    }

    public int getNumberOfSongs() 
    {
        return numberOfSongs;
    }
        
}
