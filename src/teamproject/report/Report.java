/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.report;

/**
 *
 * @author 박상현
 */
public class Report {
    private int reportType;          //로그 타입 1: 예약 2: 주문
    private String reportData;     //로그 입력 내용

    
    public Report(){
        this.reportType = 0;
        this.reportData = "(reservation/order);(LogData)";
    }
    
    public Report(int setReportType, String Data){
    reportType = setReportType;
    if(reportType == 3){
        reportData = "reservation;" + Data;
    }
    else if (reportType == 2){
        reportData = "order;" + Data;
    }
    else if (reportType == 1){
        reportData = "login;" + Data;
    }
  }
    public int getReportType(){
        return this.reportType;
    }
    public String getReportData(){
        return this.reportData;
    }
    
}
