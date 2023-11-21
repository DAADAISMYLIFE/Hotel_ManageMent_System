/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.reservation;

import java.io.IOException;
import static java.lang.String.format;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import teamproject.SystemHelper;
import teamproject.room.RoomSystem;
import teamproject.room.Room;
import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;
import teamproject.IntegrateManager;
import teamproject.food.Food;
import teamproject.food.FoodSystem;
/**
 *
 * @author qkekd
 * 예약 정보를 관리하는 시스템
 */
public class ReservationSystem {
    private ArrayList<ReservedInfo> reserveDB;
    private SystemHelper helper;
    private RoomSystem RS;
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
         int days;

          ArrayList<Room> canReserveRoom = new ArrayList<>();

      boolean isCorrect = false;
    
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
    public void runR(){
         frmR.getContentPane().setLayout(null);
         
         JButton btn1 = new JButton("모든 객실 보기");
        JButton btn2 = new JButton("예약 현황 보기");
         JButton btn3 = new JButton("예약 추가하기");
         JButton btn4 = new JButton("나가기");

        btn1.setBounds(182, 120, 172, 30);
        btn2.setBounds(182, 170, 172, 30);
        btn3.setBounds(182, 220, 172, 30);
        btn4.setBounds(182, 270, 172, 30);
        
        frmR.getContentPane().add(btn1);
        frmR.getContentPane().add(btn2);
        frmR.getContentPane().add(btn3);
        frmR.getContentPane().add(btn4);
              
        frmR.setSize(500,500);
        frmR.setLocationRelativeTo(null);
        frmR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        btn1.addActionListener(event -> {
            System.out.println("모든 객실 보기");
             try {
                 showAllReservation();
             } catch (IOException ex) {
                 Logger.getLogger(ReservationSystem.class.getName()).log(Level.SEVERE, null, ex);
             }
            frmR.setVisible(false);
        });
        btn2.addActionListener(event -> {
            showAllReservationFrame();
            frmR.setVisible(false);
        });
         btn3.addActionListener(event -> {
            frmR_add();
            frmR.setVisible(false);
        });
        btn4.addActionListener(event -> {
            System.out.println("예약 시스템 종료.");
            frmR.setVisible(false);
            IntegrateManager.frm.setVisible(true);
        });
        
        frmR.setVisible(true);
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
         JTextField add_out = new JTextField(10);
         JTextField add_num = new JTextField(10);
         
         add_in.setBounds(182, 170, 172, 30);
         add_out.setBounds(182, 210, 172, 30);
         add_num.setBounds(182, 250, 172, 30);
        btn1.setBounds(600, 600, 172, 30);
        
        frmR_add.getContentPane().add(add_in);
        frmR_add.getContentPane().add(add_out);
        frmR_add.getContentPane().add(add_num);
        frmR_add.getContentPane().add(btn1);
        
        frmR_add.setSize(800,800);
        frmR_add.setLocationRelativeTo(null);
        frmR_add.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        btn1.addActionListener(event -> {
                                                                                                                                                                  
            
            String startDate = add_in.getText();
            startYear = Integer.parseInt(startDate.split("/")[0]);
            startMonth = Integer.parseInt(startDate.split("/")[1]);
            startDay = Integer.parseInt(startDate.split("/")[2]);
            
            String endDate = add_out.getText();
            endYear = Integer.parseInt(endDate.split("/")[0]);
            endMonth = Integer.parseInt(endDate.split("/")[1]);
            endDay = Integer.parseInt(endDate.split("/")[2]);
            
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
        ReservedInfo temp = new ReservedInfo(roomID,reserverName,numOfGuests,startYear,startMonth,startDay,endYear,endMonth,endDay);
        temp.setTotalRoomFee(days * costPerNight);
        reserveDB.add(temp);
            try {
                helper.writeDBFile(3, reserveDB);
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
    }
    
    public void showAllReservationFrame(){
          if(reserveDB.isEmpty()){
            System.out.println("예약 현황이 없습니다.");
            showMessageDialog(null, "예약 현황이 없습니다.");
        }
        else{
              JFrame R_Showfrm = new JFrame();
                DefaultListModel<String> R_List =new DefaultListModel<>();
                for(ReservedInfo temp : reserveDB){
                        String temp1 = format("호실 : %4s\t\t예약자 : %-20s숙박 인원 : %-10d예약기간 : %d/%02d/%02d ~ %d/%02d/%02d\t\t숙박 비용 : %7d\t\t추가 금액 : %d\n",temp.getRoomID(),temp.getReserverName(),temp.getNumOfGuests(),temp.getStartYear(),temp.getStartMonth(),temp.getStartDay(),temp.getEndYear(),temp.getEndMonth(),temp.getEndDay(),temp.getTotalRoomFee(),temp.getExtraFee());
                        R_List.addElement(temp1);
                    }
                 JList<String> ls = new JList<>(R_List);
                 JButton B = new JButton("확인");

                 B.setBounds(700, 700, 70, 40);
                 ls.setBounds(50, 50, 700, 650);

                 R_Showfrm.add(B);
                 R_Showfrm.add(ls);

                 B.addActionListener(event -> {          
                    R_Showfrm.setVisible(false);
                    frmR.setVisible(true);
                });

                R_Showfrm.setSize(800,800);
                R_Showfrm.setLayout(null);
                R_Showfrm.setVisible(true);
                R_Showfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } 
    }
    public void showAllReservation() throws IOException{
        if(reserveDB.isEmpty()){
            System.out.println("예약 현황이 없습니다.");
            showMessageDialog(null, "예약 현황이 없습니다.");
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
    
     public void showAvaliableRoomsFrame(ArrayList<Room> canReserveRoom,int startDateI, int endDateI,int reserveGuests,boolean canPrint){
         DefaultListModel<String> A_List =new DefaultListModel<>();
         
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
                String temp = "방번호: "+RS.roomDB.get(i).getRoomNumber()+"     |방타입: "+RS.roomDB.get(i).getRoomTypes()+"      |투숙객수: "+RS.roomDB.get(i).getNumberOfGuests()+"    |박당 가격: "+RS.roomDB.get(i).getPricePerNight();
                        A_List.addElement(temp);
                if(canPrint)
                    RS.showRoom(i);
            }
        }
          JList<String> ls = new JList<>(A_List);
                        JButton B = new JButton("확인");

                        B.setBounds(900, 900, 70, 40);
                        ls.setBounds(50, 50, 850, 850);

                        A_ShowRoom.add(B);
                        A_ShowRoom.add(ls);

                        B.addActionListener(event -> {          
                            A_ShowRoom.setVisible(false);
                        });

                        A_ShowRoom.setSize(1000,1000);
                        A_ShowRoom.setLayout(null);
                        A_ShowRoom.setVisible(true);
                        A_ShowRoom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
