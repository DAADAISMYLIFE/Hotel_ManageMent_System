/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.login;

import java.io.IOException;
import java.util.ArrayList;
import teamproject.LoginFrame;
import teamproject.SystemHelper;

/**
 *
 * @author qkekd
 */
public class LoginSystem {
    public static User loginUser;
    private SystemHelper helper;
    private ArrayList<User> userDB;     //유저(일반 직원, 관리자) 정보
    
    
    public void init(){
       loginUser = null;
       userDB = new ArrayList<>();
       helper = new SystemHelper();
       //관리자 추가
       LoginFrame LF = new LoginFrame();
       LF.makeForm();
    }
    
    public void login() throws IOException{
        
        while(loginUser==null){
            System.out.print("");
        }
      
    }
    
    public User runLoginSystem() throws IOException{
        init();
        login();
        return loginUser;
    }
}
