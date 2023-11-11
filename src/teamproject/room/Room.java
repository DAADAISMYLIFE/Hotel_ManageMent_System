package teamproject.room;

public class Room {
    String roomNumber;   // 방 번호
    String roomtype; // 방타입
    int numberOfGuests;  // 투숙객 수
    int pricePerNight;   // 박당 가격

    public Room(String roomNumber, String roomtype, int pricePerNight, int numberOfGuests) {
        this.roomNumber = roomNumber;
        this.pricePerNight = pricePerNight;
        this.roomtype = roomtype;
        this.numberOfGuests = numberOfGuests;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    // 룸 정보 출력
    public void printRoomInfo() {
        System.out.print("Room Number: " + roomNumber);
        System.out.print("\t\tType: " + roomtype);
        System.out.print("\t\tPrice Per Night: $" + pricePerNight);
        System.out.println("\t\tMaximum Guests: " + numberOfGuests);
    }
}
