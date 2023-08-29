package Exam1;

class Event {
    private int eventId;
    private String eventName;
    private String eventDate;
    private String eventProgram;

    public Event(int eventId, String eventName, String eventDate, String eventProgram) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventProgram = eventProgram;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventProgram() {
        return eventProgram;
    }
}