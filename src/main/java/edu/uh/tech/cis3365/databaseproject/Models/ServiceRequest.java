package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Service_Request", schema = "dbo", catalog = "LadyBG")
public class ServiceRequest {
    private int requestId;
    private int eventId;
    private String serviceRequested;

    @Id
    @Column(name = "Request_ID", nullable = false)
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
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
    @Column(name = "Service_Requested", nullable = true, length = 25)
    public String getServiceRequested() {
        return serviceRequested;
    }

    public void setServiceRequested(String serviceRequested) {
        this.serviceRequested = serviceRequested;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceRequest that = (ServiceRequest) o;
        return requestId == that.requestId &&
                eventId == that.eventId &&
                Objects.equals(serviceRequested, that.serviceRequested);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, eventId, serviceRequested);
    }
}
