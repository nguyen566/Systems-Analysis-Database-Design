package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Locations {
    private int locationId;
    private String streetAddress;
    private String city;
    private String state;
    private String postal;
    private String country;

    @Id
    @Column(name = "Location_ID", nullable = false)
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Basic
    @Column(name = "Street_Address", nullable = false, length = 25)
    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
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
    @Column(name = "State", nullable = false, length = 2)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
    @Column(name = "Country", nullable = false, length = 25)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Locations locations = (Locations) o;
        return locationId == locations.locationId &&
                Objects.equals(streetAddress, locations.streetAddress) &&
                Objects.equals(city, locations.city) &&
                Objects.equals(state, locations.state) &&
                Objects.equals(postal, locations.postal) &&
                Objects.equals(country, locations.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, streetAddress, city, state, postal, country);
    }
}
