/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.report;

import java.util.Objects;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author 박상현
 */
public class Report {
    private String reportType;          //로그 타입 1: 예약 2: 주문
    private String reportData;     //로그 입력 내용: 타입;이름;번호 -형태
    LocalDate logDate = LocalDate.now();
    LocalTime logTimes = LocalTime.now();
    //private String logDateFormat = logDate.format(DateTimeFormatter.ofPattern("yyyy/mm/dd"));
    private String logYear = Integer.toString(logDate.getYear());
    private String logMonth = Integer.toString(logDate.getMonthValue());
    private String logDay = Integer.toString(logDate.getDayOfMonth());
    private String logTime =Integer.toString(logTimes.getHour()) + ":"+  Integer.toString(logTimes.getMinute());

    //로그 작성시 사용할 생성자
    public Report(boolean first ,String reportType, String data){
        //기존의 로그 파일 읽고 객체로 만들 때 사용
        if (first){
            this.reportType = reportType;
            this.reportData = data ;
        }
        //시스템에서 로그 작성시 사용
        else{
            this.reportType = reportType;
            this.reportData = data + ";"+ logYear + "/"+ logMonth+"/"+logDay + "/" + logTime;
        }
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
