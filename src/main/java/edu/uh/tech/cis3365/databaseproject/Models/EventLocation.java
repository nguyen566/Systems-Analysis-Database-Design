package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Event_Location", schema = "dbo", catalog = "LadyBG")
public class EventLocation {
    private int locationId;
    private String city;
    private int eventId;
    private String postal;
    private String state;
    private String streetAddress;

    @Id
    @Column(name = "Location_ID", nullable = false)
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Basic
    @Column(name = "City", nullable = false, length = 25)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
    @Column(name = "Postal", nullable = false, length = 10)
    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    @Basic
    @Column(name = "State", nullable = false, length = 2)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "Street_Address", nullable = false, length = 25)
    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventLocation that = (EventLocation) o;
        return locationId == that.locationId &&
                eventId == that.eventId &&
                Objects.equals(city, that.city) &&
                Objects.equals(postal, that.postal) &&
                Objects.equals(state, that.state) &&
                Objects.equals(streetAddress, that.streetAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, city, eventId, postal, state, streetAddress);
    }
}
