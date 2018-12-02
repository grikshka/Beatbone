/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll.util;

/**
 *
 * @author Acer
 */
public class TimeConverter {
    
    public static String convertToString(int timeInSeconds)
    {
        String result = "";
        int hours = timeInSeconds/3600;
        timeInSeconds %= 3600;
        int minutes = timeInSeconds/60;
        timeInSeconds %= 60;
        int seconds = timeInSeconds;
        if(hours != 0)
        {
            result += hours + ":";
        }
        if(minutes != 0)
        {
            if(minutes<10)
            {
                result += "0";
            }
            result += minutes + ":";
        }
        else
        {
            result += "00:";
        }
        if(seconds<10)
        {
            result += "0";
        }
        result += timeInSeconds;
        return result;
        
    }
    
    public static int convertToInt(String timeInString)
    {
        String[] time = timeInString.split(":");
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        if(time.length == 2)
        {
            minutes += Integer.parseInt(time[0]);
            seconds += Integer.parseInt(time[1]);
        }
        else
        {
            hours += Integer.parseInt(time[0]);
            minutes += Integer.parseInt(time[1]);
            seconds += Integer.parseInt(time[2]);
            
        }
        return seconds + minutes*60 + hours*3600;
        
    }
    
}
