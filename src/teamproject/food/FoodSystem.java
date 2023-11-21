/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.food;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import teamproject.IntegrateManager;
import teamproject.SystemHelper;
import teamproject.reservation.ReservationSystem;
import teamproject.reservation.ReservedInfo;
import teamproject.report.ReportSystem;


/**
 *
 * @author qkekd
 */
public class FoodSystem extends JFrame{
    private ArrayList<Food> foodDB; // 식품 관련 정보(메뉴 이름, 가격)
    private ReservationSystem reserveSys;
    private SystemHelper helper;
    private int foodCount;
    private ReportSystem foodReport;
    private boolean isManager;
    
    JFrame frmF = new JFrame();
    private DefaultTableModel tableModel;
    private JTable foodTable;
    public void runF() {
         frmF.getContentPane().setLayout(null);
         
         JButton btn1 = new JButton("메뉴 보기");
         JButton btn2 = new JButton("음식 추가");
         JButton btn3 = new JButton("음식 주문");
         JButton btn4 = new JButton("메뉴 삭제");
         JButton btn5 = new JButton("뒤로가기");

        btn1.setBounds(182, 120, 172, 30);
        btn2.setBounds(182, 170, 172, 30);
        btn3.setBounds(182, 220, 172, 30);
        btn4.setBounds(182, 270, 172, 30);
        btn5.setBounds(182, 320, 172, 30);
        
        frmF.getContentPane().add(btn1);
        frmF.getContentPane().add(btn2);
        if(isManager){
        frmF.getContentPane().add(btn3);
        frmF.getContentPane().add(btn4);
        }
        frmF.getContentPane().add(btn5);
              
        frmF.setSize(500,500);
        frmF.setLocationRelativeTo(null);
        frmF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
         btn1.addActionListener(event -> {          
            showFood();
            frmF.setVisible(false);
        });
         btn2.addActionListener(event -> {          
             try {
                 addFood();
             } catch (IOException ex) {
                 Logger.getLogger(FoodSystem.class.getName()).log(Level.SEVERE, null, ex);
             }
             frmF.setVisible(false);
        });
         btn3.addActionListener(event -> {          
             try {
                 showFood();
                 orderFood();
             } catch (IOException ex) {
                 Logger.getLogger(FoodSystem.class.getName()).log(Level.SEVERE, null, ex);
             }
             frmF.setVisible(false);
        });
         btn4.addActionListener(event -> {    
             try {
                 deleteFood();
             } catch (IOException ex) {
                 Logger.getLogger(FoodSystem.class.getName()).log(Level.SEVERE, null, ex);
             }
            
            frmF.setVisible(false);
        });
        btn5.addActionListener(event -> {
            System.out.println("식품 시스템 종료.");
            frmF.setVisible(false);
            IntegrateManager.frm.setVisible(true);
        });
        
        frmF.setVisible(true);
    }
    
    public FoodSystem(ReservationSystem reserveSys,ReportSystem reportSys) {
        this.reserveSys = reserveSys;
        foodReport = reportSys;
    }
    
    public void FoodSystemInit(boolean isManager)throws IOException{
        foodDB = new ArrayList<>();
        helper = new SystemHelper();
        foodCount = 1;
        this.isManager = isManager;
        helper.createDBFile(2, "food");
        for(String readContext : helper.readDBFile(2)){
            Food temp= new Food(Integer.parseInt(readContext.split(";")[0]),readContext.split(";")[1],Integer.parseInt(readContext.split(";")[2]));
            foodDB.add(temp);
            foodCount++;
        }
    }
    
    public void addFood() throws IOException{
         JFrame addF = new JFrame();
         addF.getContentPane().setLayout(null);
         
         JButton btn1 = new JButton("추가");
         JTextField add_name = new JTextField(10);
         JTextField add_price = new JTextField(10);
         
        btn1.setBounds(182, 120, 172, 30);
        add_name.setBounds(182, 170, 172, 30);
        add_price.setBounds(182, 210, 172, 30);
        addF.getContentPane().add(btn1);
        addF.getContentPane().add(add_name);
        addF.getContentPane().add(add_price);
              
        addF.setSize(500,500);
        addF.setLocationRelativeTo(null);
        addF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
         btn1.addActionListener(event -> {          
         String name = add_name.getText();
         String temp =add_price.getText();
         int price = Integer.parseInt(temp);
            Food newFood = new Food(foodCount,name,price);
            foodCount++;       
            foodDB.add(newFood);
            addF.setVisible(false); 
             try {
                 helper.writeDBFile(2, foodDB);
                 foodReport.addReport("menu","add;"+(newFood.getName()+";"+newFood.getPrice()));
             } catch (IOException ex) {
                 Logger.getLogger(FoodSystem.class.getName()).log(Level.SEVERE, null, ex);
             }
             frmF.setVisible(true);
        });
        addF.setVisible(true);
    }
    
    public void deleteFood() throws IOException{
        if(foodDB.isEmpty()){
            System.out.println("메뉴가 존재하지 않습니다.");
            return;
        }
         JFrame delF = new JFrame();
         delF.getContentPane().setLayout(null);
         JLabel lab = new JLabel("메뉴 ID: ");
         JButton btn1 = new JButton("삭제");
         JTextField del_ID = new JTextField(10);
         lab.setBounds(10, 10,50, 30);
        btn1.setBounds(10, 50, 172, 30);
        del_ID.setBounds(100, 10, 60, 30);
      
        delF.getContentPane().add(btn1);
        delF.getContentPane().add(del_ID);
              
        delF.setSize(200,200);
        delF.setLocationRelativeTo(null);
        delF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
         btn1.addActionListener(event -> {          
         String ID = del_ID.getText();
         int id = Integer.parseInt(ID);
         Food newFood = new Food(id);
         for(Food temp: foodDB){
             if(temp.getMenuID()==id){
                 foodDB.remove(newFood);
                 newFood =temp;
                 break;
             }
         }
            try {
                foodReport.addReport("menu","delete;"+newFood.getName()+";"+newFood.getPrice());
                helper.writeDBFile(2, foodDB);
            } catch (IOException ex) {
                Logger.getLogger(FoodSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        delF.setVisible(false);
        frmF.setVisible(true);
        });
        delF.setVisible(true);
        showFood();
    }
    
    public void showFood(){
        JFrame frmF_show = new JFrame();
        frmF_show.setTitle("식품 메뉴 보기");
        frmF_show.setSize(500, 300);
        frmF_show.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 테이블 모델 및 테이블 생성
        tableModel = new DefaultTableModel();
        tableModel.addColumn("식품ID");
        tableModel.addColumn("식품이름");
        tableModel.addColumn("식품가격");

        foodTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(foodTable);
        frmF_show.add(scrollPane, BorderLayout.CENTER);
        foodTable.setEnabled(true);
            for(int i = 0;i < foodDB.size(); i++){
                 tableModel.addRow(new String[]{String.valueOf(foodDB.get(i).getMenuID()), foodDB.get(i).getName(), String.valueOf(foodDB.get(i).getPrice())});
            }
        // 테이블 편집 비활성화
        foodTable.setEnabled(true);

            JButton hideButton = new JButton("확인");
        
         hideButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmF_show.dispose();
                frmF.setVisible(true);
            }
        });
         
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hideButton);
        frmF_show.add(buttonPanel, BorderLayout.SOUTH);
        // 레이아웃 설정
        frmF_show.pack();
        frmF_show. setLocationRelativeTo(null); // 화면 정중앙에 표시
        frmF_show.setVisible(true);
    }
    
    public void orderFood() throws IOException{
        
         JFrame frmF_order = new JFrame();
         frmF_order.getContentPane().setLayout(null);
         
         JButton btn1 = new JButton("추가");
         JTextField oder_ID= new JTextField(10);
         JTextField oder_menu = new JTextField(10);
         JLabel lab1 =new JLabel("주문 호실:");
         JLabel lab2 =new JLabel("메뉴 ID:");
         
        btn1.setBounds(182, 120, 172, 30);
        oder_ID.setBounds(182, 170, 172, 30);
        lab1.setBounds(120, 170, 60, 30);
        
        oder_menu.setBounds(182, 210, 172, 30);
         lab2.setBounds(120, 210, 60, 30);
        frmF_order.getContentPane().add(btn1);
        frmF_order.getContentPane().add(oder_ID);
        frmF_order.getContentPane().add(oder_menu);
        frmF_order.getContentPane().add(lab1);
        frmF_order.getContentPane().add(lab2);
        frmF_order.setSize(500,500);
        frmF_order.setLocationRelativeTo(null);
        frmF_order.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
         btn1.addActionListener(event -> {         
          int canFind = 0;
         String OrderRoomID= oder_ID.getText();
          int OrderMenuID = Integer.parseInt(oder_menu.getText());
         ReservedInfo roomID = new ReservedInfo(OrderRoomID);
         for(ReservedInfo temp : reserveSys.getReserveDB()){
              if(roomID.equals(temp) && temp.getCheck()){
                        roomID = temp;
                        canFind=2;
              }
              else if(roomID.equals(temp) && !temp.getCheck())   
                   canFind=1;              
         }
        switch (canFind) {
            case 0:
                JOptionPane.showMessageDialog(null, "주문자를 찾을 수 없습니다!");
                break;
            case 1:
                JOptionPane.showMessageDialog(null, "체크인 하지 않은 고객입니다!");
                System.out.print(roomID.getCheck());
                break;
                default:
                Food orderMenu = findMenu(OrderMenuID);
                if(orderMenu == null){
                    JOptionPane.showMessageDialog(null, "메뉴를 찾을 수 없습니다.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "주문 완료!");
                    roomID.addExtraFee(orderMenu.getPrice());
                    frmF_order.setVisible(false);
                    frmF.setVisible(true);
              try {
                  helper.writeDBFile(3,reserveSys.getReserveDB());
                  foodReport.addReport("order", orderMenu.getName()+";"+OrderRoomID+";"+Integer.toString(orderMenu.getPrice()));
              } catch (IOException ex) {
                  Logger.getLogger(FoodSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
                }   break;
             }
        });
        frmF_order.setVisible(true); 
    }
    
    public Food findMenu(int OrderMenuID){
            Food OrderMenu = new Food(OrderMenuID);
             //비교
             for(Food temp : foodDB){
                 if(OrderMenu.equals(temp)){
                    return temp;
                 }
             }
             return null;
    }

}
