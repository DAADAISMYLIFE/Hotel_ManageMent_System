/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.reservation;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import teamproject.IntegrateManager;
import teamproject.SystemHelper;
import teamproject.room.RoomSystem;
import teamproject.room.Room;
import teamproject.report.ReportSystem;
/**
 *
 * @author qkekd
 * 예약 정보를 관리하는 시스템
 */
public class ReservationSystem extends JFrame {
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
            ReservedInfo temp= new ReservedInfo(readContext.split(";")[0],readContext.split(";")[1],Integer.parseInt(readContext.split(";")[2]),Integer.parseInt(readContext.split(";")[3]),Integer.parseInt(readContext.split(";")[4]),Integer.parseInt(readContext.split(";")[5]),Integer.parseInt(readContext.split(";")[6]),Integer.parseInt(readContext.split(";")[7]),Integer.parseInt(readContext.split(";")[8]),Integer.parseInt(readContext.split(";")[9]),Integer.parseInt(readContext.split(";")[10]),Boolean.parseBoolean(readContext.split(";")[11]));
            reserveDB.add(temp);
        } 
    }
    
    public ArrayList<ReservedInfo> getReserveDB(){
        return this.reserveDB;
    }
    
    public void runReserSys() throws IOException{
        setTitle("예약 시스템");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container swingContext = getContentPane();
        swingContext.setLayout(null);
        JButton addReservation = new JButton("예약 추가");
        JButton deleteReservation = new JButton("예약 삭제");
        JButton checkInReservation = new JButton("체크인");
        JButton checkOutReservation = new JButton("체크아웃");
        JButton quitReservation = new JButton("나가기");
        JTable reservationTable;
        JScrollPane scrollPane;
        
        
        String[][] tableData = new String[][] {};
        
        String[] columnNames = {"호실","예약자","인원","예약기간","체크인 여부", "숙박 비용", "추가 금액"};
        DefaultTableModel model = new DefaultTableModel(tableData, columnNames){
            public boolean isCellEditable(int rowIndex, int mColIndex){
                return false;
            }
        };
        reservationTable = new JTable(model);
        scrollPane = new JScrollPane(reservationTable);
        
        TableColumnModel columnModel = reservationTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);  // 호실
        columnModel.getColumn(1).setPreferredWidth(100); // 예약자
        columnModel.getColumn(2).setPreferredWidth(50);  // 인원
        columnModel.getColumn(3).setPreferredWidth(200); // 예약기간
        columnModel.getColumn(4).setPreferredWidth(100); // 체크인 여부
        columnModel.getColumn(5).setPreferredWidth(100); // 숙박 비용
        columnModel.getColumn(6).setPreferredWidth(100); // 추가 금액
        
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setResizable(false);
        }
        reservationTable.getTableHeader().setReorderingAllowed(false);
        DefaultTableModel modelTemp = (DefaultTableModel) reservationTable.getModel();
        for (ReservedInfo reservation : reserveDB) {
            String room = reservation.getRoomID();
            String name = reservation.getReserverName();
            String numPeople = String.valueOf(reservation.getNumOfGuests());
            String checkIn =String.format("%04d/%02d/%02d",reservation.getStartYear(),reservation.getStartMonth(),reservation.getStartDay());
            String checkOut = String.format("%04d/%02d/%02d",reservation.getEndYear(),reservation.getEndMonth(),reservation.getEndDay());
            String time = checkIn + " ~ " + checkOut; 
            String isCheck = reservation.getCheck()? "true" : "false";
            String cost = String.valueOf(reservation.getTotalRoomFee());
            String extraCost = String.valueOf(reservation.getExtraFee());
            model.addRow(new String[] {room, name, numPeople, time, isCheck, cost, extraCost});
        }
        
        scrollPane.setBounds(200, 50, 700, 300);
        swingContext.add(scrollPane);
        
        addReservation.setLocation(10,100);
        deleteReservation.setLocation(10,150);
        checkInReservation.setLocation(10,200);
        checkOutReservation.setLocation(10,250);
        quitReservation.setLocation(10,300);
        
        addReservation.setSize(180,30);
        deleteReservation.setSize(180,30);
        checkInReservation.setSize(180,30);
        checkOutReservation.setSize(180,30);
        quitReservation.setSize(180,30);
        
        swingContext.add(addReservation);
        swingContext.add(deleteReservation);
        swingContext.add(checkInReservation);
        swingContext.add(checkOutReservation);
        swingContext.add(quitReservation);
        
        setSize(1000,500);
        setVisible(true);
        
        addReservation.addActionListener(event->{
            JFrame inputFrame = new JFrame("예약 추가");
            inputFrame.setSize(800, 500);
            inputFrame.setLayout(null);
            
            //예약 가능 방 출력
            JTable canReserveRoomTable;
            JScrollPane roomScrollPane;

            String[][] roomData = new String[][] {};

            String[] columnRoomNames = {"호실","타입","최대 인원","숙박비(1일당)"};
            DefaultTableModel modelRoom = new DefaultTableModel(roomData, columnRoomNames){
                public boolean isCellEditable(int rowIndex, int mColIndex){
                    return false;
                }
            };
            canReserveRoomTable = new JTable(modelRoom);
            roomScrollPane = new JScrollPane(canReserveRoomTable);

            TableColumnModel columnModelRoom = canReserveRoomTable.getColumnModel();
            columnModelRoom.getColumn(0).setPreferredWidth(10);  // 호실
            columnModelRoom.getColumn(1).setPreferredWidth(50); // 타입
            columnModelRoom.getColumn(2).setPreferredWidth(50);  // 인원
            columnModelRoom.getColumn(3).setPreferredWidth(50); // 숙박비


            for (int i = 0; i < columnModelRoom.getColumnCount(); i++) {
                columnModelRoom.getColumn(i).setResizable(false);
            }
            canReserveRoomTable.getTableHeader().setReorderingAllowed(false);
            DefaultTableModel modelCanRoom = (DefaultTableModel) canReserveRoomTable.getModel();
            roomScrollPane.setBounds(300, 20, 350, 315);
            inputFrame.add(roomScrollPane);
            //////////////////////////            
            
            JLabel nameLabel = new JLabel("예약자:");
            JTextField nameField = new JTextField();
            JLabel numPeopleLabel = new JLabel("인원:");
            JTextField numPeopleField = new JTextField();
            JLabel checkInLabel = new JLabel("체크인 날짜( / 구분):");
            JTextField checkInField = new JTextField();
            JLabel checkOutLabel = new JLabel("체크아웃 날짜( / 구분)");
            JTextField checkOutField = new JTextField();
            JButton checkButton = new JButton("가능한 방 보기");
            JButton makeReserveButton = new JButton("예약 하기");
            
            nameLabel.setLocation(10,70);
            nameField.setLocation(150,70);
            numPeopleLabel.setLocation(10,120);
            numPeopleField.setLocation(150,120);
            checkInLabel.setLocation(10,170);
            checkInField.setLocation(150,170);
            checkOutLabel.setLocation(10,220);
            checkOutField.setLocation(150,220);
            checkButton.setLocation(10,300);
            makeReserveButton.setLocation(150,300);
            
            nameLabel.setSize(100,30);
            nameField.setSize(100,30);
            numPeopleLabel.setSize(100,30);
            numPeopleField.setSize(100,30);
            checkInLabel.setSize(200,30);
            checkInField.setSize(100,30);
            checkOutLabel.setSize(200,30);
            checkOutField.setSize(100,30);
            checkButton.setSize(120,30);
            makeReserveButton.setSize(120,30);

            inputFrame.add(nameLabel);
            inputFrame.add(nameField);
            inputFrame.add(numPeopleLabel);
            inputFrame.add(numPeopleField);
            inputFrame.add(checkInLabel);
            inputFrame.add(checkInField);
            inputFrame.add(checkOutLabel);
            inputFrame.add(checkOutField);
            inputFrame.add(checkButton);
            inputFrame.add(makeReserveButton);

            inputFrame.setLocationRelativeTo(null);
            inputFrame.setVisible(true);
            checkButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();
                    String numPeople = numPeopleField.getText();
                    String checkIn = checkInField.getText();
                    String checkOut = checkOutField.getText();
                    modelCanRoom.setNumRows(0);
                    
                    if(name.isBlank() || numPeople.isBlank() || checkIn.isBlank() || checkOut.isBlank()){
                        JOptionPane.showMessageDialog(null, "모든 항목을 작성해주세요");
                    }
                    else{
                        if(!(checkIn.matches("^20\\d{2}/\\d{1,2}/\\d{1,2}$") && checkOut.matches("^20\\d{2}/\\d{1,2}/\\d{1,2}$") && numPeople.matches("[0-9]"))){
                            JOptionPane.showMessageDialog(null, "형식이 잘못되었습니다.");
                        }
                        else{
                            int startYear = Integer.parseInt(checkIn.split("/")[0]);
                            int startMonth = Integer.parseInt(checkIn.split("/")[1]);
                            int startDay = Integer.parseInt(checkIn.split("/")[2]);

                            int endYear = Integer.parseInt(checkOut.split("/")[0]);
                            int endMonth = Integer.parseInt(checkOut.split("/")[1]);
                            int endDay = Integer.parseInt(checkOut.split("/")[2]);

                            int startDateI = startYear*10000 + startMonth*100 + startDay; 
                            int endDateI = endYear*10000 + endMonth*100 + endDay; 
                            if((startMonth > 12 || endMonth > 12 || startDay > helper.getLastDayOfMonth(startYear, startMonth) || endDay > helper.getLastDayOfMonth(endYear, endMonth)) || helper.getTodayDateI() >startDateI || endDateI<=startDateI){
                                JOptionPane.showMessageDialog(null, "날짜가 잘못되었습니다.");
                            }
                            else{
                                ArrayList<Room> canReserveRoom = new ArrayList<>();
                                try {
                                    showAvaliableRooms(canReserveRoom,startDateI,endDateI,Integer.parseInt(numPeople),true);
                                } catch (IOException ex) {
                                    Logger.getLogger(ReservationSystem.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                                for(Room canRoom : canReserveRoom){
                                    modelCanRoom.addRow(new String[] {canRoom.getRoomNumber(), canRoom.getRoomtype(), String.valueOf(canRoom.getNumberOfGuests()),String.valueOf(canRoom.getPricePerNight())}); 
                                }
                                nameField.setEnabled(false);
                                numPeopleField.setEnabled(false);
                                checkInField.setEnabled(false);
                                checkOutField.setEnabled(false);
                                
                            }
                            //////////////////////////
                                makeReserveButton.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        int selectedRow = canReserveRoomTable.getSelectedRow();
                                        if (selectedRow != -1) {
                                            
                                            String roomID = (String) canReserveRoomTable.getValueAt(selectedRow, 0);
                                            int oneDayFee = Integer.parseInt((String) canReserveRoomTable.getValueAt(selectedRow, 3));
                                            int startYear = Integer.parseInt(checkIn.split("/")[0]);
                                            int startMonth = Integer.parseInt(checkIn.split("/")[1]);
                                            int startDay = Integer.parseInt(checkIn.split("/")[2]);

                                            int endYear = Integer.parseInt(checkOut.split("/")[0]);
                                            int endMonth = Integer.parseInt(checkOut.split("/")[1]);
                                            int endDay = Integer.parseInt(checkOut.split("/")[2]);
                                            
                                            ReservedInfo temp = new ReservedInfo(roomID,name,Integer.parseInt(numPeople),startYear,startMonth,startDay,endYear,endMonth,endDay,false);    
                                            String days =  String.valueOf(helper.getDiffBetweenTwoDays(startYear, startMonth, startDay, endYear, endMonth, endDay));
                                            temp.setTotalRoomFee(Integer.parseInt(days) * oneDayFee);
                                            String totalRoomFee = temp.getTotalRoomFee() +"";
                                            
                                            reserveDB.add(temp);
                                            
                                            try {
                                                helper.writeDBFile(3, reserveDB);
                                                ReserveReport.addReport("reserve","add;"+name + ";"+ roomID +";"+  Integer.toString( startYear) + "/"  + Integer.toString( startMonth) + "/" + Integer.toString( startDay) + ";" +Integer.toString( endYear)  +"/"+Integer.toString( endMonth)+ "/"+ Integer.toString( endDay) );
                                            } catch (IOException ex) {
                                                Logger.getLogger(ReservationSystem.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            
                                            String time = String.format(checkIn + " ~ " + checkOut);
                                            DefaultTableModel model = (DefaultTableModel) reservationTable.getModel();
                                            model.addRow(new String[] {roomID, name, numPeople, time, "false", totalRoomFee, "0"});
                                            JOptionPane.showMessageDialog(null, "예약이 완료 되었습니다.");
                                            inputFrame.dispose();
                                        }
                                    }
                             });
                                ////
                        }
                    }
                }
            });
            
            /*
            makeReserveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = canReserveRoomTable.getSelectedRow();
                    if (selectedRow != -1) {
                        
                    }
                }
            });*/
  
        });
        
        deleteReservation.addActionListener(event->{
            int selectedRow = reservationTable.getSelectedRow();
            if (selectedRow != -1) {
                JFrame questionFrame = new JFrame("예약 삭제");
                questionFrame.setSize(350, 300);
                questionFrame.setLayout(null);

                JLabel questionLabel = new JLabel("정말로 예약을 삭제합니까?");
                JButton yesButton = new JButton("확인");
                JButton noButton = new JButton("취소");
                
                questionLabel.setLocation(100,50);
                yesButton.setLocation(50,150);
                noButton.setLocation(200,150);
                
                questionLabel.setSize(200,50);
                yesButton.setSize(80,50);
                noButton.setSize(80,50);
                
                questionFrame.add(questionLabel);
                questionFrame.add(yesButton);
                questionFrame.add(noButton);
                questionFrame.setVisible(true);
                
                yesButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        String room = (String) reservationTable.getValueAt(selectedRow, 0);
                        String name = (String) reservationTable.getValueAt(selectedRow, 1);
                        String checkInDate = ((String) reservationTable.getValueAt(selectedRow, 3)).split(" ")[0];
                        String isCheckedIn = (String) reservationTable.getValueAt(selectedRow, 4);
                        
                        if(isCheckedIn.equals("true")){
                            JOptionPane.showMessageDialog(null, "이미 체크인 완료된 방입니다.");
                        }
                        else{
                            int startYear = Integer.parseInt(checkInDate.split("/")[0]);
                            int startMonth = Integer.parseInt(checkInDate.split("/")[1]);
                            int startDay = Integer.parseInt(checkInDate.split("/")[2]);

                            int startDateI = startYear*10000 + startMonth*100 + startDay; 
                            for(ReservedInfo temp : reserveDB){
                                if(temp.getRoomID().equals(room) && temp.getReserverName().equals(name) && temp.getStartDateI() == startDateI){
                                    JOptionPane.showMessageDialog(null, "예약을 삭제했습니다.");
                                    reserveDB.remove(temp);
                                    try {
                                        helper.writeDBFile(3, reserveDB);
                                        ReserveReport.addReport("reserve","delete;"+name + ";"+ room);
                                    } catch (IOException ex) {
                                        Logger.getLogger(ReservationSystem.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    break;
                                }
                            }
                        
                            model.removeRow(selectedRow);
                            questionFrame.dispose();
                        }
                    }  
                });
                
                noButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        questionFrame.dispose();
                    }
                });
            }
            else
                JOptionPane.showMessageDialog(null, "명령을 수행할 예약을 클릭해 주세요.");
        });
        
        checkInReservation.addActionListener(event->{
            int selectedRow = reservationTable.getSelectedRow();
            if (selectedRow != -1) {
                String room = (String) reservationTable.getValueAt(selectedRow, 0);
                for(ReservedInfo temp : reserveDB){
                    if(temp.getRoomID().equals(room) ){
                        if(temp.getCheck() == false){
                            if(temp.getStartDateI()<=helper.getTodayDateI()){
                                temp.setCheck(true);
                                JOptionPane.showMessageDialog(null, "체크인을 완료했습니다.");
                                model.setValueAt("true", selectedRow, 4);
                                try {
                                    helper.writeDBFile(3, reserveDB);
                                    ReserveReport.addReport("reserve","checkIn;"+ room);
                                } catch (IOException ex) {
                                    Logger.getLogger(ReservationSystem.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "이미 체크인 완료된 방입니다.");
                        }
                    }
                }
            }
            else
                JOptionPane.showMessageDialog(null, "명령을 수행할 예약을 클릭해 주세요.");
        });
        
        checkOutReservation.addActionListener(event->{
            int selectedRow = reservationTable.getSelectedRow();
            if (selectedRow != -1) {
            for(ReservedInfo temp : reserveDB){
                String room = (String) reservationTable.getValueAt(selectedRow, 0);
                if(temp.getRoomID().equals(room)){
                    if(temp.getCheck() == true){
                        reserveDB.remove(temp);
                    try {
                        helper.writeDBFile(3, reserveDB);
                        ReserveReport.addReport("reserve","checkOut;"+ room);
                    } catch (IOException ex) {
                        Logger.getLogger(ReservationSystem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        JOptionPane.showMessageDialog(null, "체크아웃을 완료했습니다.");
                        model.removeRow(selectedRow);
                        break;
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "아직 체크인 되지 않은 방입니다.");
                    }
                 }
                }
            }
            else
                JOptionPane.showMessageDialog(null, "명령을 수행할 예약을 클릭해 주세요.");
        });
        
        quitReservation.addActionListener(event->{
            this.dispose();
            IntegrateManager.frm.setVisible(true);
        });
        setLocationRelativeTo(null);
    }

    public ArrayList<ReservedInfo> showAllReservation(int onlyCheckIn) throws IOException{
        ArrayList<ReservedInfo> canCheckIn = new  ArrayList<>();
        if(reserveDB.isEmpty()){
            JOptionPane.showMessageDialog(null, "예약 현황이 없습니다.");
        }
        else if(onlyCheckIn == 1){
            for(ReservedInfo temp : reserveDB){
                if(temp.getCheck()){
                    canCheckIn.add(temp);
                }
            }
        }
        
        else if(onlyCheckIn == 2){
            for(ReservedInfo temp : reserveDB){
                if(!temp.getCheck()){
                    canCheckIn.add(temp);
                }
            }
        }
        return canCheckIn;
    }    
    
     public ArrayList<ReservedInfo> showUsingReservation() throws IOException{
         
        ArrayList<ReservedInfo> id = new ArrayList<>();
        if(reserveDB.isEmpty()){
             JOptionPane.showMessageDialog(null, "예약 현황이 없습니다.");
        }
        else{
           for(ReservedInfo temp : reserveDB){
                if(temp.getStartDateI() <= helper.getTodayDateI()){
                    if(!temp.getCheck()){
                         id.add(temp);
                    }
                }
            }
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
}
