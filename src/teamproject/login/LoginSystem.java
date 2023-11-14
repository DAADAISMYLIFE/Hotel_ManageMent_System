/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.login;

import java.io.IOException;
import java.util.ArrayList;
import teamproject.LoginFrame;
import teamproject.SystemHelper;
//파일 입출력
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author qkekd
 */
public class LoginSystem {
    public static User loginUser;
    private SystemHelper helper;
    private ArrayList<User> userDB;     //유저(일반 직원, 관리자) 정보
    
    public void init()throws IOException{
        loginUser = null;
        userDB = new ArrayList<>();
        helper = new SystemHelper();    
        helper.createDBFile(0, "login");
       //관리자 추가
        ArrayList<String> readContext = helper.readDBFile(0);
        if(readContext == null){
            return;
        }
        else{
            for(String temp : readContext){
                User tempUser = new User (temp.split(";")[0],temp.split(";")[1],temp.split(";")[2],Boolean.parseBoolean(temp.split(";")[3]));
                userDB.add(tempUser);
            }
        }

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
