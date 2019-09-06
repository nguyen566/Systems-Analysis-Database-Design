package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Event_Expenses", schema = "dbo", catalog = "LadyBG")
public class EventExpenses {
    private int expenseId;
    private int eventId;
    private Integer otherCost;
    private String otherExpenses;
    private String service;
    private int serviceCost;

    @Id
    @Column(name = "Expense_ID", nullable = false)
    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
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
    @Column(name = "Other_Cost", nullable = true)
    public Integer getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(Integer otherCost) {
        this.otherCost = otherCost;
    }

    @Basic
    @Column(name = "Other_Expenses", nullable = true, length = 25)
    public String getOtherExpenses() {
        return otherExpenses;
    }

    public void setOtherExpenses(String otherExpenses) {
        this.otherExpenses = otherExpenses;
    }

    @Basic
    @Column(name = "Service", nullable = false, length = 25)
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Basic
    @Column(name = "Service_Cost", nullable = false)
    public int getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(int serviceCost) {
        this.serviceCost = serviceCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventExpenses that = (EventExpenses) o;
        return expenseId == that.expenseId &&
                eventId == that.eventId &&
                serviceCost == that.serviceCost &&
                Objects.equals(otherCost, that.otherCost) &&
                Objects.equals(otherExpenses, that.otherExpenses) &&
                Objects.equals(service, that.service);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, eventId, otherCost, otherExpenses, service, serviceCost);
    }
}
