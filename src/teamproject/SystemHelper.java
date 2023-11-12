/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import teamproject.login.User;

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
    
    private LocalDate makeLocalDate(int year, int month, int day){
        String yearS = String.format("%04d", year);
        String monthS = String.format("%02d",month);
        String dayS = String.format("%02d",day);
        String reseveDate = yearS +"-" +monthS+"-" + dayS;
        CharSequence rd = new StringBuffer(reseveDate);
        LocalDate reserveLocalDateLocal = LocalDate.parse(rd);
        
        return reserveLocalDateLocal;
    }
    public int getLastDayOfMonth(int year, int month) {
        LocalDate reserveLocalDateLocal = makeLocalDate(year, month, 1);
        lastDayOfMonth = reserveLocalDateLocal.withDayOfMonth(reserveLocalDateLocal.lengthOfMonth()).getDayOfMonth();
        return lastDayOfMonth;
    }
    
    public int getDiffBetweenTwoDays(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay){
        LocalDate startDate = makeLocalDate(startYear, startMonth, startDay);
        LocalDate endDate = makeLocalDate(endYear, endMonth, endDay);
        Period diff = Period.between(startDate,endDate);
        return diff.getDays();
    }
    
    public int getTodayDateI() {
        return todayDateI;
    }
    
    public void createDBFile(int typeOfDB, String path) throws IOException{
        String File_Path  = System.getProperty("user.dir") + "\\src\\teamproject\\"+path+"\\"; //파일 경로
        String File_Name;
        
        switch (typeOfDB) {
            case 0:
                File_Name = "User.txt";
                break;
            case 1:
                File_Name = "Room.txt";
                break;
            case 2:
                File_Name = "Food.txt";
                break;
            case 3:
                File_Name = "Reservation.txt";
                break;
            case 4:
                File_Name = "Log.txt";
                break;
            default:
                return;
        }
        
        File_Path = File_Path + File_Name;
        File Create_File = new File(File_Path);//File 객체 생성
        
       //파일 관련 추가 코드
       if(!Create_File.exists()){    // 파일이 존재하지 않으면, 파일 만들고 직원들 기록
            System.out.println("File is not exist.");
            //파일 셍성, 파일 생성하려면 관리자 권한으로 실행해야 함
            Create_File.createNewFile();
        }
    }
    
    public ArrayList<String> readDBFile(int typeOfDB)throws IOException{
        String File_Path;
        File Read_File;
        
        ArrayList<String> readContext = new ArrayList<>();
        switch (typeOfDB) {
            case 0:
                File_Path = System.getProperty("user.dir") +  "\\src\\teamproject\\login\\User.txt";
                break;
            case 1:
                File_Path = System.getProperty("user.dir") +  "\\src\\teamproject\\room\\Room.txt";
                break;
            case 2:
                File_Path = System.getProperty("user.dir") +  "\\src\\teamproject\\food\\Food.txt";
                break;
            case 3:
                File_Path = System.getProperty("user.dir") +  "\\src\\teamproject\\reservation\\Reservation.txt";
                break;
            case 4:
                File_Path = System.getProperty("user.dir") +  "\\src\\teamproject\\room\\Room.txt";
                break;
            default:
                return null;      
        }

        Read_File = new File(File_Path);
        BufferedReader Buf_reader = new BufferedReader(new FileReader(Read_File));
        //읽은거 출력
        String File_Contents = null;
        while((File_Contents = Buf_reader.readLine())!= null){
            readContext.add(File_Contents);
        }   
        return readContext;
    }  
}
