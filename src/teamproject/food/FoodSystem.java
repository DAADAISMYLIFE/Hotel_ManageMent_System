/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.food;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import teamproject.SystemHelper;
import teamproject.reservation.ReservationSystem;
import teamproject.reservation.ReservedInfo;
import javax.swing.*;
import teamproject.IntegrateManager;
/**
 *
 * @author qkekd
 */
public class FoodSystem {
    private ArrayList<Food> foodDB; // 식품 관련 정보(메뉴 이름, 가격)
    private ReservationSystem reserveSys;
    private SystemHelper helper;
    private int foodCount;
    private int selectedMenu;
    boolean canFind = false;
    
    
    public void showFoodFrame(){
        JFrame F_Showfrm = new JFrame();
        DefaultListModel<String> F_List =new DefaultListModel<>();
        for(int i = 0;i < foodDB.size(); i++){
                String temp = "메뉴 ID: "+foodDB.get(i).getMenuID()+" |이름: "+foodDB.get(i).getName()+" | 가격: "+foodDB.get(i).getPrice();
                F_List.addElement(temp);
            }
        JList<String> ls = new JList<>(F_List);
        JButton B = new JButton("확인");
        
        B.setBounds(400, 400, 70, 40);
        ls.setBounds(120, 120, 350, 100);
        
        F_Showfrm.add(B);
        F_Showfrm.add(ls);
        
        B.addActionListener(event -> {          
            F_Showfrm.setVisible(false);
        });
        
        F_Showfrm.setSize(500,500);
        F_Showfrm.setLayout(null);
        F_Showfrm.setVisible(true);
        F_Showfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public FoodSystem(ReservationSystem reserveSys){
        foodDB = new ArrayList<>();
        helper = new SystemHelper();
        this.reserveSys = reserveSys;
        foodCount = 1;
    }
    
    public void addFood() throws IOException{
        System.out.print("메뉴 : ");
    
        //name =  helper.getUserInput();
        System.out.print("가격 : ");
       // int price =Integer.parseInt(helper.getUserInput());
        
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
        });
        addF.setVisible(true);

    }
      
    public void showFood(){       
        showFoodFrame();
        
        if(foodDB.isEmpty()){
            System.out.println("메뉴가 아직 없습니다. 메뉴를 추가해 주세요");
        }
        else{
            System.out.println("\n==============================================================================================");
            //음식 목록
            for(int i = 0;i < foodDB.size(); i++){
                System.out.printf("메뉴 ID : %02d   이름 : %s\t\t가격  :  %d원\n", foodDB.get(i).getMenuID(), foodDB.get(i).getName(), foodDB.get(i).getPrice());
            }
             System.out.println("==============================================================================================");
        }
    }
    
    public void orderFood() throws IOException{
        if(foodDB.isEmpty()){
             System.out.println("메뉴가 없습니다.");
             return;
         }
         if(reserveSys.showUsingReservation().isEmpty()){
            return;
        }
         
        System.out.print("주문 호실 : ");
       
         JFrame addF = new JFrame();
         addF.getContentPane().setLayout(null);
         
         JButton btn1 = new JButton("확인");
         JTextField Order_RoomID = new JTextField(10);
         JTextField Order_Menu = new JTextField(10);
         
        btn1.setBounds(182, 120, 172, 30);
        Order_RoomID.setBounds(182, 170, 172, 30);
        Order_Menu.setBounds(182, 210, 172, 30);
        
        addF.getContentPane().add(btn1);
        addF.getContentPane().add(Order_RoomID);
        addF.getContentPane().add(Order_Menu);
              
        addF.setSize(500,500);
        addF.setLocationRelativeTo(null);
        addF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
         btn1.addActionListener(event -> {          
              String OrderRoomID = Order_RoomID.getText();
         //비교를 위한 객체 생성
         ReservedInfo roomID = new ReservedInfo(OrderRoomID);
         for(ReservedInfo temp : reserveSys.getReserveDB()){
              if(roomID.equals(temp,helper.getTodayDateI())){
                 roomID = temp;
                 System.out.println(roomID.getRoomID()+ "번 방의 " + roomID.getReserverName()+"님께 주문합니다.");
                 canFind = true;
               }
         }
         if(!canFind){
             System.out.println("주문자를 찾을 수 없습니다!");
         }
         else{
            showFood();
            System.out.print("메뉴 ID를 입력해 주세요 : ");
            String temp = Order_Menu.getText();
            int OrderMenuID = Integer.parseInt(temp);

            Food orderMenu = findMenu(OrderMenuID);
            if(orderMenu == null){
                System.out.println("메뉴를 찾을 수 없습니다.");
            }
            else{
                System.out.println(orderMenu.getName() + " 주문 완료!");
                roomID.addExtraFee(orderMenu.getPrice());
            }
         }
        });//버튼 이벤트 끝
        addF.setVisible(true);
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
    
    public void deleteMenu(){
    }
    
    public void runF() {
         JFrame frmF = new JFrame();
         
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
        frmF.getContentPane().add(btn3);
        frmF.getContentPane().add(btn4);
        frmF.getContentPane().add(btn5);
              
        frmF.setSize(500,500);
        frmF.setLocationRelativeTo(null);
        frmF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
         btn1.addActionListener(event -> {          
            showFood();
        });
         btn2.addActionListener(event -> {          
             try {
                 addFood();
             } catch (IOException ex) {
                 Logger.getLogger(FoodSystem.class.getName()).log(Level.SEVERE, null, ex);
             }
        });
         btn3.addActionListener(event -> {          
             try {
                 orderFood();
             } catch (IOException ex) {
                 Logger.getLogger(FoodSystem.class.getName()).log(Level.SEVERE, null, ex);
             }
        });
         btn4.addActionListener(event -> {          
            showFood();
        });
        btn5.addActionListener(event -> {
            System.out.println("식품 시스템 종료.");
            frmF.setVisible(false);
            IntegrateManager.frm.setVisible(true);
        });
        
        frmF.setVisible(true);
    }
    
    public void runFoodSystem() throws IOException{
        runF();
        
            System.out.println("\n======================메뉴======================");
            System.out.println("1. 메뉴 보기");
            System.out.println("2. 메뉴 추가");
            System.out.println("3. 메뉴 주문");
            System.out.println("4. 메뉴 삭제");
            System.out.println("5. 뒤로 가기");
            System.out.println("===============================================");
            String selectedMenuS;
//            do{
//                selectedMenuS =  helper.getUserInput();
//            }while(!helper.CheckFormat(selectedMenuS,"[1-5]"));
//            int selectedMenu =Integer.parseInt(selectedMenuS);
            switch (selectedMenu) {
                case 1:
                    showFood();
                    break;
                case 2:
                    addFood();
                    break;
                case 3:
                    orderFood();
                case 4:
                    deleteMenu();
                    break;
                case 5:
                    break;
            }//swich 종료
    }//runFoodSystem() 종료
}//클래스 종료
