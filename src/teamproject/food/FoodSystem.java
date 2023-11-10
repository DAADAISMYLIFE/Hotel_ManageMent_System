/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.food;

import java.io.IOException;
import java.util.ArrayList;
import teamproject.SystemHelper;
import teamproject.reservation.ReservationSystem;
import teamproject.reservation.ReservedInfo;

/**
 *
 * @author qkekd
 */
public class FoodSystem {
    private ArrayList<Food> foodDB; // 식품 관련 정보(메뉴 이름, 가격)
    private ReservationSystem reserveSys;
    private SystemHelper helper;
    private int foodCount;
    
    public FoodSystem(ReservationSystem reserveSys){
        foodDB = new ArrayList<>();
        helper = new SystemHelper();
        this.reserveSys = reserveSys;
        foodCount = 1;
    }
    
    public void addFood() throws IOException{
        System.out.print("메뉴 : ");
        String name;
            do{
               name =  helper.getUserInput();
             }while(!helper.CheckFormat(name,"[a-zA-Z]+"));
        System.out.print("가격 : ");
        int price =Integer.parseInt(helper.getUserInput());
        
        Food newFood = new Food(foodCount,name,price);
        foodCount++;
        
        foodDB.add(newFood);
    }
      
    public void showFood(){       
        
        if(foodDB.isEmpty()){
            System.out.println("메뉴가 아직 없습니다. 메뉴를 추가해 주세요");
        }
        else{
            //음식 목록
            for(int i = 0;i < foodDB.size(); i++){
                System.out.printf("%02d   이름 : %s\t\t가격  :  %d원\n", foodDB.get(i).getMenuID(), foodDB.get(i).getName(), foodDB.get(i).getPrice());
            }
        }
    }
    
    public void OrderFood() throws IOException{
        boolean canFind = false;
        reserveSys.showAllReservation();        
        System.out.print("호실: ");
        String OrderRoomID = helper.getUserInput();
        
         //비교를 위한 객체 생성
         ReservedInfo roomID = new ReservedInfo(OrderRoomID);
         //비교
         for(ReservedInfo temp : reserveSys.getReserveDB()){
             if(roomID.equals(temp)){
                roomID = temp;
                 System.out.println(roomID.getRoomID()+ "번 방의 " + roomID.getReserverName()+"님께 주문합니다.");
                 canFind = true;
             }
         }
         if(!canFind){
             System.out.println("주문자를 찾을 수 없습니다!");
         }
         else{
            canFind = false;
            showFood();
            System.out.print("메뉴 ID: ");
            int OrderMenuID = Integer.parseInt(helper.getUserInput());

             //비교를 위한 객체 생성
            Food OrderMenu = new Food(OrderMenuID);
             //비교
             for(Food temp : foodDB){
                 if(OrderMenu.equals(temp)){
                    OrderMenu = temp;
                     System.out.println(OrderMenu.getName()+ " 주문 완료");
                     roomID.addExtraFee(OrderMenu.getPrice());
                     canFind = true;
                 }
             }
             if(!canFind)
                System.out.println("메뉴를 찾을 수 없습니다.");
         }
    }
    
    public void runFoodSystem() throws IOException{
        OUTER:
        while (true) {
            System.out.println("======================식품======================");
            System.out.println("1. 메뉴 보기");
            System.out.println("2. 음식 추가");
            System.out.println("3. 음식 주문");
            System.out.println("4. 뒤로 가기");
            System.out.println("===============================================");
            String selectedMenuS;
            do{
                selectedMenuS =  helper.getUserInput();
            }while(!helper.CheckFormat(selectedMenuS,"[1-4]"));
            int selectedMenu =Integer.parseInt(selectedMenuS);
            switch (selectedMenu) {
                case 1:
                    showFood();
                    break;
                case 2:
                    addFood();
                    break;
                case 3:
                    OrderFood();
                    break;
                case 4:
                    break OUTER;
                default:
                    break;
            }
        }  
    }
}
