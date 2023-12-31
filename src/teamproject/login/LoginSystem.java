/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.login;

import java.io.IOException;
import java.util.ArrayList;
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
    private User loginUser;
    private SystemHelper helper;
    private ArrayList<User> userDB;     //유저(일반 직원, 관리자) 정보
    
    public void init()throws IOException{
        loginUser = null;
        userDB = new ArrayList<>();
        helper = new SystemHelper();    
        helper.createDBFile(0, "login");
       //관리자 추가
       //User temp = new User("admin","Admin","관리자",true);
       //userDB.add(temp);
       //일반 직원 추가
       //temp = new User("alba","Alba","직원");
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
    }
    
    public void login() throws IOException{
        User loginTryingUser;
        
        do{
            //아이디 입력
            System.out.print("ID : ");
            String ID = helper.getUserInput();
            //비밀번호 입력
            System.out.print("PASS : ");
            String password = helper.getUserInput();

            //비교를 위한 객체 생성
            loginTryingUser = new User(ID,password);
            
            //비교
            for(User temp : userDB){
                if(loginTryingUser.equals(temp)){
                    loginUser = temp;
                    System.out.println("안녕하세요 " + loginUser.getName() + "님");
                    break;
                }
            }
            
            if(loginUser == null)
                System.out.println("정보를 찾을 수 없습니다.");
            
        }while(loginUser == null);
    }
    
    public User runLoginSystem() throws IOException{
        init();
        login();
        
        return loginUser;
    }
}
