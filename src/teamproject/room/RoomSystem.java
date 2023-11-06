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
        private ArrayList<Room> roomDB;
        
        public void roomInit(){
            roomDB = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                String roomNum = String.format("%04d", i); // 룸 번호 형식 지정 (0001, 0002, ...)
                int pricePerNight = 100;
                Room room = new Room(roomNum, pricePerNight);
                roomDB.add(room);
            }
        }
        
        public void runRoomSys() throws IOException{
            boolean continueReservations = true;
            
            while (continueReservations) {
                SystemHelper helper = new SystemHelper();
                System.out.println("1. show all rooms");
                System.out.println("2. show avaliable rooms ");
                System.out.println("3. Exit");
                String choice;
                do{
                    choice = helper.getUserInput();
                }while(!helper.CheckFormat(choice,"[1-3]"));
                int choose = Integer.parseInt(choice);
                switch (choose) {
                    case 1:
                        for(int i = 0; i < 100; i++){
                            Room temp = roomDB.get(i);
                            temp.printRoomInfo();
                        }
                        break;
                    case 2:
                       for(int i = 0; i < 100; i++){
                            Room temp = roomDB.get(i);
                            if(temp.isReserved){
                                temp.printRoomInfo();
                            }
                        }
                        break;
                    case 3:
                        continueReservations = false;
                        break;
                }
            }
        }
}
