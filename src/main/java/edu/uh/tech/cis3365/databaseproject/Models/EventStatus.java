package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Event_Status", schema = "dbo", catalog = "LadyBG")
public class EventStatus {
    private int statusId;
    private Date eventDate;
    private int eventId;
    private String status;

    @Id
    @Column(name = "Status_ID", nullable = false)
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
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
    @Column(name = "Event_ID", nullable = false)
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Basic
    @Column(name = "Status", nullable = false, length = 25)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventStatus that = (EventStatus) o;
        return statusId == that.statusId &&
                eventId == that.eventId &&
                Objects.equals(eventDate, that.eventDate) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusId, eventDate, eventId, status);
    }
}
