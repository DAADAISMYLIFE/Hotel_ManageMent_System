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
    private int findType;          //1: 로그인 관련  2: 주문 관련 3: 예약 관련 
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
        System.out.println("1: 로그인 보고서 2: 주문 보고서 3: 메뉴 보고서 4: 예약 보고서");
         findType = Integer.parseInt(helper.getUserInput("[1-4]"));
         
         showReport(findType);
         
    }
    
    public void showReport(int findType){
        if(ReportDB.isEmpty()){
                System.out.println("로그가 아직 없습니다.");
         }
         else{
            System.out.println("\n==============================================================================================");
            //로그 출력
            String findTypeS;
            switch (findType) {
                case 1:
                    findTypeS = "login";
                    break;
                case 2:
                    findTypeS = "order";
                    break;
                case 3:
                    findTypeS = "menu";
                    break;
                case 4:
                    findTypeS = "reserve";
                    break;
                default:
                    findTypeS = "";
                    break;
            }
            for(Report temp  : ReportDB){
                //형식에 맞는 로그 출력
                  if(temp.getReportType().equals(findTypeS))
                    System.out.printf("%s;%s\n",temp.getReportType(),temp.getReportData());
            }
            System.out.println("\n==============================================================================================");
         } 
    }
    //로그 파일에 적을 내용 추가
    public void addReport(String reportType, String Data)throws IOException{
        Report tmp = new Report(false,reportType,Data);
        ReportDB.add(tmp);
        helper.writeDBFile(4, ReportDB);
    }
}
