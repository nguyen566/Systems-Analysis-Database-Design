package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Time;
import java.util.Objects;

@Entity
public class Payroll {
    private int payrollId;
    private Time timeIn;
    private Time timeOut;
    private int timesheetId;
    private int wage;

    @Id
    @Column(name = "Payroll_ID", nullable = false)
    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    @Basic
    @Column(name = "Time_in", nullable = false)
    public Time getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Time timeIn) {
        this.timeIn = timeIn;
    }

    @Basic
    @Column(name = "Time_out", nullable = false)
    public Time getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Time timeOut) {
        this.timeOut = timeOut;
    }

    @Basic
    @Column(name = "Timesheet_ID", nullable = false)
    public int getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(int timesheetId) {
        this.timesheetId = timesheetId;
    }

    @Basic
    @Column(name = "Wage", nullable = false)
    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payroll payroll = (Payroll) o;
        return payrollId == payroll.payrollId &&
                timesheetId == payroll.timesheetId &&
                wage == payroll.wage &&
                Objects.equals(timeIn, payroll.timeIn) &&
                Objects.equals(timeOut, payroll.timeOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payrollId, timeIn, timeOut, timesheetId, wage);
    }
}
