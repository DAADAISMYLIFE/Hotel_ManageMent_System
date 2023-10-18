/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject;


import java.io.IOException;
import teamproject.login.LoginSystem;
import teamproject.login.User;

/**
 *
 * @author qkekd
 */
public class IntegrateManager {
    User loginUser;
    LoginSystem LogSys;
    
    
    
    public void initIM(){
        loginUser = null;
        LogSys = new LoginSystem();
    }
            
    public void runIM() throws IOException{
        initIM();
        loginUser = LogSys.runLoginSystem();
        
        
        
    }
    
    
}
