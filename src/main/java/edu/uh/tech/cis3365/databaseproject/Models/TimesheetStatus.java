package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Timesheet_Status", schema = "dbo", catalog = "LadyBG")
public class TimesheetStatus {
    private int timeStatusId;
    private String comments;
    private String status;
    private int timesheetId;

    @Id
    @Column(name = "TimeStatus_ID", nullable = false)
    public int getTimeStatusId() {
        return timeStatusId;
    }

    public void setTimeStatusId(int timeStatusId) {
        this.timeStatusId = timeStatusId;
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
    @Column(name = "Timesheet_ID", nullable = false)
    public int getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(int timesheetId) {
        this.timesheetId = timesheetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimesheetStatus that = (TimesheetStatus) o;
        return timeStatusId == that.timeStatusId &&
                timesheetId == that.timesheetId &&
                Objects.equals(comments, that.comments) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeStatusId, comments, status, timesheetId);
    }
}
