package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Vacation_Request", schema = "dbo", catalog = "ladybg")
public class VacationRequest implements Serializable {
    private int vacationId;
    private int employeeId;
    private String reason;
    private Date dateRequested;
    private Date dateReturn;

    public VacationRequest() {
    }

    public VacationRequest(int vacationId, Date dateRequested, Date dateReturn, int employeeId, String reason) {
        this.vacationId = vacationId;
        this.dateRequested = dateRequested;
        this.dateReturn = dateReturn;
        this.employeeId = employeeId;
        this.reason = reason;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id", nullable = false)
    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    @Basic
    @Column(name = "date_requested", nullable = false)
    public Date getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Date dateRequested) {
        this.dateRequested = dateRequested;
    }

    @Basic
    @Column(name = "date_return", nullable = false)
    public Date getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }

    @Basic
    @Column(name = "employee_id", nullable = false)
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Basic
    @Column(name = "reason", nullable = false, length = 25)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VacationRequest that = (VacationRequest) o;
        return vacationId == that.vacationId &&
                employeeId == that.employeeId &&
                Objects.equals(reason, that.reason) &&
                Objects.equals(dateRequested, that.dateRequested) &&
                Objects.equals(dateReturn, that.dateReturn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vacationId, employeeId, reason, dateRequested, dateReturn);
    }
}
