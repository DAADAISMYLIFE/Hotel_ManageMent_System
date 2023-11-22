/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.food;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import teamproject.IntegrateManager;
import teamproject.SystemHelper;
import teamproject.reservation.ReservationSystem;
import teamproject.reservation.ReservedInfo;
import teamproject.report.ReportSystem;


/**
 *
 * @author qkekd
 */
public class FoodSystem extends JFrame {
    private ArrayList<Food> foodDB; // 식품 관련 정보(메뉴 이름, 가격)
    private ReservationSystem reserveSys;
    private SystemHelper helper;
    private int foodCount;
    private ReportSystem foodReport;
    private boolean isManager;
    
    public FoodSystem(ReservationSystem reserveSys,ReportSystem reportSys) {
        this.reserveSys = reserveSys;
        foodReport = reportSys;
    }
    
    public void FoodSystemInit(boolean isManager)throws IOException{
        foodDB = new ArrayList<>();
        helper = new SystemHelper();
        foodCount = 1;
        this.isManager = isManager;
        helper.createDBFile(2, "food");
        for(String readContext : helper.readDBFile(2)){
            Food temp= new Food(Integer.parseInt(readContext.split(";")[0]),readContext.split(";")[1],Integer.parseInt(readContext.split(";")[2]));
            foodDB.add(temp);
            foodCount++;
        }
    }
    
    public void runFoodSystem() throws IOException{
        setTitle("메뉴 시스템");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container swingContext = getContentPane();
        swingContext.setLayout(null);
        JButton addFood = new JButton("메뉴 추가");
        JButton deleteFood = new JButton("메뉴 삭제");
        JButton makeOrder = new JButton("주문하기");
        JButton quitFood = new JButton("나가기");
        JTable menuTable;
        JScrollPane scrollPane;

        String[][] tableData = new String[][] {};
        String[] columnNames = {"메뉴 ID","메뉴 이름","가격"};
        DefaultTableModel model = new DefaultTableModel(tableData, columnNames){
            public boolean isCellEditable(int rowIndex, int mColIndex){
                return false;
            }
        };
        menuTable = new JTable(model);
        scrollPane = new JScrollPane(menuTable);
        TableColumnModel columnModel = menuTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);  // 메뉴 ID
        columnModel.getColumn(1).setPreferredWidth(225); // 메뉴 이름
        columnModel.getColumn(2).setPreferredWidth(80);  // 가격
        
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setResizable(false);
        }
        
        DefaultTableModel modelTemp = (DefaultTableModel) menuTable.getModel();
        for (Food food : foodDB) {
            String menuID = String.valueOf(food.getMenuID());
            String menuName = food.getName();
            String price = String.valueOf(food.getPrice());
            model.addRow(new String[] {menuID, menuName, price});
        }
        
        scrollPane.setBounds(230, 50, 400, 200);
        swingContext.add(scrollPane);
        menuTable.getTableHeader().setReorderingAllowed(false);
        addFood.setLocation(30,50);
        deleteFood.setLocation(30,100);
        makeOrder.setLocation(30,150);
        quitFood.setLocation(30,200);
        
        addFood.setSize(180,30);
        deleteFood.setSize(180,30);
        makeOrder.setSize(180,30);
        quitFood.setSize(180,30);
        
        swingContext.add(addFood);
        swingContext.add(deleteFood);
        swingContext.add(makeOrder);
        swingContext.add(quitFood);
        
        setSize(700,350);
        setVisible(true);
        
        addFood.addActionListener(event->{
            JFrame inputFrame = new JFrame("메뉴 추가");
            inputFrame.setSize(250, 200);
            inputFrame.setLayout(null);
            
            JLabel menuLabel = new JLabel("메뉴 이름:");
            JTextField menuField = new JTextField();
            JLabel priceLabel = new JLabel("메뉴 가격:");
            JTextField priceField = new JTextField();
            JButton submitButton = new JButton("확인");
            
            menuLabel.setLocation(20,20);
            menuField.setLocation(100,20);
            priceLabel.setLocation(20,70);
            priceField.setLocation(100,70);
            submitButton.setLocation(70,120);
            
            menuLabel.setSize(100,30);
            menuField.setSize(100,30);
            priceLabel.setSize(100,30);
            priceField.setSize(100,30);
            submitButton.setSize(100,30);

            inputFrame.add(menuLabel);
            inputFrame.add(menuField);
            inputFrame.add(priceLabel);
            inputFrame.add(priceField);
            inputFrame.add(submitButton);
            inputFrame.setVisible(true);
            
            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String menuID = String.valueOf(foodCount);
                    String menuName = menuField.getText();
                    String menuPrice = priceField.getText();
                    
                    if(menuName.isBlank() || menuPrice.isBlank()){
                        System.out.println("모든 항목을 작성해주세요");
                    }
                    else{
                        if(!menuPrice.matches("[0-9]+")){
                            System.out.println("가격을 제대로 입력해 주세요");
                        }
                        else{
                            Food temp = new Food(Integer.parseInt(menuID),menuName,Integer.parseInt(menuPrice));
                            foodDB.add(temp);
                            try {
                                foodReport.addReport("menu","add;"+(temp.getName()+";"+temp.getPrice()));
                                helper.writeDBFile(2, foodDB);
                            } catch (IOException ex) {
                                Logger.getLogger(FoodSystem.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            DefaultTableModel model = (DefaultTableModel) menuTable.getModel();
                            model.addRow(new String[] {menuID, menuName, menuPrice}); 
                            inputFrame.dispose();
                        }
                    } 
                }
                });
        });
        deleteFood.addActionListener(event->{
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow != -1) {
                JFrame questionFrame = new JFrame("메뉴 삭제");
                questionFrame.setSize(350, 300);
                questionFrame.setLayout(null);

                JLabel questionLabel = new JLabel("정말로 메뉴를 삭제합니까?");
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

                            int menuID = Integer.parseInt((String) menuTable.getValueAt(selectedRow, 0));
                            for(Food temp : foodDB){
                                if(temp.getMenuID()==menuID){
                                    try {
                                        foodReport.addReport("menu","delete;"+temp.getName()+";"+temp.getPrice());
                                        foodDB.remove(temp);
                                        helper.writeDBFile(2, foodDB);
                                    } catch (IOException ex) {
                                        Logger.getLogger(FoodSystem.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    break;
                                }
                            }
                        
                            model.removeRow(selectedRow);
                            questionFrame.dispose();
                        }
                });
            }
        });
        
        makeOrder.addActionListener(event->{
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow != -1) {
                String menuName = (String) menuTable.getValueAt(selectedRow, 1);
                int menuPrice = Integer.parseInt((String) menuTable.getValueAt(selectedRow, 2));
                
                JFrame selectReserverFrame = new JFrame("메뉴 주문");
                selectReserverFrame.setSize(200, 200);
                selectReserverFrame.setLayout(null);
                
                ArrayList<String> checkedInRooms = new ArrayList<>();
                for(ReservedInfo temp : reserveSys.getReserveDB()){
                    if(temp.getCheck()){
                        checkedInRooms.add(temp.getRoomID());
                    }
                }
                String[] availableRooms = checkedInRooms.toArray(new String[0]);
                JList<String> roomList = new JList<>(availableRooms);
                JScrollPane roomListScrollPane = new JScrollPane(roomList);
                roomListScrollPane.setBounds(40, 10, 100, 80); 
                
                selectReserverFrame.add(roomListScrollPane);
                
                
                JButton yesButton = new JButton("확인");
                yesButton.setLocation(50,100);
                yesButton.setSize(80,50);
                selectReserverFrame.add(yesButton);
                
                selectReserverFrame.setVisible(true);
                yesButton.addActionListener(e->{
                    String  selectedRoomID  = roomList.getSelectedValue();
                    for(ReservedInfo temp : reserveSys.getReserveDB()){
                        if(temp.getRoomID().equals(selectedRoomID) && temp.getCheck()){
                            temp.addExtraFee(menuPrice);
                            try {
                                System.out.println("주문 완료");
                                helper.writeDBFile(3,reserveSys.getReserveDB());
                                foodReport.addReport("order", menuName+";"+temp.getRoomID()+";"+Integer.toString(menuPrice));
                            } catch (IOException ex) {
                                Logger.getLogger(FoodSystem.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            selectReserverFrame.dispose();
                            break;
                        }    
                    }
                });
            }
        });
        quitFood.addActionListener(event->{
            this.dispose();
            IntegrateManager.frm.setVisible(true);
        });
    }
    
    public void addFood() throws IOException{
        System.out.print("메뉴 : ");
        String name;
        name =  helper.getUserInput();
        System.out.print("가격 : ");
        int price =Integer.parseInt(helper.getUserInput());
        
        Food newFood = new Food(foodCount,name,price);
        foodCount++;
        
        foodDB.add(newFood);
        foodReport.addReport("menu","add;"+(newFood.getName()+";"+newFood.getPrice()));
        helper.writeDBFile(2, foodDB);
        
    }
    public void deleteFood() throws IOException{
        if(foodDB.isEmpty()){
            System.out.println("메뉴가 존재하지 않습니다.");
            return;
        }
        showFood();
        System.out.print("삭제할 메뉴 ID : ");
        int id = Integer.parseInt(helper.getUserInput());
        Food newFood = new Food(id);
        foodReport.addReport("menu","delete;"+newFood.getName()+";"+newFood.getPrice());
        foodDB.remove(newFood);
        helper.writeDBFile(2, foodDB);
    }
      
    public void showFood(){       
        
        if(foodDB.isEmpty()){
            System.out.println("메뉴가 아직 없습니다. 메뉴를 추가해 주세요");
        }
        else{
            System.out.println("\n==============================================================================================");
            //음식 목록
            for(int i = 0;i < foodDB.size(); i++){
                System.out.printf("메뉴 ID : %02d   이름 : %-30s가격  :  %8d원\n", foodDB.get(i).getMenuID(), foodDB.get(i).getName(), foodDB.get(i).getPrice());
            }
             System.out.println("==============================================================================================");
        }
    }
    
    public void orderFood() throws IOException{
        int canFind = 0;
        if(foodDB.isEmpty()){
             System.out.println("메뉴가 없습니다.");
             return;
         }
         if(reserveSys.showAllReservation(1).isEmpty()){
            return;
        }
         
        System.out.print("주문 호실 : ");
       
        String OrderRoomID = helper.getUserInput();
        
         //비교를 위한 객체 생성
         ReservedInfo roomID = new ReservedInfo(OrderRoomID);
         for(ReservedInfo temp : reserveSys.getReserveDB()){
              if(roomID.equals(temp) && temp.getCheck()){
                        roomID = temp;
                        System.out.println(roomID.getRoomID()+ "번 방의 " + roomID.getReserverName()+"님께 주문합니다.");
                        canFind=2;
              }
              else if(roomID.equals(temp) && !temp.getCheck())   
                   canFind=1;              
         }
        switch (canFind) {
            case 0:
                System.out.println("주문자를 찾을 수 없습니다!");
                break;
            case 1:
                System.out.println("체크인 하지 않은 고객입니다.");
                System.out.print(roomID.getCheck());
                break;
            default:
                showFood();
                System.out.print("메뉴 ID를 입력해 주세요 : ");
                int OrderMenuID = Integer.parseInt(helper.getUserInput("[0-9]+"));
                Food orderMenu = findMenu(OrderMenuID);
                if(orderMenu == null){
                    System.out.println("메뉴를 찾을 수 없습니다.");
                }
                else{
                    System.out.println(orderMenu.getName() + " 주문 완료!");
                    roomID.addExtraFee(orderMenu.getPrice());
                    helper.writeDBFile(3,reserveSys.getReserveDB());
                    foodReport.addReport("order", orderMenu.getName()+";"+OrderRoomID+";"+Integer.toString(orderMenu.getPrice()));
                }   break;
        }
    }
    
    public Food findMenu(int OrderMenuID){
            Food OrderMenu = new Food(OrderMenuID);
             //비교
             for(Food temp : foodDB){
                 if(OrderMenu.equals(temp)){
                    return temp;
                 }
             }
             return null;
    }
}
