/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import teamproject.food.FoodSystem;
import teamproject.login.LoginSystem;
import teamproject.login.User;
import teamproject.reservation.ReservationSystem;
import teamproject.room.RoomSystem;
import javax.swing.*;
/**
 *
 * @author qkekd
 */
public class IntegrateManager {
    SystemHelper helper;
    LoginSystem LogSys;
    FoodSystem FoodSys;
    ReservationSystem RserveSys;
    User loginUser;
    public static JFrame frm = new JFrame();

    public void initIM()throws IOException{
        loginUser = null;
        helper = new SystemHelper();
        LogSys = new LoginSystem();
        RserveSys = new ReservationSystem();
        RserveSys.ReserveSysInit();
        FoodSys = new FoodSystem(RserveSys);
        FoodSys.FoodSystemInit();
    }
            
    public void runIM() throws IOException{
        initIM();
        loginUser = LogSys.runLoginSystem();
       // while(!isQuit){
            showMainMenu();
        //}
    }
    
    public void run(){
        
         frm.getContentPane().setLayout(null);
         
         JButton btn1 = new JButton("객실 및 예약 정보");
        JButton btn2 = new JButton("식품 주문 및 정보");
         JButton btn3 = new JButton("시스템 정보 및 보고서");
         JButton btn4 = new JButton("종료");

        btn1.setBounds(182, 120, 172, 30);
        btn2.setBounds(182, 170, 172, 30);
        btn3.setBounds(182, 220, 172, 30);
        btn4.setBounds(182, 270, 172, 30);
        
        frm.getContentPane().add(btn1);
        frm.getContentPane().add(btn2);
        frm.getContentPane().add(btn3);
        frm.getContentPane().add(btn4);
              
        frm.setSize(500,500);
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
          btn1.addActionListener(event -> {
            System.out.println("객실 현황 / 예약 보기");
                 RserveSys.runReserSys();
             frm.setVisible(false);
        });
        btn2.addActionListener(event -> {
             System.out.println("식품 현황 보기");
                 FoodSys.runFoodSystem();
             frm.setVisible(false);
        });
        
        btn3.addActionListener(event -> {
            System.out.println("시스템 정보 및 보고서");
        });
        
        btn4.addActionListener(event -> {
            System.out.println("시스템을 종료합니다.");
            frm.dispose();
            System.exit(0);
        });
        
        frm.setVisible(true);
    }
    
    public void showMainMenu() throws IOException{
        run();
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
                break;
            case 0:
                System.out.println("시스템을 종료합니다.");
                break;
            default:
                break;
        }
        
        
        
        
    }
    
    
}
