/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

/**
 *
 * @author qkekd
 */
public class SystemHelper {
     // 현재 날짜 구하기 (시스템 시계)
        LocalDate now = LocalDate.now();         // 연도, 월(문자열, 숫자), 일, 일(year 기준), 요일(문자열, 숫자)
        int year = now.getYear();                                       // 연도
        int monthValue = now.getMonthValue();                    // 달(숫자)
        int dayOfMonth = now.getDayOfMonth();                  // 해당 달의 날짜
        int lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth()).getDayOfMonth();
        int todayDateI = year*10000 + monthValue*100 + dayOfMonth;
    
    
    
    
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
            System.out.print("올바른 값을 입력해 주세요 : ");
            return false;
        }
    }
    
    public void showTodayDate(){       
        System.out.println("현재 날짜 : " + now);
    }

    public int getYear() {
        return year;
    }

    public int getMonthValue() {
        return monthValue;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getLastDayOfMonth(int year, int month) {
        String yearS = String.format("%04d", year);
        String monthS = String.format("%02d",month);
        String reseveDate = yearS +"-" +monthS+"-01";
        CharSequence rd = new StringBuffer(reseveDate);
        LocalDate reserveLocalDateLocal = LocalDate.parse(rd);
        lastDayOfMonth = reserveLocalDateLocal.withDayOfMonth(reserveLocalDateLocal.lengthOfMonth()).getDayOfMonth();
        return lastDayOfMonth;
    }
    
    public int getTodayDateI() {
        return todayDateI;
    }
    
    
    
    
    
  
}
