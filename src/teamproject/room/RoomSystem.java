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
                    pricePerNight = 50;
                    type = "Standard Room";
                    MaxiumGuest = 2;
                }
                else if(20<=i && i<40){ // 3~4층 : 더블, 70$, 최대 2명
                    pricePerNight = 70;
                    type = "Double Room";
                    MaxiumGuest = 2;
                }
                else if(40<=i && i<60){ // 5~6층 : 트윈, 70$, 최대 2명
                    pricePerNight = 70;
                    type = "Twin Room";
                    MaxiumGuest = 2;
                }
                else if(60<=i && i<80){ // 7~8층 : 트리플, 90$, 최대 3명
                    pricePerNight = 90;
                    type = "Triple Room";
                    MaxiumGuest = 3;
                }
                else{ // 9~10층 : 슈페리얼, 120$, 최대 4명
                    pricePerNight = 120;
                    type = "Superior Room";
                    MaxiumGuest = 4;
                }
                
                Room room = new Room(roomNum, type, pricePerNight,MaxiumGuest);
                roomDB.add(room);
                helper.createDBFile(1, "room");
                helper.readDBFile(1);
            }
        }
        
        public void showAllRoom() throws IOException{
            for(int i = 0; i < 10; i++){
                System.out.println("\n======================================================================예약====================================================================");
                for(int j = 0; j < 10; j++){
                    Room temp = roomDB.get(i*10 + j);
                    temp.printRoomInfo();
                }

                System.out.println("===============================================================================================================================================\n");
                System.out.print("\n다음 층 보기 <y / n> : ");
                if(helper.getUserInput("[y,n]").equals("n")){
                    break;
                }
            }
        }
        
        public void showRoom(int index){
            roomDB.get(index).printRoomInfo();
        }        
        
//        public void showTypeRoom() throws IOException{
//            System.out.println("검색할 객실 타입을 선택하세요");
//            System.out.println("1. Standard Room");
//            System.out.println("2. Double Room");
//            System.out.println("3. Twin Room");
//            System.out.println("4. Triple Room");
//            System.out.println("5. Superior Room");
//            
//           String selectedMenuS;
//            do{
//                selectedMenuS =  helper.getUserInput();
//            }while(!helper.CheckFormat(selectedMenuS,"[1-5]"));
//            int selectedMenu =Integer.parseInt(selectedMenuS);
//            
//            switch (selectedMenu) {
//                case 1:
//                    showStandard();
//                    
//                case 2:
//                    
//                    break;
//                case 3:
//                    
//                case 4:
//                    
//                    break;
//                case 5:
//                    break;
//                
//            }
//        }

//        public void showStandard(){
//            for(int i = 0; i < 2; i++){
//                System.out.println("\n======================================================================예약======================================================================");
//                for(int j = 0; j < 10; j++){
//                    Room temp = roomDB.get(i*10 + j);
//                    temp.printRoomInfo();
//                }
//                System.out.println("===============================================================================================================================================\n");
//              }
//        }
}
