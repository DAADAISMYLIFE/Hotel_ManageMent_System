/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package teamproject;
import java.time.LocalDate;

/**
 *
 * @author 이승환
 */
public class Time {
       // 현재 날짜 구하기 (시스템 시계)
       LocalDate now = LocalDate.now();         // 연도, 월(문자열, 숫자), 일, 일(year 기준), 요일(문자열, 숫자)
       int year = now.getYear();                                       // 연도
       String month = now.getMonth().toString();                 // 달(문자)
       int monthValue = now.getMonthValue();                    // 달(숫자)
       int dayOfMonth = now.getDayOfMonth();                  // 해당 달의 날짜
       int dayOfYear = now.getDayOfYear();                        // 310일
       String dayOfWeek = now.getDayOfWeek().toString();     // 월요일 = 1, 화요일 = 2, 수요일 = 3....
       int dayOfWeekValue = now.getDayOfWeek().getValue();         // 결과 출력   
       
       public void showCalendar(){       
       System.out.println(now); // 2023-11-06        
       System.out.println(year); // 2023       
       System.out.println(monthValue); // 11        
       System.out.println(dayOfMonth); // 06
       System.out.printf("%04d-%02d-%02d%n", year, monthValue, dayOfMonth);
       //System.out.println(dayOfYear); // 310 
       //System.out.println(dayOfWeek + "(" + dayOfWeekValue + ")"); // THURSDAY(4) 
       };
       
//       public static void main(String[] args){
//       Time calendar = new Time();
//       calendar.showCalendar();
//       }
}


