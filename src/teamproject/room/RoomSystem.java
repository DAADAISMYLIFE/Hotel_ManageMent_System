/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.room;

import java.io.IOException;
import java.util.ArrayList;
import teamproject.SystemHelper;

/**
 *
 * @author qkekd
 */
public class RoomSystem {
        public ArrayList<Room> roomDB;
        
        public void roomInit(){
            roomDB = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                int roomNumer = (i/10+1)*100 + i%10;
                String roomNum = String.format("%03d", roomNumer); // 룸 번호 형식 지정 (0001, 0002, ...)
                int pricePerNight = 100;
                Room room = new Room(roomNum, pricePerNight);
                roomDB.add(room);
            }
        }
        
        public void showAllRoom(){
            for(Room temp : roomDB){
                temp.printRoomInfo();
           }
        }
        
        public void showRoom(int index){
            roomDB.get(index).printRoomInfo();
        }
}
