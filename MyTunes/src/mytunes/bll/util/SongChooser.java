package mytunes.bll.util;

import java.util.List;
import java.util.Random;
import java.util.Stack;
import mytunes.be.Song;

/**
 * The {@code SongChooser} class is responsible for
 * 
 * 
 * @author schemabuoi
 * @author kiddo
 */
public class SongChooser {
    
    private Stack<Song> previousSongs = new Stack();
    
    public Song getFirstSong(List<Song> songList)
    {
        if(songList.isEmpty())
        {
            return null;
        }
        return songList.get(0);
    }
    
    public Song getRandomSong(List<Song> songList)
    {
        Random rand = new Random();
        int songId = rand.nextInt(songList.size());
        return songList.get(songId);
    }
    
    public Song getNextSong(List<Song> songList, Song currentSong)
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
    
    public Song getPreviousSong(List<Song> songList, Song currentSong)
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
    
    public Song getNextRandomSong(List<Song> songList, Song currentSong)
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
    
    public Song getPreviousRandomSong(Song currentSong)
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
    
    public void clearPreviousRandomSongs()
    {
        previousSongs = new Stack();
    }
    
}
