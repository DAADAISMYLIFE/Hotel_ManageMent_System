/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.food;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import teamproject.SystemHelper;
import teamproject.reservation.ReservedInfo;

/**
 *
 * @author qkekd
 */
public class FoodSystem {
    private ArrayList<Food> foodDB; // 식품 관련 정보(메뉴 이름, 가격)
    private SystemHelper helper;
    
    public FoodSystem(){
        foodDB = new ArrayList<>();
        helper = new SystemHelper();
    }
    
    public void addFood() throws IOException{
        System.out.print("메뉴 : ");
        String name;
            do{
               name =  helper.getUserInput();
             }while(!helper.CheckFormat(name,"[a-zA-Z]+"));
        System.out.print("가격 : ");
        int price =Integer.parseInt(helper.getUserInput());
        
        Food newFood = new Food(name,price);
        
        foodDB.add(newFood);
    }
      
    public void showFood(){       
        
        if(foodDB.isEmpty()){
            System.out.println("메뉴가 아직 없습니다. 메뉴를 추가해 주세요");
        }
        else{
            //음식 목록
            for(int i = 0;i < foodDB.size(); i++){
                System.out.printf("%02d   이름 : %s\t\t가격  :  %d원\n", i+1, foodDB.get(i).getName(), foodDB.get(i).getPrice());
            }
        }
    }
    
    public int OrderFood() throws IOException{
        System.out.print("호실: ");
        String OrderID = helper.getUserInput();
        //TODO 주문한 예약자 
        
        System.out.print("메뉴: ");
        String OrderMenu = helper.getUserInput();
        
         //비교를 위한 객체 생성
           Food OrderTrying = new Food(OrderMenu,10);
            
            //비교
            for(Food temp : foodDB){
                if(OrderTrying.equals(temp)){
                   OrderTrying = temp;
                    System.out.println(OrderTrying.getName()+ " 주문 완료");
                    return 0;
                }
            }           
                        
            String str = OrderMenu;
           System.out.println(str+ " 주문 실패");
            return 0;
    }
    
    public void runF(){
         JFrame frmF = new JFrame();
         
         frmF.getContentPane().setLayout(null);
         
         JButton btn1 = new JButton("메뉴 보기");
        JButton btn2 = new JButton("음식 추가");
         JButton btn3 = new JButton("음식 주문");
         JButton btn4 = new JButton("뒤로가기");

        btn1.setBounds(182, 120, 172, 30);
        btn2.setBounds(182, 170, 172, 30);
        btn3.setBounds(182, 220, 172, 30);
        btn4.setBounds(182, 270, 172, 30);
        
        frmF.getContentPane().add(btn1);
        frmF.getContentPane().add(btn2);
        frmF.getContentPane().add(btn3);
        frmF.getContentPane().add(btn4);
              
        frmF.setSize(500,500);
        frmF.setLocationRelativeTo(null);
        frmF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        btn4.addActionListener(event -> {
            System.out.println("시스템을 종료합니다.");
            frmF.setVisible(false);
        });
        
        frmF.setVisible(true);
    }
    public void runFoodSystem() throws IOException{
        runF();
//        while(true){
//            System.out.println("======================식품======================");
//            System.out.println("1. 메뉴 보기");
//            System.out.println("2. 음식 추가");
//            System.out.println("3. 음식 주문");
//            System.out.println("4. 뒤로 가기");
//            System.out.println("===============================================");
//
//            String selectedMenuS;
//            do{
//               selectedMenuS =  helper.getUserInput();
//             }while(!helper.CheckFormat(selectedMenuS,"[1-4]"));
//            int selectedMenu =Integer.parseInt(selectedMenuS);
//
//            if(selectedMenu == 1){
//                showFood();
//            }
//            else if(selectedMenu == 2){
//                addFood();
//            }
//            else if(selectedMenu==3){
//                OrderFood();
//            }
//             else if(selectedMenu==4){
//                break;
//            }
//        }  
    }
}
