package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Booking_Status", schema = "dbo", catalog = "LadyBG")
public class BookingStatus {
    private int bookingId;
    private Date bookingDate;
    private String bookingStatus;
    private int clientId;

    @Id
    @Column(name = "Booking_ID", nullable = false)
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    @Basic
    @Column(name = "Booking_Date", nullable = false)
    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    @Basic
    @Column(name = "Booking_Status", nullable = false, length = 25)
    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Basic
    @Column(name = "Client_ID", nullable = false)
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingStatus that = (BookingStatus) o;
        return bookingId == that.bookingId &&
                clientId == that.clientId &&
                Objects.equals(bookingDate, that.bookingDate) &&
                Objects.equals(bookingStatus, that.bookingStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, bookingDate, bookingStatus, clientId);
    }
}
