package teamproject.room;

import java.util.ArrayList;
import java.util.Scanner;

public class Room {
    String roomNumber;   // 방 번호
    boolean isReserved;  // 예약 상태
    String guestName;    // 예약자 이름
    int numberOfGuests;  // 투숙객 수
    int pricePerNight;   // 박당 가격

    public Room(String roomNumber, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.pricePerNight = pricePerNight;
        this.isReserved = false;
        this.guestName = "";
        this.numberOfGuests = 0;
    }

    // 예약하기
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
    }

    // 룸 정보 출력
    public void printRoomInfo() {
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Price Per Night: $" + pricePerNight);
        if (isReserved) {
            System.out.println("Reserved for: " + guestName);
            System.out.println("Number of Guests: " + numberOfGuests);
        } else {
            System.out.println("Room is not reserved.");
        }
    }

    public static void main(String[] args) {
        ArrayList<Room> hotelFloor = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            String roomNum = String.format("%04d", i); // 룸 번호 형식 지정 (0001, 0002, ...)
            int pricePerNight = 100;
            Room room = new Room(roomNum, pricePerNight);
            hotelFloor.add(room);
        }

        
        boolean continueReservations = true;

        while (continueReservations) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("1. Reserve a room");
            System.out.println("2. Cancel reservation");
            System.out.println("3. Exit");
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter room number: ");
                    int roomNumber = scanner.nextInt();
                    System.out.print("Enter guest name: ");
                    scanner.nextLine(); // Consume newline character
                    String guestName = scanner.nextLine();
                    System.out.print("Enter number of guests: ");
                    int numGuests = scanner.nextInt();

                    if (roomNumber >= 1 && roomNumber <= hotelFloor.size()) {
                        Room room = hotelFloor.get(roomNumber - 1);
                        boolean reservationSuccess = room.reserveRoom(guestName, numGuests);
                        if (reservationSuccess) {
                            System.out.println("Reservation successful!");
                        } else {
                            System.out.println("Reservation failed. Room is already reserved.");
                        }
                    } else {
                        System.out.println("Invalid room number.");
                    }
                    break;
                case 2:
                    System.out.print("Enter room number to cancel reservation: ");
                    int cancelRoomNumber = scanner.nextInt();

                    if (cancelRoomNumber >= 1 && cancelRoomNumber <= hotelFloor.size()) {
                        Room room = hotelFloor.get(cancelRoomNumber - 1);
                        room.cancelReservation();
                        System.out.println("Reservation canceled.");
                    } else {
                        System.out.println("Invalid room number.");
                    }
                    break;
                case 3:
                    continueReservations = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                    break;
            }
        }
    }
}
