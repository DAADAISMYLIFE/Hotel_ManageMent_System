/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import teamproject.food.FoodSystem;
import teamproject.login.LoginSystem;
import teamproject.login.User;
import teamproject.reservation.ReservationSystem;
import teamproject.report.ReportSystem;

/**
 *
 * @author qkekd
 */
public class IntegrateManager {
    SystemHelper helper;
    LoginSystem LogSys;
    FoodSystem FoodSys;
    ReservationSystem RserveSys;
    ReportSystem ReportSys;
    boolean isQuit;
    User loginUser;
    public static JFrame frm;
    
    
    public void initIM() throws IOException{
        loginUser = null;
        helper = new SystemHelper();
        ReportSys = new ReportSystem();
        ReportSys.ReportSystemInit();
        
        LogSys = new LoginSystem(ReportSys);
        RserveSys = new ReservationSystem(ReportSys);
        LogSys.init();
        RserveSys.ReserveSysInit();
        FoodSys = new FoodSystem(RserveSys,ReportSys);
        frm = new JFrame();
    }
            
    public void runIM() throws IOException{
        initIM();
        loginUser = LogSys.runLoginSystem();
        FoodSys.FoodSystemInit(loginUser.getManager());
        showMainMenu();
    }
    
    public void showMainMenu() throws IOException{
        frm.setTitle("호텔 관리 통합 시스템");
        frm.getContentPane().setLayout(null);
        JButton reservationButton = new JButton("객실 및 예약 정보");
        JButton menuButton = new JButton("식품 주문 및 정보");
        JButton reportButton = new JButton("로그 정보");
        JButton quitButton = new JButton("종료");

        reservationButton.setBounds(70, 30, 210, 50);
        menuButton.setBounds(70, 110, 210, 50);
        reportButton.setBounds(70, 190, 210, 50);
        quitButton.setBounds(70, 270, 210, 50);

        frm.getContentPane().add(reservationButton);
        frm.getContentPane().add(menuButton);
         if(loginUser.getManager() == true){
            frm.getContentPane().add(reportButton);
         }
        frm.getContentPane().add(quitButton);

        frm.setSize(350,400);
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        reservationButton.addActionListener(event -> {
           try {
               RserveSys.runReserSys();
               frm.setVisible(false);
           } catch (IOException ex) {
               Logger.getLogger(IntegrateManager.class.getName()).log(Level.SEVERE, null, ex);
           }

        });
        menuButton.addActionListener(event -> {
           try {
               FoodSys.runFoodSystem();
               frm.setVisible(false);
           } catch (IOException ex) {
               Logger.getLogger(IntegrateManager.class.getName()).log(Level.SEVERE, null, ex);
           }

        });

        reportButton.addActionListener(event -> {
           try {
               ReportSys.runReportSystem();
               frm.setVisible(false);
           } catch (IOException ex) {
               Logger.getLogger(IntegrateManager.class.getName()).log(Level.SEVERE, null, ex);
           }
        });

        quitButton.addActionListener(event -> {
           try {
               ReportSys.addReport("login", loginUser.getID()+";logout");
           } catch (IOException ex) {
               Logger.getLogger(IntegrateManager.class.getName()).log(Level.SEVERE, null, ex);
           }
            JOptionPane.showMessageDialog(null, "시스템을 종료합니다.");
            frm.dispose();
            System.exit(0);
        });

        frm.setVisible(true);
    }

    public User getLoginUser() {
        return loginUser;
    }
}
