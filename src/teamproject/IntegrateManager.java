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
import teamproject.report.ReportSysyem;

/**
 *
 * @author qkekd
 */
public class IntegrateManager {
    SystemHelper helper;
    LoginSystem LogSys;
    FoodSystem FoodSys;
    ReservationSystem RserveSys;
    ReportSysyem ReportSys;
    boolean isQuit;
    User loginUser;
    
    
    
    
    public void initIM() throws IOException{
        loginUser = null;
        helper = new SystemHelper();
        
        
        LogSys = new LoginSystem();
        RserveSys = new ReservationSystem();
        LogSys.init();
        RserveSys.ReserveSysInit();
        FoodSys = new FoodSystem(RserveSys);
        FoodSys.FoodSystemInit();
       ReportSys = new ReportSysyem();
       ReportSys.ReportSystemInit();
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
        helper.showTodayDate();
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
        
        switch (selectedMenu) {
            case 1:
                RserveSys.runReserSys();
                break;
            case 2:
                FoodSys.runFoodSystem();
                break;
            case 3:
                ReportSys.runReportSystem();
                break;
            case 0:
                System.out.println("시스템을 종료합니다.");
                isQuit = true;
                break;
            default:
                break;
        }
        
        
        
        
    }
    
    
}
