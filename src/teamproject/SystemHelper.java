/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author qkekd
 */
public class SystemHelper {
    
    public String getUserInput() throws IOException{
        String input;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        input = br.readLine();
        return input;
    }
    
    public String getUserInput(String rex) throws IOException{
        String input;
        boolean isCorrect = false;
        do{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            input = br.readLine();
            if(input.matches(rex))
                isCorrect = true;
            else{
                System.out.println("올바른 값을 입력해 주세요");
            }
        }while(!isCorrect);
        return input;
    }
    
    public boolean CheckFormat(String input, String rex){
        if(input.matches(rex))
            return true;
        else{
            System.out.println("올바른 값을 입력해 주세요");
            return false;
        }
    }
  
}
