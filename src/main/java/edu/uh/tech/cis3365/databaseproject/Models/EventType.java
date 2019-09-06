package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Event_Type", schema = "dbo", catalog = "LadyBG")
public class EventType {
    private int eventTypeId;
    private int eventId;
    private String eventType;

    @Id
    @Column(name = "Event_Type_ID", nullable = false)
    public int getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(int eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    @Basic
    @Column(name = "Event_ID", nullable = false)
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Basic
    @Column(name = "Event_Type", nullable = false, length = 25)
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventType eventType1 = (EventType) o;
        return eventTypeId == eventType1.eventTypeId &&
                eventId == eventType1.eventId &&
                Objects.equals(eventType, eventType1.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventTypeId, eventId, eventType);
    }
}
