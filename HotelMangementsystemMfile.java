import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class HotelReservationSystem {
    class Room {
        int roomNumber;
        boolean isReserved;

        public Room(int roomNumber) {
            this.roomNumber = roomNumber;
            this.isReserved = false;
        }
    }

    class Reservation {
        int id;
        String guestName;
        Room room;
        String contactNumber;

        public Reservation(int id, String guestName, Room room, String contactNumber) {
            this.id = id;
            this.guestName = guestName;
            this.room = room;
            this.contactNumber = contactNumber;
            this.room.isReserved = true;
        }
    }

    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    public void start(Scanner scanner) {

        for (int i = 1; i <= 5; i++) {
            rooms.add(new Room(i));
        }

        //Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("HOTEL MANAGEMENT SYSTEM");
            System.out.println("Rooms:");
            for (Room room : rooms) {
                System.out.println("Room Number: " + room.roomNumber + ", Reserved: " + (room.isReserved ? "Yes" : "No"));
            }
            System.out.println("1. Reserve room");
            System.out.println("2. View Reservations");
            System.out.println("3. Get Room Number");
            System.out.println("4. Update Reservations");
            System.out.println("5. Delete Reservations");
            System.out.println("6. Add Rooms");
            System.out.println("7. Remove Rooms");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    reserveRoom(scanner);
                    break;
                case 2:
                    viewReservations();
                    break;
                case 3:
                    getRoomNumber(scanner);
                    break;
                case 4:
                    updateReservation(scanner);
                    break;
                case 5:
                    deleteReservation(scanner);
                    break;
                case 6:
                    addRooms(scanner);
                    break;
                case 7:
                    removeRooms(scanner);
                    break;
                case 0:
                    exit();
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void reserveRoom(Scanner scanner) {
        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();
        System.out.print("Enter the number of rooms to reserve: ");
        int numRooms = scanner.nextInt();
        scanner.nextLine();

        List<Room> roomsToReserve = new ArrayList<>();
        for (int i = 0; i < numRooms; i++) {
            System.out.print("Enter room number for reservation " + (i + 1) + ": ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();

            Room room = null;
            for (Room r : rooms) {
                if (r.roomNumber == roomNumber && !r.isReserved) {
                    room = r;
                    break;
                }
            }

            if (room == null) {
                System.out.println("Invalid room number or room is already reserved for reservation " + (i + 1) + ".");
                return;
            }

            roomsToReserve.add(room);
        }

        System.out.print("Enter contact number (11 digits starting with '01'): ");
        String contactNumber = scanner.nextLine();

        if (contactNumber.length() != 11 || !contactNumber.startsWith("01")) {
            System.out.println("Contact number must be 11 digits starting with '01'. Please try again.");
            return;
        }

        for (Room room : roomsToReserve) {
            int id = reservations.size() + 1;
            Reservation reservation = new Reservation(id, guestName, room, contactNumber);
            reservations.add(reservation);
            System.out.println("Reservation successful for room " + room.roomNumber + "! Your reservation ID is: " + id);
        }
    }

    private void addRooms(Scanner scanner) {
        System.out.print("Enter the number of rooms to add: ");
        int numRoomsToAdd = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numRoomsToAdd; i++) {
            System.out.print("Enter room number to add: ");
            int newRoomNumber = scanner.nextInt();
            scanner.nextLine();

            boolean roomExists = false;
            for (Room r : rooms) {
                if (r.roomNumber == newRoomNumber) {
                    System.out.println("Room number already exists.");
                    roomExists = true;
                    break;
                }
            }

            if (!roomExists) {
                Room newRoom = new Room(newRoomNumber);
                rooms.add(newRoom);
                System.out.println("Room added successfully!");
            }
        }
    }

    private void removeRooms(Scanner scanner) {
        System.out.print("Enter the number of rooms to remove: ");
        int numRoomsToRemove = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numRoomsToRemove; i++) {
            System.out.print("Enter room number to remove: ");
            int roomNumberToRemove = scanner.nextInt();
            scanner.nextLine();

            Room roomToRemove = null;
            for (Room r : rooms) {
                if (r.roomNumber == roomNumberToRemove) {
                    roomToRemove = r;
                    break;
                }
            }

            if (roomToRemove != null) {
                if (roomToRemove.isReserved) {
                    System.out.println("Cannot remove a reserved room.");
                } else {
                    rooms.remove(roomToRemove);
                    System.out.println("Room removed successfully!");
                }
            } else {
                System.out.println("Room not found.");
            }
        }
    }


    private void viewReservations() {
        System.out.println("Current Reservations:");
        for (Reservation reservation : reservations) {
            System.out.println("Reservation ID: " + reservation.id);
            System.out.println("Guest Name: " + reservation.guestName);
            System.out.println("Room Number: " + reservation.room.roomNumber);
            System.out.println("Contact Number: " + reservation.contactNumber);
            System.out.println();
        }
    }

    private void getRoomNumber(Scanner scanner) {
        System.out.print("Enter reservation ID: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine();

        for (Reservation reservation : reservations) {
            if (reservation.id == reservationId) {
                System.out.println("Room number for Reservation ID " + reservationId +
                        " and Guest " + reservation.guestName + " is: " + reservation.room.roomNumber);
                return;
            }
        }

        System.out.println("Reservation not found for the given ID.");
    }

    private void updateReservation(Scanner scanner) {
        System.out.print("Enter reservation ID to update: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine();

        for (Reservation reservation : reservations) {
            if (reservation.id == reservationId) {
                System.out.print("Enter new guest name: ");
                reservation.guestName = scanner.nextLine();
                System.out.print("Enter new room number: ");
                int newRoomNumber = scanner.nextInt();
                scanner.nextLine();
                Room newRoom = null;
                for (Room r : rooms) {
                    if (r.roomNumber == newRoomNumber && !r.isReserved) {
                        newRoom = r;
                        break;
                    }
                }
                if (newRoom == null) {
                    System.out.println("Invalid room number or room is already reserved.");
                    return;
                }
                reservation.room.isReserved = false;
                reservation.room = newRoom;
                reservation.room.isReserved = true;
                System.out.print("Enter new contact number (11 digits starting with '01'): ");
                String contactNumber = scanner.nextLine();


                if (contactNumber.length() != 11 || !contactNumber.startsWith("01")) {
                    System.out.println("Contact number must be 11 digits starting with '01'. Please try again.");
                    return;
                }

                System.out.println("Reservation updated successfully!");
                return;
            }
        }

        System.out.println("Reservation not found for the given ID.");
    }

    private void deleteReservation(Scanner scanner) {
        System.out.print("Enter the number of reservations to delete: ");
        int numReservations = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numReservations; i++) {
            System.out.print("Enter reservation ID to delete for reservation " + (i + 1) + ": ");
            int reservationId = scanner.nextInt();
            scanner.nextLine();

            Reservation reservation = null;
            for (Reservation r : reservations) {
                if (r.id == reservationId) {
                    reservation = r;
                    break;
                }
            }

            if (reservation != null) {
                reservation.room.isReserved = false;
                reservations.remove(reservation);
                System.out.println("Reservation " + reservationId + " deleted successfully!");
            } else {
                System.out.println("Reservation not found for the given ID.");
            }
        }
    }


    private void exit() {
        System.out.print("Exiting System");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i--;
        }
        System.out.println();
        System.out.println("ThankYou For Using Hotel Reservation System!!!");
    }

    public static void main(String[] args) {
        try {
            HotelReservationSystem hotelReservationSystem = new HotelReservationSystem();
            File inputFile = new File("input.txt");
            Scanner scanner = new Scanner(inputFile);
            File outputFile = new File("output.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            PrintStream printStream = new PrintStream(fileOutputStream);
            System.setOut(printStream);

            while (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        hotelReservationSystem = new HotelReservationSystem();
                        File inputFile1 = new File("inputreserve.txt");
                        Scanner scanner1 = new Scanner(inputFile1);
                        hotelReservationSystem.start(scanner1);
                        break;
                    case 2:
                        hotelReservationSystem = new HotelReservationSystem();
                        File inputFile2 = new File("inputvewreserve.txt");
                        Scanner scanner2 = new Scanner(inputFile2);
                        hotelReservationSystem.start(scanner2);
                        break;
                    case 3:
                        hotelReservationSystem = new HotelReservationSystem();
                        File inputFile3 = new File("inputgetroom.txt");
                        Scanner scanner3 = new Scanner(inputFile3);
                        hotelReservationSystem.start(scanner3);
                        break;
                    case 4:
                        hotelReservationSystem = new HotelReservationSystem();
                        File inputFile4 = new File("inputupdate.txt");
                        Scanner scanner4 = new Scanner(inputFile4);
                        hotelReservationSystem.start(scanner4);
                        break;
                    case 5:
                        hotelReservationSystem = new HotelReservationSystem();
                        File inputFile5 = new File("inputdelete.txt");
                        Scanner scanner5 = new Scanner(inputFile5);
                        hotelReservationSystem.start(scanner5);
                        break;
                    case 6:
                        hotelReservationSystem = new HotelReservationSystem();
                        File inputFile6 = new File("inputadd.txt");
                        Scanner scanner6 = new Scanner(inputFile6);
                        hotelReservationSystem.start(scanner6);
                        break;
                    case 7:
                        hotelReservationSystem = new HotelReservationSystem();
                        File inputFile7 = new File("inputremove.txt");
                        Scanner scanner7 = new Scanner(inputFile7);
                        hotelReservationSystem.start(scanner7);
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
