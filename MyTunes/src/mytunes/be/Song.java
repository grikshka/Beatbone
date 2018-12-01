/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;
 /**
 *
 * @author Acer
 */
public class Song {
    
    private String title;
    private String artist;
    private String genre;
    private int time;
    
    public Song(String title, String artist, String genre, int time) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
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
    
    
    
}
