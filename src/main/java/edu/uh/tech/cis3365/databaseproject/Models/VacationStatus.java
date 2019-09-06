package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Vacation_Status", schema = "dbo", catalog = "LadyBG")
public class VacationStatus {
    private int vacationStatusId;
    private String comments;
    private String status;
    private int vacationId;

    @Id
    @Column(name = "Vacation_StatusID", nullable = false)
    public int getVacationStatusId() {
        return vacationStatusId;
    }

    public void setVacationStatusId(int vacationStatusId) {
        this.vacationStatusId = vacationStatusId;
    }

    @Basic
    @Column(name = "Comments", nullable = true, length = 25)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Basic
    @Column(name = "Status", nullable = false, length = 25)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "Vacation_ID", nullable = false)
    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VacationStatus that = (VacationStatus) o;
        return vacationStatusId == that.vacationStatusId &&
                vacationId == that.vacationId &&
                Objects.equals(comments, that.comments) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vacationStatusId, comments, status, vacationId);
    }
}
