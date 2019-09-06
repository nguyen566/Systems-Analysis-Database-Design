package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "Event_Date", schema = "dbo", catalog = "LadyBG")
public class EventDate {
    private int eventDateId;
    private Date eventDate;
    private Time eventEnd;
    private int eventId;
    private Time eventStart;

    @Id
    @Column(name = "Event_Date_ID", nullable = false)
    public int getEventDateId() {
        return eventDateId;
    }

    public void setEventDateId(int eventDateId) {
        this.eventDateId = eventDateId;
    }

    @Basic
    @Column(name = "Event_Date", nullable = false)
    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @Basic
    @Column(name = "Event_End", nullable = false)
    public Time getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(Time eventEnd) {
        this.eventEnd = eventEnd;
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
    @Column(name = "Event_Start", nullable = false)
    public Time getEventStart() {
        return eventStart;
    }

    public void setEventStart(Time eventStart) {
        this.eventStart = eventStart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDate eventDate1 = (EventDate) o;
        return eventDateId == eventDate1.eventDateId &&
                eventId == eventDate1.eventId &&
                Objects.equals(eventDate, eventDate1.eventDate) &&
                Objects.equals(eventEnd, eventDate1.eventEnd) &&
                Objects.equals(eventStart, eventDate1.eventStart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventDateId, eventDate, eventEnd, eventId, eventStart);
    }
}
