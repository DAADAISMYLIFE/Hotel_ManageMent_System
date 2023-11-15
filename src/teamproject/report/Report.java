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
    private String reportData;     //로그 입력 내용: 타입;이름;번호 -형태

    public Report(){
        this.reportType = 0;
        this.reportData = "(reservation/order);(LogData)";
    }
    
    public Report(int setReportType, String Data){
    reportType = setReportType;
        switch (reportType) {
            case 3:
                reportData = "reservation;" + Data;
                break;
            case 2:
                reportData = "order;" + Data;
                break;
            case 1:
                reportData = "login;" + Data;
                break;
            case 4:
                reportData = "menu;" + Data;
                break;
            default:
                break;
        }
  }
    public int getReportType(){
        return this.reportType;
    }
    public String getReportData(){
        return this.reportData;
    }
    
}
