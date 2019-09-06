package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "Timesheet", schema = "dbo", catalog = "LadyBG")
public class Timesheet implements Serializable {
    private int timesheetId;
    private int employeeId;
    private Date workDay;
    private Time timeIn;
    private Time timeOut;

    public Timesheet() {
    }

    public Timesheet(int timesheetId, int employeeId, Date workDay, Time timeIn, Time timeOut) {
        this.timesheetId = timesheetId;
        this.employeeId = employeeId;
        this.workDay = workDay;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timesheet_id", nullable = false)
    public int getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(int timesheetId) {
        this.timesheetId = timesheetId;
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
    @Column(name = "work_day", nullable = false)
    public Date getWorkDay() {
        return workDay;
    }

    public void setWorkDay(Date workDay) {
        this.workDay = workDay;
    }

    @Basic
    @Column(name = "time_in", nullable = false)
    public Time getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Time timeIn) {
        this.timeIn = timeIn;
    }

    @Basic
    @Column(name = "time_out", nullable = true)
    public Time getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Time timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timesheet timesheet = (Timesheet) o;
        return timesheetId == timesheet.timesheetId &&
                employeeId == timesheet.employeeId &&
                Objects.equals(workDay, timesheet.workDay) &&
                Objects.equals(timeIn, timesheet.timeIn) &&
                Objects.equals(timeOut, timesheet.timeOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timesheetId, employeeId, workDay, timeIn, timeOut);
    }
}
