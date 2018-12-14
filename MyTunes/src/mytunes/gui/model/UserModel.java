/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import mytunes.be.User;
import mytunes.bll.BllManager;
import mytunes.bll.IBllFacade;

/**
 *
 * @author Acer
 */
public class UserModel {
    
    private static UserModel instance;
    private IBllFacade bllManager;
    
    private UserModel()
    {
        bllManager = new BllManager();
    }
    
    public static UserModel createInstance()
    {
        if(instance == null)
        {
            instance = new UserModel();
        }
        return instance;
    }
    
    public User createUser(String email, String password)
    {
        return bllManager.createUser(email, password);
    }
    
    public User getUser(String email, String password)
    {
        return bllManager.getUser(email, password);
    }
}
