/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.food;

import java.io.IOException;
import java.util.ArrayList;
import teamproject.SystemHelper;

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
    
    public void runFoodSystem() throws IOException{
        while(true){
            System.out.println("======================식품======================");
            System.out.println("1. 메뉴 보기");
            System.out.println("2. 음식 추가");
            System.out.println("3. 뒤로 가기");
            System.out.println("===============================================");

            String selectedMenuS;
            do{
               selectedMenuS =  helper.getUserInput();
             }while(!helper.CheckFormat(selectedMenuS,"[1-3]"));
            int selectedMenu =Integer.parseInt(selectedMenuS);

            if(selectedMenu == 1){
                showFood();
            }
            else if(selectedMenu == 2){
                addFood();
            }
            else if(selectedMenu==3){
                break;
            }
        }  
    }
}
