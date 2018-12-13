/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.bll.BllManager;

/**
 *
 * @author Acer
 */
public class GenresViewModel {
    
    private static GenresViewModel instance;
    private BllManager bllManager;
    private ObservableList<String> mainGenres;
    private ObservableList<String> allGenres;
    private String selectedGenre;
    
    private GenresViewModel()
    {
        bllManager = new BllManager();
        mainGenres = FXCollections.observableArrayList(new ArrayList());
        addMainGenres();
        allGenres = FXCollections.observableArrayList(bllManager.getAllGenres());
    }
    
    public static GenresViewModel createInstance()
    {
        if(instance == null)
        {
            instance = new GenresViewModel();
        }
        return instance;
    }
    
    public ObservableList<String> getMainGenres()
    {
        return mainGenres;
    }
    
    public ObservableList<String> getAllGenres()
    {
        return allGenres;
    }
    
    public String getSelectedGenre()
    {
        return selectedGenre;
    }
    
    public void clearSelectedGenre()
    {
        selectedGenre = null;
    }
    
    public void setSelectedGenre(String name)
    {
        selectedGenre = name;
    }

    private void addMainGenres() {
        mainGenres.add("Rap");
        mainGenres.add("Jazz");
        mainGenres.add("Blues");
        mainGenres.add("Rock");
        mainGenres.add("Pop");
    }
}
