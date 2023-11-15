/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.report;

import java.io.IOException;
import teamproject.SystemHelper;
import java.util.ArrayList;
/**
 * @author 박상현
 */
public class ReportSystem {
    private int reportType;                                                                                         //1: 예약 관련 2: 주문 관련
    private SystemHelper helper;
    private ArrayList<Report> ReportDB;
    
    public ReportSystem(){
    
    }
    //기본 설정 
    public void ReportSystemInit() throws IOException {
         ReportDB = new ArrayList<>();
        helper = new SystemHelper();
        helper.createDBFile(4, "report");
        /*//log.txt에 있는 내용을 ReportDB로 가져옴
        for(String readContext : helper.readDBFile(4)){
            int type;
            if((readContext.split(";")[0]).equals("login")){
                type = 1;
            }
            else if ((readContext.split(";")[0]).equals("order")){
                type = 2;
            }
            else{
                type = 3;
            }
            Report temp= new Report(type,(readContext.split(";")[1]) + (readContext.split(";")[2]));
            ReportDB.add(temp);
            Addedlog++;
        }*/
       // helper.writeDBFile(4, ReportDB);
    }
    
    //ReportSystem이 하는 일 (보고서 읽기)
    public void runReportSystem() throws IOException{
        System.out.println("어떤 보고서를 보고싶으신가요? 1: 로그인 보고서 2: 주문 보고서 3: 예약 보고서");
         reportType = Integer.parseInt(helper.getUserInput("[1-3]"));
         // 다른 시스템에서 DB를 변경하였기 때문에 ReportSystemInit()의 DB를 최신화 시켜야 함
         //log.txt에 있는 내용을 ReportDB로 가져옴
        for(String readContext : helper.readDBFile(4)){
            int type;
            if((readContext.split(";")[0]).equals("login")){
                type = 1;
            }
            else if ((readContext.split(";")[0]).equals("order")){
                type = 2;
            }
            else{
                type = 3;
            }
            Report temp= new Report(type,(readContext.split(";")[1]) +";"+ (readContext.split(";")[2]));
            ReportDB.add(temp);
        }
         //---------------------------------------------------------------------------------
         if(ReportDB.isEmpty()){
                System.out.println("로그가 아직 없습니다.");
         }
         else{
            System.out.println("\n==============================================================================================");
            //로그 출력
            for(int i = 0;i < ReportDB.size(); i++){
                if(reportType == 3 && ReportDB.get(i).getReportData().contains("reservation") ){   //예약 기록
                  System.out.printf("%s\n", ReportDB.get(i).getReportData());
                }
                else if (reportType == 2 && ReportDB.get(i).getReportData().contains("order")){              //주문 기록
                    //주문 로그
                    System.out.printf("%s\n", ReportDB.get(i).getReportData());
                }
                else if(reportType == 1 && ReportDB.get(i).getReportData().contains("login")){               //로그인 기록
                 System.out.printf("%s\n", ReportDB.get(i).getReportData());
                }
            }
            System.out.println("==============================================================================================");
         } 
    }
    //로그 파일에 적을 내용 추가
    public void addReport(int reportType, String Data)throws IOException{
        Report tmp = new Report(reportType,Data);
        ReportDB.add(tmp);
        helper.writeDBFile(4, ReportDB);
    }
}
