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
    
    public FoodSystem(ReservationSystem reserveSys) {
        this.reserveSys = reserveSys;
    }
    
    public void FoodSystemInit()throws IOException{
        foodDB = new ArrayList<>();
        helper = new SystemHelper();
        foodCount = 1;
        helper.createDBFile(2, "food");
        for(String readContext : helper.readDBFile(2)){
            Food temp= new Food(Integer.parseInt(readContext.split(";")[0]),readContext.split(";")[1],Integer.parseInt(readContext.split(";")[2]));
            foodDB.add(temp);
            foodCount++;
        }
    }
    
    public void addFood() throws IOException{
        System.out.print("메뉴 : ");
        String name;
        name =  helper.getUserInput();
        System.out.print("가격 : ");
        int price =Integer.parseInt(helper.getUserInput());
        
        Food newFood = new Food(foodCount,name,price);
        foodCount++;
        
        foodDB.add(newFood);
        helper.writeDBFile(2, foodDB);
    }
      
    public void showFood(){       
        
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
        boolean canFind = false;
        if(foodDB.isEmpty()){
             System.out.println("메뉴가 없습니다.");
             return;
         }
         if(reserveSys.showUsingReservation().isEmpty()){
            return;
        }
         
        System.out.print("주문 호실 : ");
       
        String OrderRoomID = helper.getUserInput();
        
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
            int OrderMenuID = Integer.parseInt(helper.getUserInput());

            Food orderMenu = findMenu(OrderMenuID);
            if(orderMenu == null){
                System.out.println("메뉴를 찾을 수 없습니다.");
            }
            else{
                System.out.println(orderMenu.getName() + " 주문 완료!");
                roomID.addExtraFee(orderMenu.getPrice());
            }
            
         }
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
    
    public void runFoodSystem() throws IOException{
        OUTER:
        while (true) {
            System.out.println("\n======================메뉴======================");
            System.out.println("1. 메뉴 보기");
            System.out.println("2. 메뉴 추가");
            System.out.println("3. 메뉴 주문");
            System.out.println("4. 메뉴 삭제");
            System.out.println("5. 뒤로 가기");
            System.out.println("===============================================");
            String selectedMenuS;
            do{
                selectedMenuS =  helper.getUserInput();
            }while(!helper.CheckFormat(selectedMenuS,"[1-5]"));
            int selectedMenu =Integer.parseInt(selectedMenuS);
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
                    break OUTER;
                
            }
        }  
    }
}
