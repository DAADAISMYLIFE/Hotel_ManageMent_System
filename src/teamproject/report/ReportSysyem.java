/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.report;

import java.io.IOException;
import teamproject.SystemHelper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 * @author 박상현
 */
public class ReportSysyem {
    private int reportType;                                                                                         //1: 예약 관련 2: 주문 관련
    private SystemHelper helper;
    private ArrayList<Report> ReportDB;    

    //기본 설정 
    public void ReportSystemInit() throws IOException {
        ReportDB = new ArrayList<>();
        helper = new SystemHelper();
        helper.createDBFile(5, "report");
        helper.writeDBFile(4, ReportDB);
    }
    
    //ReportSystem이 하는 일 (보고서 읽기)
    public void runReportSystem() throws IOException{
        boolean isCorrect = false;
        do{
        System.out.println("어떤 보고서를 보고싶으신가요? 1: 예약 보고서 2: 주문 보고서");
         BufferedReader bufRead = new BufferedReader(new InputStreamReader(System.in));
         reportType = Integer.parseInt(bufRead.readLine());
         if(reportType == 1){
         helper.readDBFile(4);
         isCorrect = true;
         }
         else if (reportType == 2){
         helper.readDBFile(5);
         isCorrect = true;
         }
         else{
                System.out.println("올바른 값을 입력해 주세요");
         }
        }while(!isCorrect);
    }
}
