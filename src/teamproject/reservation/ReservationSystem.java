/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.reservation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import teamproject.SystemHelper;
import teamproject.room.RoomSystem;
import teamproject.room.Room;
import teamproject.report.ReportSystem;
import javax.swing.*;
import teamproject.IntegrateManager;
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
         JFrame frmR = new JFrame();
         JFrame A_ShowRoom = new JFrame();
         int numOfGuests=0;
         int startYear;
         int startMonth;
         int startDay;
         int endYear;
         int endMonth;
         int endDay;
         int startDateI;
         int endDateI;
         long days = 0;
         ArrayList<Room> canReserveRoom = new ArrayList<>();
         boolean isCorrect = false;
    
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
            ReservedInfo temp= new ReservedInfo(readContext.split(";")[0],readContext.split(";")[1],Integer.parseInt(readContext.split(";")[2]),Integer.parseInt(readContext.split(";")[3]),Integer.parseInt(readContext.split(";")[4]),Integer.parseInt(readContext.split(";")[5]),Integer.parseInt(readContext.split(";")[6]),Integer.parseInt(readContext.split(";")[7]),Integer.parseInt(readContext.split(";")[8]),Integer.parseInt(readContext.split(";")[9]),Integer.parseInt(readContext.split(";")[10]),Boolean.parseBoolean(readContext.split(";")[11]));
            reserveDB.add(temp);
        }
    }
    public ArrayList<ReservedInfo> getReserveDB(){
        return this.reserveDB;
    }
    
    
     public void runR(){
         frmR.getContentPane().setLayout(null);
         
         JButton btn1 = new JButton("모든 객실 보기");
         JButton btn2 = new JButton("예약 현황 보기");
         JButton btn3 = new JButton("예약 추가하기");
         JButton btn4 = new JButton("예약 삭제하기");
         JButton btn5 = new JButton("체크인 하기");
         JButton btn6 = new JButton("체크아웃 하기");
         JButton btn7 = new JButton("나가기");
         
        btn1.setBounds(182, 120, 172, 30);
        btn2.setBounds(182, 170, 172, 30);
        btn3.setBounds(182, 220, 172, 30);
        btn4.setBounds(182, 270, 172, 30);
         btn5.setBounds(182, 320, 172, 30);
        btn6.setBounds(182, 370, 172, 30);
        btn7.setBounds(182, 420, 172, 30);
        
        frmR.getContentPane().add(btn1);
        frmR.getContentPane().add(btn2);
        frmR.getContentPane().add(btn3);
        frmR.getContentPane().add(btn4);
        frmR.getContentPane().add(btn5);
        frmR.getContentPane().add(btn6);
        frmR.getContentPane().add(btn7);
              
        frmR.setSize(800,800);
        frmR.setLocationRelativeTo(null);
        frmR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        btn1.addActionListener(event -> { //모든 객실 보기 버튼
            
            frmR.setVisible(false);
        });
        btn2.addActionListener(event -> { //예약 현황보기 버튼
           
            frmR.setVisible(false);
        });
         btn3.addActionListener(event -> { try {
             //예약 추가하기
             addReservation();
             } catch (IOException ex) {
                 Logger.getLogger(ReservationSystem.class.getName()).log(Level.SEVERE, null, ex);
             }
            frmR.setVisible(false);
        });
        btn7.addActionListener(event -> {//나나기
           
            frmR.setVisible(false);
            IntegrateManager.frm.setVisible(true);
        });
        
        frmR.setVisible(true);
    }
    public void runReserSys() throws IOException{
        boolean continueReservations = true;

        while (continueReservations) {
            System.out.println("\n=====================예약=========================");
            System.out.println("1. 모든 객실 보기");
            System.out.println("2. 예약 현황 보기 ");
            System.out.println("3. 예약 추가하기 ");
            System.out.println("4. 예약 삭제하기");
            System.out.println("5. 체크인 하기 ");
            System.out.println("6. 체크아웃 하기");
            System.out.println("0. 나가기");
            System.out.println("===================================================");
            int choose = Integer.parseInt(helper.getUserInput("[0-6]"));
            switch (choose) {
                case 1:
                    RS.showAllRoom();
                    break;
                case 2:
                    showAllReservation(0);
                    break;
                case 3:
                    addReservation();
                    break;
                case 4:
                    deleteReservation();
                    break;
                case 5:
                    checkIn(); 
                    break;
                case 6:
                    checkOut();
                    break;
                case 0:
                    continueReservations = false;
                    break;
            }
        }
    }
    
    public void addReservation()throws IOException{        
      frmR_add();
    }
    
    public void frmR_add(){
         JFrame frmR_add = new JFrame();
        frmR_add.getContentPane().setLayout(null);
        
        JLabel lbl = new JLabel();
        lbl.setBounds(100, 170, 150, 30);
        lbl.setText("체크인 날짜: ");
        frmR_add.getContentPane().add(lbl);
         JLabel lbl2 = new JLabel();
        lbl2.setBounds(90, 210, 150, 30);
        lbl2.setText("체크아웃 날짜: ");
        frmR_add.getContentPane().add(lbl2);
         JLabel lbl3 = new JLabel();
        lbl3.setBounds(110, 250, 150, 30);
        lbl3.setText("예약 인원: ");
        frmR_add.getContentPane().add(lbl3);
        
        JButton btn1 = new JButton("확인");
         JTextField add_in = new JTextField(10);
          JTextField add_in2 = new JTextField(10);
           JTextField add_in3 = new JTextField(10);
         JTextField add_out = new JTextField(10);
          JTextField add_out2 = new JTextField(10);
           JTextField add_out3 = new JTextField(10);
         JTextField add_num = new JTextField(10);
         
         add_in.setBounds(180, 170, 60, 30);
         add_in2.setBounds(240, 170, 60, 30);
         add_in3.setBounds(300, 170, 60, 30);
         add_out.setBounds(180, 210, 60, 30);
         add_out2.setBounds(240, 210, 60, 30);
         add_out3.setBounds(300, 210, 60, 30);
         add_num.setBounds(180, 250, 60, 30);
        btn1.setBounds(600, 600, 172, 30);
        
        frmR_add.getContentPane().add(add_in);
        frmR_add.getContentPane().add(add_in2);
        frmR_add.getContentPane().add(add_in3);
        frmR_add.getContentPane().add(add_out);
        frmR_add.getContentPane().add(add_out2);
        frmR_add.getContentPane().add(add_out3);
        frmR_add.getContentPane().add(add_num);
        frmR_add.getContentPane().add(btn1);
        
        frmR_add.setSize(800,800);
        frmR_add.setLocationRelativeTo(null);
        frmR_add.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        btn1.addActionListener(event -> {
                                                                                                                                                                  
            
            startYear = Integer.parseInt(add_in.getText());
            startMonth = Integer.parseInt(add_in2.getText());
            startDay = Integer.parseInt(add_in3.getText());
            
            
            endYear = Integer.parseInt(add_out.getText());
            endMonth = Integer.parseInt(add_out2.getText());
            endDay = Integer.parseInt(add_out3.getText());
            
            startDateI = startYear*10000 + startMonth*100 + startDay; 
            endDateI = endYear*10000 + endMonth*100 + endDay; 
            days = helper.getDiffBetweenTwoDays(startYear, startMonth, startDay, endYear, endMonth, endDay);
            
            numOfGuests = Integer.parseInt(add_num.getText());
            
            if(startMonth > 12 || endMonth > 12 || startDay > helper.getLastDayOfMonth(startYear, startMonth) || endDay > helper.getLastDayOfMonth(endYear, endMonth)){
                 JOptionPane.showMessageDialog(null, "잘못된 날짜 입력!");
            }
            else if(helper.getTodayDateI() >startDateI){
                 JOptionPane.showMessageDialog(null, "예약 날짜를 오늘 이후로 해주세요");
            }
            else if(endDateI<=startDateI){
                JOptionPane.showMessageDialog(null, "잘못된 날짜 입력!");
            }
            else{
                if(endDateI>startDateI){
                    System.out.format("%d/%d/%d ~ %d/%d/%d (%d일)\n",startYear,startMonth,startDay,endYear,endMonth,endDay,days);
                    frmR_add2();
                    boolean canPrint = true;
                    try {
                        showAvaliableRooms(canReserveRoom,startDateI,endDateI,numOfGuests,canPrint);
                    } catch (IOException ex) {
                        Logger.getLogger(ReservationSystem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    frmR_add.setVisible(false);
                }
            }
            frmR_add.setVisible(false);
        });// 버튼 끝
        
        
         frmR_add.setVisible(true);
    }// 예약 추가 프레임1 끝
    public void frmR_add2(){
        JFrame frmR_add2 = new JFrame();
        frmR_add2.getContentPane().setLayout(null);
        
        JLabel lbl = new JLabel();
        lbl.setBounds(100, 170, 150, 30);
        lbl.setText("방 번호: ");
        frmR_add2.getContentPane().add(lbl);
        JLabel lbl2 = new JLabel();
        lbl2.setBounds(100, 210, 150, 30);
        lbl2.setText("예약자 이름: ");
        frmR_add2.getContentPane().add(lbl2);
        
        JButton btn1 = new JButton("확인");
         JTextField add_ID = new JTextField(10);
         JTextField add_name = new JTextField(10);
      
         add_ID.setBounds(182, 170, 172, 30);
         add_name.setBounds(182, 210, 172, 30);
        btn1.setBounds(600, 600, 172, 30);
        
        frmR_add2.getContentPane().add(add_ID);
        frmR_add2.getContentPane().add(add_name);
        frmR_add2.getContentPane().add(btn1);
        
        frmR_add2.setSize(800,800);
        frmR_add2.setLocationRelativeTo(null);
        frmR_add2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        ///////////////////////////////////////////////////버튼 시작/////////////////////////////////////////////////////
        btn1.addActionListener(event -> {
         boolean canReserve = false;
         int maximunGuests = 0;
         int costPerNight = 0;
         String roomID;
         
         roomID = add_ID.getText();
         String reserverName = add_name.getText();
         
         for(Room temp : canReserveRoom){
                if(temp.getRoomNumber().equals(roomID)){
                    canReserve = true;
                    maximunGuests = temp.getNumberOfGuests();
                    costPerNight = temp.getPricePerNight();
                    break;
                }
            }
         
         if(!canReserve){
                JOptionPane.showMessageDialog(null, "예약 불가능한 방입니다.");
         }
         else if(numOfGuests>maximunGuests){
             JOptionPane.showMessageDialog(null, "정원 초과입니다.");
         }
         else{
         ReservedInfo temp = new ReservedInfo(roomID,reserverName,numOfGuests,startYear,startMonth,startDay,endYear,endMonth,endDay,false);
        temp.setTotalRoomFee((int)days * costPerNight);
        reserveDB.add(temp);
            try {
                helper.writeDBFile(3, reserveDB);
                ReserveReport.addReport("reserve","add;"+reserverName + ";"+ roomID +  Integer.toString( startYear) + "/" + startMonth + "/"  + Integer.toString( startMonth) + "/" + Integer.toString( startDay) + "~" +Integer.toString( endYear)  +"/"+Integer.toString( endMonth)+ "/"+ Integer.toString( endDay) );
            } catch (IOException ex) {
                Logger.getLogger(ReservationSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            JOptionPane.showMessageDialog(null, "예약 완료.");
            frmR_add2.setVisible(false);
            frmR.setVisible(true);
         }
        });////////////////////////////////////////////////////버튼 끝//////////////////////////////////////////////////////////////////
        
        frmR_add2.setVisible(true);
   }// 예약 추가 프레임2 끝
    
    public ArrayList<ReservedInfo> showAllReservation(int onlyCheckIn) throws IOException{
        ArrayList<ReservedInfo> canCheckIn = new  ArrayList<>();
        if(reserveDB.isEmpty()){
            System.out.println("예약 현황이 없습니다.");
        }
        else if(onlyCheckIn == 0){
            System.out.println("\n=============================================================================예약=============================================================================");
            for(ReservedInfo temp : reserveDB){
                System.out.format("호실 : %4s\t예약자 : %-20s숙박 인원 : %-2d 예약기간 : %d/%02d/%02d ~ %d/%02d/%02d\t체크인 : %s\t숙박 비용 : %7d\t추가 금액 : %d\n",temp.getRoomID(),temp.getReserverName(),temp.getNumOfGuests(),temp.getStartYear(),temp.getStartMonth(),temp.getStartDay(),temp.getEndYear(),temp.getEndMonth(),temp.getEndDay(),temp.getCheck(),temp.getTotalRoomFee(),temp.getExtraFee());
            }
            System.out.println("=============================================================================================================================================================");
        } 
        
        else if(onlyCheckIn == 1){
            System.out.println("\n=============================================================================예약=============================================================================");
            for(ReservedInfo temp : reserveDB){
                if(temp.getCheck()){
                    System.out.format("호실 : %4s\t예약자 : %-20s숙박 인원 : %-5d예약기간 : %d/%02d/%02d ~ %d/%02d/%02d  체크인 : %s\t숙박 비용 : %4d\t추가 금액 : %d\n",temp.getRoomID(),temp.getReserverName(),temp.getNumOfGuests(),temp.getStartYear(),temp.getStartMonth(),temp.getStartDay(),temp.getEndYear(),temp.getEndMonth(),temp.getEndDay(),temp.getCheck(),temp.getTotalRoomFee(),temp.getExtraFee());
                    canCheckIn.add(temp);
                }
            }
            System.out.println("=============================================================================================================================================================");
        }
        
        else if(onlyCheckIn == 2){
            System.out.println("\n=============================================================================예약=============================================================================");
            for(ReservedInfo temp : reserveDB){
                if(!temp.getCheck()){
                    System.out.format("호실 : %4s\t예약자 : %-20s숙박 인원 : %-5d예약기간 : %d/%02d/%02d ~ %d/%02d/%02d  체크인 : %s\t숙박 비용 : %4d\t추가 금액 : %d\n",temp.getRoomID(),temp.getReserverName(),temp.getNumOfGuests(),temp.getStartYear(),temp.getStartMonth(),temp.getStartDay(),temp.getEndYear(),temp.getEndMonth(),temp.getEndDay(),temp.getCheck(),temp.getTotalRoomFee(),temp.getExtraFee());
                    canCheckIn.add(temp);
                }
            }
            System.out.println("=============================================================================================================================================================");
        }
        
        return canCheckIn;
    }    
    
     public ArrayList<ReservedInfo> showUsingReservation() throws IOException{
         
        ArrayList<ReservedInfo> id = new ArrayList<>();
        if(reserveDB.isEmpty()){
            System.out.println("예약 현황이 없습니다.");
        }
        else{
            System.out.println("\n==========================================================예약==========================================================");
            for(ReservedInfo temp : reserveDB){
                if(temp.getStartDateI() <= helper.getTodayDateI()){
                    if(!temp.getCheck()){
                     System.out.format("호실 : %4s\t\t예약자 : %-20s예약기간 : %d/%02d/%02d ~ %d/%02d/%02d\n",temp.getRoomID(),temp.getReserverName(),temp.getStartYear(),temp.getStartMonth(),temp.getStartDay(),temp.getEndYear(),temp.getEndMonth(),temp.getEndDay());
                     id.add(temp);
                    }
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
        ArrayList<ReservedInfo> notCheckedInRooms = showAllReservation(2);
        if(notCheckedInRooms.isEmpty())
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
        
        for(ReservedInfo temp : notCheckedInRooms){
            if(temp.getRoomID().equals(roomID) && temp.getReserverName().equals(reserverName) && temp.getStartDateI() == checkinDateI){
                reserveDB.remove(temp);
                helper.writeDBFile(3, reserveDB);
                ReserveReport.addReport("reserve","delete;"+reserverName + ";"+ roomID);
                System.out.print("삭제 완료");
                break;
            }
        }
    }

    public void checkIn() throws IOException{
        ArrayList<ReservedInfo> checkedInRooms =showUsingReservation();
        if(checkedInRooms.isEmpty()){
            System.out.println("체크인할 예약이 없습니다.");
            return;
        }
        
        System.out.print("체크인 할 방의 번호를 입력해 주세요 : ");
        String roomID = helper.getUserInput("[0-1]{0,1}[0-9][0-1][0-9]");
        
        boolean canFind = false;
        for(ReservedInfo temp : reserveDB){
            if(temp.getRoomID().equals(roomID) && temp.getCheck() == false){
                canFind = true;
                temp.setCheck(true);
                System.out.print("체크인 완료");
                helper.writeDBFile(3, reserveDB);
                ReserveReport.addReport("reserve","checkIn;"+ roomID);
                break;
            }
        }
        
        if(!canFind){
            System.out.println("예약된 방을 찾을 수 없습니다.");
        }
    }
    
    public void checkOut() throws IOException{
        ArrayList<ReservedInfo> checkedInRooms =showAllReservation(1);
        
        if(checkedInRooms.isEmpty()){
            System.out.println("체크인된 방이 없습니다.");
            return;
        }
        
        System.out.print("체크 아웃 할 방의 번호를 입력해 주세요 : ");
        String roomID = helper.getUserInput("[0-1]{0,1}[0-9][0-1][0-9]");

        boolean canFind = false;
        for(ReservedInfo temp : reserveDB){
            if(temp.getRoomID().equals(roomID) && temp.getCheck() == true){
                canFind = true;
                reserveDB.remove(temp);
                helper.writeDBFile(3, reserveDB);
                ReserveReport.addReport("reserve","checkOut;"+ roomID);
                System.out.print("체크 아웃 완료");
                break;
            }
        }
        if(!canFind){
            System.out.println("예약된 방을 찾을 수 없습니다.");
        }
    }
    
}
