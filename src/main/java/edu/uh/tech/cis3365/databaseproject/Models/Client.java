package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Client", schema = "dbo", catalog = "LadyBG")
public class Client implements Serializable {
    private int clientId;
    private String clientFirst;
    private String clientLast;
    private String clientEmail;
    private String clientPhone;
    private String clientStreet;
    private String clientCity;
    private String clientState;
    private String clientComments;
    private int locationId;

    public Client() {
    }

    public Client(int clientId, String clientFirst, String clientLast, String clientEmail, String clientPhone, String clientStreet, String clientCity, String clientState, String clientComments, int locationId) {
        this.clientId = clientId;
        this.clientFirst = clientFirst;
        this.clientLast = clientLast;
        this.clientEmail = clientEmail;
        this.clientPhone = clientPhone;
        this.clientStreet = clientStreet;
        this.clientCity = clientCity;
        this.clientState = clientState;
        this.clientComments = clientComments;
        this.locationId = locationId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "client_first", nullable = false, length = 25)
    public String getClientFirst() {
        return clientFirst;
    }

    public void setClientFirst(String clientFirst) {
        this.clientFirst = clientFirst;
    }

    @Basic
    @Column(name = "client_last", nullable = false, length = 25)
    public String getClientLast() {
        return clientLast;
    }

    public void setClientLast(String clientLast) {
        this.clientLast = clientLast;
    }

    @Basic
    @Column(name = "client_email", nullable = false, length = 50)
    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    @Basic
    @Column(name = "client_phone", nullable = false, length = 25)
    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    @Basic
    @Column(name = "client_street", nullable = false, length = 25)
    public String getClientStreet() {
        return clientStreet;
    }

    public void setClientStreet(String clientStreet) {
        this.clientStreet = clientStreet;
    }

    @Basic
    @Column(name = "client_city", nullable = false, length = 25)
    public String getClientCity() {
        return clientCity;
    }

    public void setClientCity(String clientCity) {
        this.clientCity = clientCity;
    }

    @Basic
    @Column(name = "client_state", nullable = false, length = 2)
    public String getClientState() {
        return clientState;
    }

    public void setClientState(String clientState) {
        this.clientState = clientState;
    }

    @Basic
    @Column(name = "client_comments", nullable = true, length = 100)
    public String getClientComments() {
        return clientComments;
    }

    public void setClientComments(String clientComments) {
        this.clientComments = clientComments;
    }

    @Basic
    @Column(name = "location_id", nullable = false)
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientId == client.clientId &&
                Objects.equals(clientFirst, client.clientFirst) &&
                Objects.equals(clientLast, client.clientLast) &&
                Objects.equals(clientEmail, client.clientEmail) &&
                Objects.equals(clientPhone, client.clientPhone) &&
                Objects.equals(clientStreet, client.clientStreet) &&
                Objects.equals(clientCity, client.clientCity) &&
                Objects.equals(clientState, client.clientState) &&
                Objects.equals(clientComments, client.clientComments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, clientFirst, clientLast, clientEmail, clientPhone, clientStreet, clientCity, clientState, clientComments);
    }
}
