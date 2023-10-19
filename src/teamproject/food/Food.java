/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.food;

/**
 *
 * @author qkekd
 */
public class Food {
    private String name;
    private int price;

    public Food(String name, int price) {
        this.name = name;
        this.price = price;
    }
    public Food(String name) {
        this(name,0);
    }
    
    public Food() {
        this("",0);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
     public boolean equals(Object object){
        Food food = (Food) object;
        if(food.getName().equals(this.getName())){
            return true;
        }
        else
            return false;
     }
     
}
