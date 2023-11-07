/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package teamproject.reservation;

import java.io.IOException;

/**
 *
 * @author qkekd
 */
public class TestDrive {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ReservationSystem RS = new ReservationSystem();
        for(int i = 0; i < 4; i++){
        RS.addReservation();
        RS.showAllReservation();
        }
    }
    
}
