/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject;


import java.io.IOException;
import teamproject.food.FoodSystem;
import teamproject.login.LoginSystem;
import teamproject.login.User;

/**
 *
 * @author qkekd
 */
public class IntegrateManager {
    SystemHelper helper;
    LoginSystem LogSys;
    FoodSystem FoodSys;
    
    User loginUser;
    
    
    
    
    public void initIM(){
        loginUser = null;
        LogSys = new LoginSystem();
        helper = new SystemHelper();
        FoodSys = new FoodSystem();
    }
            
    public void runIM() throws IOException{
        initIM();
        loginUser = LogSys.runLoginSystem();
        
        while(true){
            showMainMenu();
        }
    }
    
    public void showMainMenu() throws IOException{
        System.out.println("==============================================");
        System.out.println("1. 객실 및 예약 정보");
        System.out.println("2. 식품 주문 및 정보");
        if(loginUser.getManager() == true){
            System.out.println("3. 시스템 정보 수정 및 보고서 작성");
        }
        System.out.println("==============================================");
        
        int selectedMenu =Integer.parseInt(helper.getUserInput());
        
        if(selectedMenu == 1){
            System.out.println("객실 현황 보기");
        }
        else if(selectedMenu == 2){
            System.out.println("식품 현황 보기");
            FoodSys.runFoodSystem();
        }
        else if(selectedMenu == 3){
            System.out.println("시스템 정보 보기");
        }
        
        
        
        
    }
    
    
}
