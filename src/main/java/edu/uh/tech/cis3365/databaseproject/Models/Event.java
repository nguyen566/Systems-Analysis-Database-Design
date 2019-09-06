package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "Event", schema = "dbo", catalog = "ladybg")
public class Event implements Serializable {
    private int eventId;
    private String serviceRequested;
    private Date eventDate;
    private int clientId;
    private Time startTime;
    private Time endTime;
    private String eventLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id")
    private Client client;

    public Event() {
    }

    public Event(int eventId, String serviceRequested, Date eventDate, int clientId, Time startTime, Time endTime, String eventLocation) {
        this.eventId = eventId;
        this.serviceRequested = serviceRequested;
        this.eventDate = eventDate;
        this.clientId = clientId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventLocation = eventLocation;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Basic
    @Column(name = "service_requested", nullable = true, length = 25)
    public String getServiceRequested() {
        return serviceRequested;
    }

    public void setServiceRequested(String serviceRequested) {
        this.serviceRequested = serviceRequested;
    }

    @Basic
    @Column(name = "event_date", nullable = false)
    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @Basic
    @Column(name = "client_id", nullable = false)
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "start_time", nullable = false)
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = false)
    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "event_location", nullable = false, length = 25)
    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventId == event.eventId &&
                clientId == event.clientId &&
                Objects.equals(serviceRequested, event.serviceRequested) &&
                Objects.equals(eventDate, event.eventDate) &&
                Objects.equals(startTime, event.startTime) &&
                Objects.equals(endTime, event.endTime) &&
                Objects.equals(eventLocation, event.eventLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, serviceRequested, eventDate, clientId, startTime, endTime, eventLocation);
    }
}
