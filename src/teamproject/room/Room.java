package teamproject.room;

public class Room {
    String roomNumber;   // 방 번호
    boolean isReserved;  // 예약 상태
    //String guestName;    // 예약자 이름
    int numberOfGuests;  // 투숙객 수
    int pricePerNight;   // 박당 가격
    int year;
    int month;
    int day;

    public Room(String roomNumber, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.pricePerNight = pricePerNight;
        this.isReserved = false;
        //this.guestName = "";
        this.numberOfGuests = 0;
    }

    /*// 예약하기
    public boolean reserveRoom(String guestName, int numberOfGuests) {
        if (!isReserved) {
            this.guestName = guestName;
            this.numberOfGuests = numberOfGuests;
            this.isReserved = true;
            return true;  // 예약 성공
        } else {
            return false; // 예약 실패
        }
    }

    // 예약 취소하기
    public void cancelReservation() {
        this.guestName = "";
        this.numberOfGuests = 0;
        this.isReserved = false;
    }*/

    // 룸 정보 출력
    public void printRoomInfo() {
        System.out.print("Room Number: " + roomNumber);
        System.out.print("\tPrice Per Night: $" + pricePerNight);
        System.out.print("\t\tisReserved: " + isReserved);
        System.out.println("\tNumber of Guests: " + numberOfGuests);
        System.out.println("");
    }
}
