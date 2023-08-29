package Exam1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


public class EventRegistering {
    // Oppretter et HashMap for å lagre Event-objekter med eventId som nøkkel
    private static final HashMap<Integer, Event> events = new HashMap<>();

    public static void main(String[] args) {
        // Laster inn events fra databasen
        loadEventsFromDatabase();
        // Kaller på mainMenu-metoden i RegistreringTerminal-klassen
        RegistreringTerminal.mainMenu();
    }

    public static Event getEvent(int eventId) {
        // Henter et Event-objekt fra events HashMap basert på eventId
        return events.get(eventId);
    }

    public static HashMap<Integer, Event> getEvents() {
        // Returnerer events HashMap
        return events;
    }

    private static void loadEventsFromDatabase() {
        try {
            // Oppretter forbindelse til eventDB-databasen
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventDB", "sensor", "passord");
            // Oppretter et Statement-objekt for å sende SQL-spørringer til databasen
            Statement statement = connection.createStatement();
            // Utfører en SQL-spørring for å hente alle rader fra events-tabellen
            ResultSet resultSet = statement.executeQuery("SELECT * FROM events");

            // oppretter Event-objekter basert på dataene
            while (resultSet.next()) {
                int eventId = resultSet.getInt("event_id");
                String eventName = resultSet.getString("event_name");
                String eventDate = resultSet.getString("event_date");
                String eventProgram = resultSet.getString("event_program");

                // Legger til Event-objektet i event-HashMap med eventId som nøkkel
                events.put(eventId, new Event(eventId, eventName, eventDate, eventProgram));
            }

            // Lukker resultSet, statement og connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}