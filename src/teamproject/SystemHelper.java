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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import teamproject.food.Food;
import teamproject.reservation.ReservedInfo;
import teamproject.report.Report;

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
    
    public long getDiffBetweenTwoDays(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay){
        LocalDate startDate = makeLocalDate(startYear, startMonth, startDay);
        LocalDate endDate = makeLocalDate(endYear, endMonth, endDay);
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return days;
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
        
        // 파일이 존재하지 않으면 파일 생성
       if(!Create_File.exists()){    
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
            case 4 : //로그 읽기
                File_Path = System.getProperty("user.dir") +  "\\src\\teamproject\\report\\Log.txt";
                break;
            default:
                return null;      
        }

        Read_File = new File(File_Path);
        BufferedReader Buf_reader = new BufferedReader(new FileReader(Read_File));
        String File_Contents = null;
        while((File_Contents = Buf_reader.readLine())!= null){
            readContext.add(File_Contents);
        }   
        return readContext;
    }  
    
    public void writeDBFile(int typeOfDB, Object DBList)throws IOException{
        String File_Path;
        FileWriter write;
        ArrayList<String> writeLine = new ArrayList<>();
        switch (typeOfDB) {
            case 2:
                File_Path = System.getProperty("user.dir") +  "\\src\\teamproject\\food\\Food.txt";
                write = new FileWriter(File_Path,false);
                ArrayList<Food> foodWriter = (ArrayList<Food>)DBList;
                for(Food temp : foodWriter){
                    writeLine.add(temp.getMenuID() + ";"+ temp.getName() + ";"+temp.getPrice()+"\n");
                }
                for(String writeContext : writeLine){
                    write.write(writeContext);
                }
                write.flush();
                write.close();
                break;
            case 3:
                File_Path = System.getProperty("user.dir") +  "\\src\\teamproject\\reservation\\Reservation.txt";
                write = new FileWriter(File_Path,false);
                ArrayList<ReservedInfo> reservationWriter = (ArrayList<ReservedInfo>)DBList;
                for(ReservedInfo temp : reservationWriter){
                    writeLine.add(temp.getRoomID() + ";"+temp.getReserverName()+ ";"+temp.getNumOfGuests()+";"+temp.getStartYear()+";"+temp.getStartMonth()+";"+temp.getStartDay()+";"+temp.getEndYear()+";"+temp.getEndMonth()+";"+temp.getEndDay()+";"+temp.getTotalRoomFee()+";"+temp.getExtraFee()+";"+temp.getCheck()+"\n");
                }
                for(String writeContext : writeLine){
                    write.write(writeContext);
                }
                write.flush();
                write.close();
                break;
            case 4:
                File_Path = System.getProperty("user.dir") +  "\\src\\teamproject\\report\\Log.txt";
                write = new FileWriter(File_Path,false);
                ArrayList<Report> reportWriter = (ArrayList<Report>)DBList;
                for(Report temp : reportWriter){
                    writeLine.add(temp.getReportType()+";"+temp.getReportData()+"\n");
                }
                for(String writeContext : writeLine){
                    write.write(writeContext);
                }
                write.flush();
                write.close();
                break;
        } 
    }  
}
