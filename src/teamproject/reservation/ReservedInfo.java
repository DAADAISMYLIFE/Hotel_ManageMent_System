/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.reservation;

import java.util.Objects;
import teamproject.SystemHelper;

/**
 *
 * @author qkekd
 * 예약 정보를 담고 있는 클래스
 */
public class ReservedInfo {
    private SystemHelper helper = new SystemHelper();
    
    private String roomID;
    private String reserverName;
    private int numOfGuests;
    
    private int startYear;
    private int startMonth;
    private int startDay;
    private int endYear;
    private int endMonth;
    private int endDay;
    
    private int totalRoomFee;
    private int extraFee;

    public ReservedInfo(){
        this("","",0,0,0,0,0,0,0);
    }
    
    public ReservedInfo(String roomID){
        this(roomID,"",0,0,0,0,0,0,0);
    }
    
    public ReservedInfo(String roomID,String reserverName,int startYear, int startMonth, int startDay){
        this(roomID,reserverName,0,startYear,startMonth,startDay,0,0,0);
    }
    
    public ReservedInfo(String roomID, String reserverName, int numOfGuests, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        this.roomID = roomID;
        this.reserverName = reserverName;
        this.numOfGuests = numOfGuests;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.endYear = endYear;
        this.endMonth = endMonth;
        this.endDay = endDay;
        this.totalRoomFee = 0;
        this.extraFee = 0;
    }
    
    public ReservedInfo(String roomID, String reserverName, int numOfGuests, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay,int totalRoomFee, int extraFee) {
        this.roomID = roomID;
        this.reserverName = reserverName;
        this.numOfGuests = numOfGuests;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.endYear = endYear;
        this.endMonth = endMonth;
        this.endDay = endDay;
        this.totalRoomFee = totalRoomFee;
        this.extraFee = extraFee;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getReserverName() {
        return reserverName;
    }

    public void setReserverName(String reserverName) {
        this.reserverName = reserverName;
    }

    public int getNumOfGuests() {
        return numOfGuests;
    }

    public void setNumOfGuests(int numOfGuests) {
        this.numOfGuests = numOfGuests;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }
    
    public void addExtraFee(int extraFee){
        this.extraFee+=extraFee;
    }

    public int getExtraFee() {
        return extraFee;
    }

    public int getStartDateI(){ 
        return startYear*10000 + startMonth*100 + startDay; 
    }
    
    public int getEndDateI(){
        return endYear*10000 + endMonth*100 + endDay;
    }

    public int getTotalRoomFee() {
        return totalRoomFee;
    }

    public void setTotalRoomFee(int totalRoomFee) {
        this.totalRoomFee = totalRoomFee;
    }
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 3;
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
        final ReservedInfo other = (ReservedInfo) obj;
        return Objects.equals(this.roomID, other.roomID);
    }
    
    public boolean equals(Object obj, int findDateI, boolean isToday) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if(isToday){
            if(((ReservedInfo)obj).getStartDateI()<= findDateI){
                final ReservedInfo other = (ReservedInfo) obj;
                return Objects.equals(this.roomID, other.roomID);
            }
            else{
                return false;
            }
        }
        else{
            if(((ReservedInfo)obj).getStartDateI()== findDateI){
                final ReservedInfo other = (ReservedInfo) obj;
                return Objects.equals(this.roomID, other.roomID);
            }
            else{
                return false;
            }
        }
    }
    
}
