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
        private SystemHelper helper;
        
        public void roomInit() throws IOException{
            roomDB = new ArrayList<>();
            helper = new SystemHelper();
            for (int i = 0; i < 100; i++) {
                int roomNumer = (i/10+1)*100 + i%10+1;
                String roomNum = String.format("%03d", roomNumer); // 룸 번호 형식 지정 (0001, 0002, ...)
                int pricePerNight;
                int MaxiumGuest;
                String type;
                
                if(0<=i && i<20){ // 1-2층 : 스탠다드, 50$, 최대 2명
                    pricePerNight = 50000;
                    type = "Standard Room";
                    MaxiumGuest = 2;
                }
                else if(20<=i && i<40){ // 3~4층 : 더블, 70$, 최대 2명
                    pricePerNight = 70000;
                    type = "Double Room";
                    MaxiumGuest = 2;
                }
                else if(40<=i && i<60){ // 5~6층 : 트윈, 70$, 최대 2명
                    pricePerNight = 70000;
                    type = "Twin Room";
                    MaxiumGuest = 2;
                }
                else if(60<=i && i<80){ // 7~8층 : 트리플, 90$, 최대 3명
                    pricePerNight = 90000;
                    type = "Triple Room";
                    MaxiumGuest = 3;
                }
                else{ // 9~10층 : 슈페리얼, 120$, 최대 4명
                    pricePerNight = 120000;
                    type = "Superior Room";
                    MaxiumGuest = 4;
                }
                
                Room room = new Room(roomNum, type, pricePerNight,MaxiumGuest);
                roomDB.add(room);
                helper.createDBFile(1, "room");
                helper.readDBFile(1);
            }
        }
}
