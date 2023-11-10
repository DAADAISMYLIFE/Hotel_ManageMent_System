/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject;


import java.io.IOException;
import teamproject.food.FoodSystem;
import teamproject.login.LoginSystem;
import teamproject.login.User;
import teamproject.reservation.ReservationSystem;
import teamproject.room.RoomSystem;

/**
 *
 * @author qkekd
 */
public class IntegrateManager {
    SystemHelper helper;
    LoginSystem LogSys;
    FoodSystem FoodSys;
    ReservationSystem RserSys;
    boolean isQuit;
    User loginUser;
    
    
    
    
    public void initIM(){
        loginUser = null;
        LogSys = new LoginSystem();
        helper = new SystemHelper();
        FoodSys = new FoodSystem();
        RserSys = new ReservationSystem();
    }
            
    public void runIM() throws IOException{
        initIM();
        loginUser = LogSys.runLoginSystem();
        
        while(!isQuit){
            showMainMenu();
        }
    }
    
    public void showMainMenu() throws IOException{
        String rex = "[0-2]";
        System.out.println("현재 날짜");
        helper.showCalendar();
        System.out.println("==============================================");
        System.out.println("1. 객실 및 예약 정보");
        System.out.println("2. 식품 주문 및 정보");
        if(loginUser.getManager() == true){
            rex = "[0-3]";
            System.out.println("3. 시스템 정보 수정 및 보고서 작성");
        }
        System.out.println("0. 종료");
        System.out.println("==============================================");
        
        String selectedMenuS;
        do{
                 selectedMenuS =  helper.getUserInput();
         }while(!helper.CheckFormat(selectedMenuS,rex));
        int selectedMenu =Integer.parseInt(selectedMenuS);
        
        if(selectedMenu == 1){
            System.out.println("객실 현황 / 예약 보기");
            RserSys.runReserSys();
        }
        else if(selectedMenu == 2){
            System.out.println("식품 현황 보기");
            FoodSys.runFoodSystem();
        }
        else if(selectedMenu == 3){
            System.out.println("시스템 정보 보기");
        }
        else if(selectedMenu == 0){
            System.out.println("시스템을 종료합니다.");
            isQuit = true;
        }
        
        
        
        
    }
    
    
}
