/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.report;

import java.awt.Container;
import java.io.IOException;
import teamproject.SystemHelper;
import java.util.ArrayList;
import javax.swing.*;
/**
 * @author 박상현
 */
public class ReportSystem extends JFrame{
    private int findType = 0;          //1: 로그인 관련  2: 주문 관련 3: 예약 관련 
    private SystemHelper helper;
    private ArrayList<Report> ReportDB;
    
    public ReportSystem(){
    
    }
    //기본 설정 
    public void ReportSystemInit() throws IOException {
        ReportDB = new ArrayList<>();
        helper = new SystemHelper();
        helper.createDBFile(4, "report");
        //기존에 있는 로그파일 읽고 다시 쓰는 부분
        for(String readContext : helper.readDBFile(4)){
            Report temp= new Report(true,readContext.split(";",2)[0],readContext.split(";",2)[1]);
            ReportDB.add(temp);
        }
    }
    
    //ReportSystem이 하는 일 (보고서 읽기)
    public void runReportSystem() throws IOException{
        //스윙 코드
         setTitle("보고서");
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         Container swing_Context = getContentPane();
         swing_Context.setLayout(null); //절대 위치 사용
         //버튼 객체 생성
        JButton loginButton = new JButton("로그인 보고서");
        JButton orderButton = new JButton("주문 보고서");
        JButton menuButton = new JButton("메뉴 보고서");
        JButton reservationButton = new JButton("예약 보고서");
        //위치 설정
        loginButton.setLocation(50,40);
        orderButton.setLocation(50,80);
        menuButton.setLocation(50,120);
        reservationButton.setLocation(50,160);
        //크기 설정
        loginButton.setSize(200,30);
        orderButton.setSize(200,30);
        menuButton.setSize(200,30);
        reservationButton.setSize(200,30);
        //버튼을 스윙에 추가
        swing_Context.add(loginButton);
        swing_Context.add(orderButton);
        swing_Context.add(menuButton);
        swing_Context.add(reservationButton);
        //크기랑 보이기 설정
        setSize(300,300);
        setVisible(true);
        //버튼별 이벤트
        loginButton.addActionListener(event -> {
             findType = 1;
             showReport(findType);
         });
        orderButton.addActionListener(event -> {
             findType = 2;
             showReport(findType);
         });
        menuButton.addActionListener(event -> {
             findType = 3;
             showReport(findType);
         });
        reservationButton.addActionListener(event -> {
             findType = 4;
             showReport(findType);
         });
         
         //기존에 있는 함수
        /*System.out.println("1: 로그인 보고서 2: 주문 보고서 3: 메뉴 보고서 4: 예약 보고서");
         findType = Integer.parseInt(helper.getUserInput("[1-4]"));*/
         
    }
    
    public void showReport(int findType){
        if(ReportDB.isEmpty()){
                System.out.println("로그가 아직 없습니다.");
         }
         else{
            System.out.println("\n==============================================================================================");
            //로그 출력
            String findTypeS;
            String reportTypeS = "";
            switch (findType) {
                case 1:
                    findTypeS = "login";
                    reportTypeS = "로그인 ";
                    break;
                case 2:
                    findTypeS = "order";
                    reportTypeS = "주문 ";
                    break;
                case 3:
                    findTypeS = "menu";
                    reportTypeS = "메뉴 ";
                    break;
                case 4:
                    findTypeS = "reserve";
                    reportTypeS = "예약 ";
                    break;
                default:
                    findTypeS = "";
                    break;
            }
            //스윙 만들기
            setTitle(reportTypeS+"보고서");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Container swing_Context = getContentPane();
            //나가기 버튼
            JButton reportExit = new JButton("나가기");
            reportExit.setLocation(50,200);
            reportExit.setSize(150,30);
            swing_Context.add(reportExit);
            reportExit.addActionListener(event -> {
                setVisible(false);
            });
            //DB 내용 읽기
            for(Report temp  : ReportDB){
                //형식에 맞는 로그 출력
                  if(temp.getReportType().equals(findTypeS))
                    System.out.printf("%s;%s\n",temp.getReportType(),temp.getReportData());
            }
            System.out.println("\n==============================================================================================");
            //크기랑 보이기 설정
            setSize(300,300);
            setVisible(true);
        } 
    }
    
    //로그 파일에 적을 내용 추가
    public void addReport(String reportType, String Data)throws IOException{
        Report tmp = new Report(false,reportType,Data);
        ReportDB.add(tmp);
        helper.writeDBFile(4, ReportDB);
    }
}
