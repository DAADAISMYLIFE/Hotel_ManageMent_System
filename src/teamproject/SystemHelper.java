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
    
    public boolean CheckFormat(String input, String rex){
        if(input.matches(rex))
            return true;
        else
            return false;
    }
}
