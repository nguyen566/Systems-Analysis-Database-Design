package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Employee_Type", schema = "dbo", catalog = "LadyBG")
public class EmployeeType {
    private int employeeTypeId;
    private int employeeId;
    private boolean newEmp;
    private boolean returningEmp;

    @Id
    @Column(name = "Employee_Type_ID", nullable = false)
    public int getEmployeeTypeId() {
        return employeeTypeId;
    }

    public void setEmployeeTypeId(int employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }

    @Basic
    @Column(name = "Employee_ID", nullable = false)
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Basic
    @Column(name = "New_Emp", nullable = false)
    public boolean isNewEmp() {
        return newEmp;
    }

    public void setNewEmp(boolean newEmp) {
        this.newEmp = newEmp;
    }

    @Basic
    @Column(name = "Returning_Emp", nullable = false)
    public boolean isReturningEmp() {
        return returningEmp;
    }

    public void setReturningEmp(boolean returningEmp) {
        this.returningEmp = returningEmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeType that = (EmployeeType) o;
        return employeeTypeId == that.employeeTypeId &&
                employeeId == that.employeeId &&
                newEmp == that.newEmp &&
                returningEmp == that.returningEmp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeTypeId, employeeId, newEmp, returningEmp);
    }
}
