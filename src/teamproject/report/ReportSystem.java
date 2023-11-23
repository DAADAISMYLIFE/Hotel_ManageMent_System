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
import javax.swing.table.DefaultTableModel;
import teamproject.IntegrateManager;

/**
 * @author 박상현
 */
public class ReportSystem extends JFrame {

    private int findType = 0;          //1: 로그인 관련  2: 주문 관련 3: 예약 관련 
    private SystemHelper helper;
    private ArrayList<Report> ReportDB;
    private JTable reportTable;            //보고서 작성 테이블
    public DefaultTableModel defaultReportTableModel; //처음 테이블 형식
    public DefaultTableModel reportTableModel;          //보고서 테이블의 형식
    public JFrame reportContext;       //보고서용 프레임 

    public ReportSystem() {
    }            //기본 생성자

    //기본 설정 
    public void ReportSystemInit() throws IOException {
        ReportDB = new ArrayList<>();
        helper = new SystemHelper();
        helper.createDBFile(4, "report");
        //기존에 있는 로그파일 읽고 다시 쓰는 부분
        for (String readContext : helper.readDBFile(4)) {
            Report temp = new Report(true, readContext.split(";", 2)[0], readContext.split(";", 2)[1]);
            ReportDB.add(temp);
        }
    }

    //ReportSystem이 하는 일 (보고서 읽기)
    public void runReportSystem() throws IOException {
        //처음 보고서 테이블 양식
        defaultReportTableModel = new DefaultTableModel();  //아무것도 없는 model
       
        reportTable = new JTable(defaultReportTableModel);
        //프레임 코드
        setTitle("보고서");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container swing_Context = getContentPane();
        swing_Context.setLayout(null); //절대 위치 사용
        //버튼 객체 생성
        JButton loginButton = new JButton("로그인 보고서");
        JButton orderButton = new JButton("주문 보고서");
        JButton menuButton = new JButton("메뉴 보고서");
        JButton reservationButton = new JButton("예약 보고서");
        JButton quitReport = new JButton("나가기");
        //위치 설정
        loginButton.setLocation(20, 30);
        orderButton.setLocation(20, 80);
        menuButton.setLocation(20, 130);
        reservationButton.setLocation(20, 180);
        quitReport.setLocation(20, 230);
        reportTable.setLocation(250, 20);
        //크기 설정
        loginButton.setSize(200, 30);
        orderButton.setSize(200, 30);
        menuButton.setSize(200, 30);
        reservationButton.setSize(200, 30);
        quitReport.setSize(200, 30);
        reportTable.setSize(700, 300);
        //버튼을 프레임에 추가
        swing_Context.add(loginButton);
        swing_Context.add(orderButton);
        swing_Context.add(menuButton);
        swing_Context.add(reservationButton);
        swing_Context.add(quitReport);
        swing_Context.add(reportTable);
        //프레임 초기 위치 설정밒
        //setLocationRelativeTo(null);
        //크기랑 보이기 설정
        setSize(1000, 400);
        setLocationRelativeTo(null);
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
        quitReport.addActionListener(event -> {
            swing_Context.remove(reportTable);
            this.dispose();
            IntegrateManager.frm.setVisible(true);
        });
    }

    public void showReport(int findType) {
        reportTableModel = new DefaultTableModel(){
        public boolean isCellEditable(int rowIndex, int mColIndex) {
                    return false;
        }};             //다른곳에서 model에 colum이랑 Row 추가할 예정
        if (ReportDB.isEmpty()) {
            JOptionPane.showMessageDialog(null, "로그가 아직 없습니다.");
        } else {
            String findTypeS;
            //보고서 양식(model) 설정
            switch (findType) {
                case 1:
                    findTypeS = "login";    //로그인 관련 기록
                    reportTableModel.addColumn("사용자");
                    reportTableModel.addColumn("접속 여부"); //로그인/로그아웃
                    reportTableModel.addColumn("접속 시간");
                    break;
                case 2:
                    findTypeS = "order";  //주문 관련 기록
                    reportTableModel.addColumn("호실");
                    reportTableModel.addColumn("음식 이름");
                    reportTableModel.addColumn("음식 가격");
                    reportTableModel.addColumn("주문 시간");
                    break;
                case 3:
                    findTypeS = "menu"; //메뉴 관련 기록
                    reportTableModel.addColumn("메뉴 이름");
                    reportTableModel.addColumn("가격");
                    reportTableModel.addColumn("타입(?)"); //추가/삭제
                    reportTableModel.addColumn("변경 시간");
                    break;
                case 4:
                    findTypeS = "reserve";  //예약 관련 기록
                    reportTableModel.addColumn("예약자");
                    reportTableModel.addColumn("호실");
                    reportTableModel.addColumn("체크인 날짜");
                    reportTableModel.addColumn("체크아웃 날짜");
                    reportTableModel.addColumn("예약 여부"); //예약 추가/삭제
                    reportTableModel.addColumn("예약 추가 시간");
                    break;
                default:
                    findTypeS = "";
                    break;
            }
            //DB 내용 읽기
            for (Report tmp : ReportDB) {
                //형식에 맞는 로그 출력
                if (tmp.getReportType().equals(findTypeS)) {
                    //table과 연동하여 보여주기
                    switch (findTypeS) {
                        case "login":
                            reportTableModel.addRow(new String[]{tmp.getReportData().split(";")[0], tmp.getReportData().split(";")[1], tmp.getReportData().split(";")[2]});
                            break;
                        case "order":
                            reportTableModel.addRow(new String[]{tmp.getReportData().split(";")[1]+"호", tmp.getReportData().split(";")[0], tmp.getReportData().split(";")[2]+"원", tmp.getReportData().split(";")[3]});
                            break;
                        case "menu":
                            reportTableModel.addRow(new String[]{tmp.getReportData().split(";")[1], tmp.getReportData().split(";")[2]+"원", tmp.getReportData().split(";")[0], tmp.getReportData().split(";")[3]});
                            break;
                        case "reserve":
                            if (!(tmp.getReportData().split(";")[0].equals("체크인") || tmp.getReportData().split(";")[0].equals("체크아웃"))) {
                                reportTableModel.addRow(new String[]{tmp.getReportData().split(";")[1], tmp.getReportData().split(";")[2]+"호", tmp.getReportData().split(";")[3], tmp.getReportData().split(";")[4], tmp.getReportData().split(";")[0], tmp.getReportData().split(";")[5]});
                            } else {
                                reportTableModel.addRow(new String[]{tmp.getReportData().split(";")[1],tmp.getReportData().split(";")[2]+"호","","",tmp.getReportData().split(";")[0], tmp.getReportData().split(";")[3]});
                            }
                            break;
                        default:
                            break;
                    }
                }
            }

            reportTable.setModel(reportTableModel); //Table model 바꾸기
            
        }

    }

    //로그 파일에 적을 내용 추가
    public void addReport(String reportType, String Data) throws IOException {
        Report tmp = new Report(false, reportType, Data);
        ReportDB.add(tmp);
        helper.writeDBFile(4, ReportDB);
    }
}
