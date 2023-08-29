package Exam1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class RegistreringTerminal {
    private static final Scanner scanner = new Scanner(System.in);
    private static String currentStudent;

    //Koden for det første menyen man møter
    public static void mainMenu() {
        while (true) {
            System.out.println("Velkommen! Her er dine valg:");
            System.out.println("1) Logg inn som student");
            System.out.println("2) Se hele programmet");
            System.out.println("3) Avslutt");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Hva er ditt navn: ");
                String studentName = scanner.nextLine();

                if (isRegisteredStudent(studentName)) {
                    currentStudent = studentName;
                    loggedInMenu();
                } else {
                    System.out.println("Du er ikke registrert. Prøv igjen.");
                }
            } else if (choice == 2) {
                System.out.println("Hele programmet:");
                for (Event event : EventRegistering.getEvents().values()) {
                    System.out.println("Event ID: " + event.getEventId());
                    System.out.println("Event Navn: " + event.getEventName());
                    System.out.println("Event Dato: " + event.getEventDate());
                    System.out.println("Event Program: " + event.getEventProgram());
                    System.out.println("------------------------");
                }
            } else if (choice == 3) {
                System.out.println("Avslutter programmet.");
                break;
            } else {
                System.out.println("Ugyldig valg. Prøv igjen.");
            }
        }
    }
// her kommer koden til menyen man møter når man har fått logget inn
    private static void loggedInMenu() {
        while (true) {
            System.out.println("1) Registrer til eventet");
            System.out.println("2) Se alle deltakere");
            System.out.println("3) Se alle deltakere fra ditt utdanningsprogram");
            System.out.println("4) Søk etter en deltaker");
            System.out.println("5) Se hele programmet");
            System.out.println("6) Logg ut");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerParticipant();
                    break;
                case 2:
                    System.out.println("Alle deltakere:");
                    showAllParticipants();
                    break;
                case 3:
                    System.out.println("Deltakere fra ditt utdanningsprogram:");
                    showParticipantsByStudyProgram();
                    break;
                case 4:
                    searchParticipant();
                    break;
                case 5:
                    System.out.println("Hele programmet:");
                    showEventProgram();
                    break;
                case 6:
                    System.out.println("Logget ut.");
                    currentStudent = null;
                    return;
                default:
                    System.out.println("Ugyldig valg. Prøv igjen.");
            }
        }
    }

    private static boolean isRegisteredStudent(String studentName) {
        try {
            // Oppretter en forbindelse til universityDB-databasen
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/universityDB", "sensor1", "passord");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE student_name = '" + studentName + "'");

            // Sjekker om det finnes en rad i resultatsettet, dvs. om studenten er registrert
            boolean isRegistered = resultSet.next();

            resultSet.close();
            statement.close();
            connection.close();

            return isRegistered;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static void registerParticipant() {
        System.out.print("Skriv inn event ID: ");
        int eventId = scanner.nextInt();
        scanner.nextLine();

        Event event = EventRegistering.getEvent(eventId);
        if (event == null) {
            System.out.println("Eventet ble ikke funnet.");
            return;
        }

        System.out.print("Antall gjester: ");
        int guestCount = scanner.nextInt();
        scanner.nextLine();

        try {
            // Oppretter en forbindelse til eventDB-databasen
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventDB", "sensor", "passord");
            Statement statement = connection.createStatement();

            // SQL-spørring for å sette inn en ny deltaker i participants-tabellen
            String insertQuery = "INSERT INTO participants (student_name, study_program, event_id, guests) VALUES (?, ?, ?, ?)";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, currentStudent);
            preparedStatement.setString(2, "");
            preparedStatement.setInt(3, event.getEventId());
            preparedStatement.setInt(4, guestCount);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            statement.close();
            connection.close();

            System.out.println("Du er nå registrert til eventet.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showAllParticipants() {
        try {
            // Oppretter en forbindelse til eventDB-databasen
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventDB", "sensor", "passord");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM participants");

            // Viser deltakerinformasjon
            while (resultSet.next()) {
                String studentName = resultSet.getString("student_name");
                int eventId = resultSet.getInt("event_id");
                int guestCount = resultSet.getInt("guests");

                System.out.println("Student Navn: " + studentName);
                System.out.println("Event ID: " + eventId);
                System.out.println("Antall gjester: " + guestCount);
                System.out.println("------------------------");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showParticipantsByStudyProgram() {
        try {
            // Oppretter en forbindelse til universityDB-databasen
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/universityDB", "sensor1", "passord");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE student_name='" + currentStudent + "'");

            // Viser deltakere med samme studieprogram som den nåværende studenten
            while (resultSet.next()) {
                String studentName = resultSet.getString("student_name");
                System.out.println("Student Navn: " + studentName);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchParticipant() {
        System.out.print("Søk etter deltaker: ");
        String searchName = scanner.nextLine();

        try {
            // Oppretter en forbindelse til eventDB-databasen
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventDB", "sensor", "passord");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM participants WHERE student_name LIKE '%" + searchName + "%'");

            // Viser søkeresultatene
            while (resultSet.next()) {
                String studentName = resultSet.getString("student_name");
                int eventId = resultSet.getInt("event_id");
                int guestCount = resultSet.getInt("guests");

                System.out.println("Student Navn: " + studentName);
                System.out.println("Event ID: " + eventId);
                System.out.println("Antall gjester: " + guestCount);
                System.out.println("------------------------");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showEventProgram() {
        try {
            // Oppretter en forbindelse til eventDB-databasen
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventDB", "sensor", "passord");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM events");

            // Viser hele event-programmet
            while (resultSet.next()) {
                String eventProgram = resultSet.getString("event_program");
                System.out.println(eventProgram);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
