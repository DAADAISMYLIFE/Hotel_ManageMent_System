/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.report;

import java.util.Objects;

/**
 *
 * @author 박상현
 */
public class Report {
    private String reportType;          //로그 타입 1: 예약 2: 주문
    private String reportData;     //로그 입력 내용: 타입;이름;번호 -형태

    public Report(){
        this("Type","Context");
    }
    
    public Report(String reportType, String data){
        this.reportType = reportType;
        this.reportData = data;
  }
    public String getReportType(){
        return this.reportType;
    }
    public String getReportData(){
        return this.reportData;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Report other = (Report) obj;
        return Objects.equals(this.reportType, other.reportType);
    }
    
    
    
    
    
}
