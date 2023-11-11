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
    RoomSystem RoomSys;
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
    
    public void run() throws IOException{
        
         JFrame frm = new JFrame();
         
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
             try {
                 RserSys.runReserSys();
             } catch (IOException ex) {
                 Logger.getLogger(IntegrateManager.class.getName()).log(Level.SEVERE, null, ex);
             }
        });
        btn2.addActionListener(event -> {
             System.out.println("식품 현황 보기");
             try {
                 FoodSys.runFoodSystem();
             } catch (IOException ex) {
                 Logger.getLogger(IntegrateManager.class.getName()).log(Level.SEVERE, null, ex);
             }
        });
        
        btn3.addActionListener(event -> {
            System.out.println("시스템 정보 및 보고서");
            
           
        });
        
        btn4.addActionListener(event -> {
            System.out.println("시스템을 종료합니다.");
            isQuit = true;
            frm.dispose();
            System.exit(0);
        });
        
        frm.setVisible(true);
    }
    
            
    public void runIM() throws IOException{
        initIM();
        loginUser = LogSys.runLoginSystem();
        
        
         showMainMenu();
        
    }
    
    public void showMainMenu() throws IOException{
        run();
        String rex = "[0-2]";
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
