package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Client_Type", schema = "dbo", catalog = "LadyBG")
public class ClientType {
    private int clientTypeId;
    private int clientId;
    private String firstName;
    private boolean newClient;
    private boolean returningClient;

    @Id
    @Column(name = "Client_Type_ID", nullable = false)
    public int getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(int clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    @Basic
    @Column(name = "Client_ID", nullable = false)
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "First_Name", nullable = false, length = 25)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "New_Client", nullable = false)
    public boolean isNewClient() {
        return newClient;
    }

    public void setNewClient(boolean newClient) {
        this.newClient = newClient;
    }

    @Basic
    @Column(name = "Returning_Client", nullable = false)
    public boolean isReturningClient() {
        return returningClient;
    }

    public void setReturningClient(boolean returningClient) {
        this.returningClient = returningClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientType that = (ClientType) o;
        return clientTypeId == that.clientTypeId &&
                clientId == that.clientId &&
                newClient == that.newClient &&
                returningClient == that.returningClient &&
                Objects.equals(firstName, that.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientTypeId, clientId, firstName, newClient, returningClient);
    }
}
