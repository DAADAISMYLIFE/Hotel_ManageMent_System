package teamproject.room;

public class Room {
    String roomNumber;   // 방 번호
    boolean isReserved;  // 예약 상태
    //String guestName;    // 예약자 이름
    int numberOfGuests;  // 투숙객 수
    int pricePerNight;   // 박당 가격

    public Room(String roomNumber, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.pricePerNight = pricePerNight;
        this.isReserved = false;
        //this.guestName = "";
        this.numberOfGuests = 0;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    // 룸 정보 출력
    public void printRoomInfo() {
        System.out.print("Room Number: " + roomNumber);
        System.out.print("\tPrice Per Night: $" + pricePerNight);
        System.out.print("\t\tisReserved: " + isReserved);
        System.out.println("\tNumber of Guests: " + numberOfGuests);
        System.out.println("");
    }
}
