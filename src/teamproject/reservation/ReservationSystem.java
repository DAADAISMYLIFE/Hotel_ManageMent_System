/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.reservation;

import java.io.IOException;
import java.util.ArrayList;
import teamproject.SystemHelper;
import teamproject.room.RoomSystem;
import teamproject.room.Room;
import teamproject.report.ReportSystem;
/**
 *
 * @author qkekd
 * 예약 정보를 관리하는 시스템
 */
public class ReservationSystem {
    private ArrayList<ReservedInfo> reserveDB;
    private SystemHelper helper;
    private RoomSystem RS;
    private ReportSystem ReserveReport;
    
    public ReservationSystem(ReportSystem reportSys){
        ReserveReport = reportSys;
    }
    
    public void ReserveSysInit() throws IOException{
        reserveDB = new ArrayList<>();
        helper = new SystemHelper();
        RS = new RoomSystem();
        RS.roomInit();
        helper.createDBFile(3, "reservation");
        for(String readContext : helper.readDBFile(3)){
            ReservedInfo temp= new ReservedInfo(readContext.split(";")[0],readContext.split(";")[1],Integer.parseInt(readContext.split(";")[2]),Integer.parseInt(readContext.split(";")[3]),Integer.parseInt(readContext.split(";")[4]),Integer.parseInt(readContext.split(";")[5]),Integer.parseInt(readContext.split(";")[6]),Integer.parseInt(readContext.split(";")[7]),Integer.parseInt(readContext.split(";")[8]),Integer.parseInt(readContext.split(";")[9]),Integer.parseInt(readContext.split(";")[10]));
            reserveDB.add(temp);
        }
    }
    
    public ArrayList<ReservedInfo> getReserveDB(){
        return this.reserveDB;
    }
    
    public void runReserSys() throws IOException{
        boolean continueReservations = true;

        while (continueReservations) {
            System.out.println("\n=====================예약=========================");
            System.out.println("1. 모든 객실 보기");
            System.out.println("2. 예약 현황 보기 ");
            System.out.println("3. 예약 추가하기 ");
            System.out.println("4. 예약 삭제하기");
            System.out.println("5. 나가기");
            System.out.println("===================================================");
            int choose = Integer.parseInt(helper.getUserInput("[1-5]"));
            switch (choose) {
                case 1:
                    RS.showAllRoom();
                    break;
                case 2:
                    showAllReservation();
                    break;
                case 3:
                    addReservation();
                    break;
                case 4:
                    deleteReservation();
                    break;
                case 5:
                    continueReservations = false;
                    break;
            }
        }
    }
    
    public void addReservation()throws IOException{        
        //예약 정보 입력
        int startYear;
        int startMonth;
        int startDay;
        int endYear;
        int endMonth;
        int endDay;
        
         int startDateI;
         int endDateI;
         
         int days;
         
         ArrayList<Room> canReserveRoom = new ArrayList<>();
        
        boolean isCorrect = false;
        do{
            System.out.print("체크인 날짜를 입력해 주세요('/' 구분) : ");
            String startDate = helper.getUserInput("^20\\d{2}/\\d{1,2}/\\d{1,2}$");
            startYear = Integer.parseInt(startDate.split("/")[0]);
            startMonth = Integer.parseInt(startDate.split("/")[1]);
            startDay = Integer.parseInt(startDate.split("/")[2]);

            System.out.print("체크아웃 날짜를 입력해 주세요('/' 구분) : ");
            String endDate = helper.getUserInput("^20\\d{2}/\\d{1,2}/\\d{1,2}$");
            endYear = Integer.parseInt(endDate.split("/")[0]);
            endMonth = Integer.parseInt(endDate.split("/")[1]);
            endDay = Integer.parseInt(endDate.split("/")[2]);
            
            
            startDateI = startYear*10000 + startMonth*100 + startDay; 
            endDateI = endYear*10000 + endMonth*100 + endDay; 
            days = helper.getDiffBetweenTwoDays(startYear, startMonth, startDay, endYear, endMonth, endDay);
            if(startMonth > 12 || endMonth > 12 || startDay > helper.getLastDayOfMonth(startYear, startMonth) || endDay > helper.getLastDayOfMonth(endYear, endMonth)){
                System.out.println("잘못된 날짜 입력!");
            }
            else if(helper.getTodayDateI() >startDateI){
                System.out.println("예약 날짜를 오늘 이후로 해주세요");
            }
            else if(endDateI<=startDateI){
                System.out.println("잘못된 날짜 입력!");
            }
            else{
                if(endDateI>startDateI){
                    System.out.format("%d/%d/%d ~ %d/%d/%d (%d일)\n",startYear,startMonth,startDay,endYear,endMonth,endDay,days);
                    
                    isCorrect = true;
                }
            }
        }while(!isCorrect);
        
        System.out.print("예약 인원을 입력해 주세요 : ");
        int numOfGuests = Integer.parseInt(helper.getUserInput("[0-9]"));
        
       
        //설정된 날짜동안 가능한 방 보여 주기
        System.out.print("예약 가능한 방들의 목록을 표시할까요?[y/n] : ");
        boolean canPrint = false;
        if(helper.getUserInput("[y,n]").equals("y")){
            canPrint = true;
        }
        showAvaliableRooms(canReserveRoom,startDateI,endDateI,numOfGuests,canPrint);

        boolean canReserve = false;
        int maximunGuests = 0;
        int costPerNight = 0;
         String roomID;
        do{
            System.out.print("방 번호를 선택해 주세요 : ");
            roomID = helper.getUserInput("[0-1]{0,1}[0-9][0-1][0-9]");
            //예약 가능한 방에 포함되지 않으면 오류
            for(Room temp : canReserveRoom){
                if(temp.getRoomNumber().equals(roomID)){
                    canReserve = true;
                    maximunGuests = temp.getNumberOfGuests();
                    costPerNight = temp.getPricePerNight();
                    break;
                }
            }
            if(!canReserve){
                System.out.println("예약 불가능한 방 입니다.");
                if(!canPrint){
                    System.out.print("예약 가능한 방들의 목록을 표시할까요?[y/n] : ");
                    if(helper.getUserInput("[y,n]").equals("y")){
                        canPrint = true;
                    }
                    showAvaliableRooms(canReserveRoom,startDateI,endDateI,numOfGuests,canPrint);
                }
            }
        }while(!canReserve);
             
        if(numOfGuests>maximunGuests){
            System.out.println("정원 초과 입니다.");
            return;
        }
        
        System.out.print("예약자 이름을 입력해 주세요 : ");
        String reserverName = helper.getUserInput();
        
        ReservedInfo temp = new ReservedInfo(roomID,reserverName,numOfGuests,startYear,startMonth,startDay,endYear,endMonth,endDay);
        temp.setTotalRoomFee(days * costPerNight);
        reserveDB.add(temp);
        helper.writeDBFile(3, reserveDB);
        
        //Log.txt에 예약 내역 기록
        ReserveReport.addReport( 3,reserverName + ";"+ roomID + ";");
    }
    
    public void showAllReservation() throws IOException{
        if(reserveDB.isEmpty()){
            System.out.println("예약 현황이 없습니다.");
        }
        else{
            System.out.println("\n=============================================================================예약=============================================================================");
            for(ReservedInfo temp : reserveDB){
                System.out.format("호실 : %4s\t\t예약자 : %-20s숙박 인원 : %-10d예약기간 : %d/%02d/%02d ~ %d/%02d/%02d\t\t숙박 비용 : %7d\t\t추가 금액 : %d\n",temp.getRoomID(),temp.getReserverName(),temp.getNumOfGuests(),temp.getStartYear(),temp.getStartMonth(),temp.getStartDay(),temp.getEndYear(),temp.getEndMonth(),temp.getEndDay(),temp.getTotalRoomFee(),temp.getExtraFee());
            }
            System.out.println("=============================================================================================================================================================");
        } 
    }    
    
     public ArrayList<String> showUsingReservation() throws IOException{
         
        ArrayList<String> id = new ArrayList<>();
        if(reserveDB.isEmpty()){
            System.out.println("예약 현황이 없습니다.");
        }
        else{
            System.out.println("\n==========================================================예약==========================================================");
            for(ReservedInfo temp : reserveDB){
                if(temp.getStartDateI() <= helper.getTodayDateI()){
                     System.out.format("호실 : %4s\t\t예약자 : %-20s예약기간 : %d/%02d/%02d ~ %d/%02d/%02d\n",temp.getRoomID(),temp.getReserverName(),temp.getStartYear(),temp.getStartMonth(),temp.getStartDay(),temp.getEndYear(),temp.getEndMonth(),temp.getEndDay());
                     id.add(temp.getRoomID());
                }
            }
            System.out.println("==========================================================================================================================");
        } 
        return id;
    }  
    
    public void showAvaliableRooms(ArrayList<Room> canReserveRoom,int startDateI, int endDateI,int reserveGuests,boolean canPrint) throws IOException{
        for(int i = 0; i < 100; i++){
            
            boolean canShow = true;
            
            if(reserveGuests > RS.roomDB.get(i).getNumberOfGuests()){
                canShow = false;
            }
            
            
            for(ReservedInfo temp : reserveDB){
                if(temp.getRoomID().equals(RS.roomDB.get(i).getRoomNumber())){
                    int startDateT = temp.getStartYear()*10000 + temp.getStartMonth()*100 + temp.getStartDay(); 
                    int endDateT = temp.getEndYear()*10000 + temp.getEndMonth()*100 + temp.getEndDay();
                    
                    
                    if((startDateI>=startDateT && startDateI < endDateT) ||(endDateI>startDateT && endDateI <= endDateT)|| (startDateI <= startDateT && endDateI >= endDateT))
                         canShow = false;
                }     
            }
            if(canShow){
                //예약 가능한 방 리스트
                canReserveRoom.add(RS.roomDB.get(i));
                if(canPrint)
                    RS.showRoom(i);
            }
        }
    }
    
    public void deleteReservation() throws IOException{
        showAllReservation();
        if(reserveDB.isEmpty())
            return;
        
        System.out.print("삭제할 방의 번호를 입력해 주세요 : ");
        String roomID = helper.getUserInput("[0-1]{0,1}[0-9][0-1][0-9]");
        System.out.print("예약자 이름을 입력해 주세요 : ");
        String reserverName = helper.getUserInput();
        System.out.print("체크인 날짜를 입력해 주세요('/' 구분) : ");
        String startDate = helper.getUserInput("^20\\d{2}/\\d{1,2}/\\d{1,2}$");
        int checkinYear = Integer.parseInt(startDate.split("/")[0]);
        int checkinMonth = Integer.parseInt(startDate.split("/")[1]);
        int checkinDay = Integer.parseInt(startDate.split("/")[2]);
        int checkinDateI = checkinYear*10000 + checkinMonth*100 + checkinDay;
        
        for(ReservedInfo temp : reserveDB){
            if(temp.getRoomID().equals(roomID) && temp.getReserverName().equals(reserverName) && temp.getStartDateI() == checkinDateI){
                reserveDB.remove(temp);
                helper.writeDBFile(3, reserveDB);
                break;
            }
        }
    }
    
}
