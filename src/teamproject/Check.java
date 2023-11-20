/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject;
import java.io.IOException;
import java.util.ArrayList;
import teamproject.SystemHelper;
import teamproject.room.RoomSystem;
import teamproject.room.Room;
import teamproject.report.ReportSystem;
import teamproject.reservation.ReservationSystem;
import teamproject.reservation.ReservedInfo;
/**
 *
 * @author kgb69
 */
public class Check {
     private ReservationSystem reserveSys;
    
    private SystemHelper helper = new SystemHelper();
    
    public Check(ReservationSystem reserveSys){
        this.reserveSys = reserveSys;
    }
    public void C_S_run() throws IOException{
        
        OUTER:
        while(true){
         String rex = "[0-2]";
        helper.showTodayDate();
        System.out.println("==============================================");
        System.out.println("1. 체크 인 ");
        System.out.println("2. 체크 아웃");
        System.out.println("0. 종료");
        System.out.println("==============================================");
        
        String selectedMenuS;
        do{
            selectedMenuS =  helper.getUserInput();
         }while(!helper.CheckFormat(selectedMenuS,rex));
        int selectedMenu =Integer.parseInt(selectedMenuS);
        
        switch (selectedMenu) {
            case 1:
                C_in();
                break;
            case 2:
                C_out();
                break;
            case 0:
                break OUTER;
    }// 체크인 종료
  }
}
    public void C_in() throws IOException{
        reserveSys.showAllReservation();
        reserveSys.Check_in();
         
    }
    
    public void C_out() throws IOException{
        reserveSys.deleteReservation1();
    }
}//클래스 종료

