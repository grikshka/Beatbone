/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import mytunes.bll.util.TimeConverter;

 /**
 *
 * @author Acer
 */
public class Song {
    
    private String title;
    private String artist;
    private String genre;
    private String path;
    private int time;
    
    public Song(String title, String artist, String genre, String path, int time) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.path = path;
        this.time = time;
    }
    
    public String getTitle() 
    {
        return title;
    }
    
    public String getArtist() 
    {
        return artist;
    }
    
    public String getGenre() 
    {
        return genre;
    }
    
    public int getTime() 
    {
        return time;
    }
    
    public void setTitle(String title) 
    {
        this.title = title;
    }
    
    public void setArtist(String artist) 
    {
        this.artist = artist;
    }
    public void setGenre(String genre) 
    {
        this.genre = genre;
    }
    
    public String getPath() 
    {
        return path;
    }

    public void setPath(String path) 
    {
        this.path = path;
    }
    
    public String getTimeInString()
    {
        return TimeConverter.convertToString(time);
    }
    
    @Override
    public String toString()
    {
        return title;
    }
    
}
