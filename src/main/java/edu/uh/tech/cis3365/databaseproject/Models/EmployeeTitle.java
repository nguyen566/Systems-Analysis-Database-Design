package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Employee_Title", schema = "dbo", catalog = "LadyBG")
public class EmployeeTitle {
    private int titleId;
    private String empTitle;
    private int employeeId;

    @Id
    @Column(name = "Title_ID", nullable = false)
    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    @Basic
    @Column(name = "Emp_Title", nullable = false, length = 25)
    public String getEmpTitle() {
        return empTitle;
    }

    public void setEmpTitle(String empTitle) {
        this.empTitle = empTitle;
    }

    @Basic
    @Column(name = "Employee_ID", nullable = false)
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeTitle that = (EmployeeTitle) o;
        return titleId == that.titleId &&
                employeeId == that.employeeId &&
                Objects.equals(empTitle, that.empTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titleId, empTitle, employeeId);
    }
}
