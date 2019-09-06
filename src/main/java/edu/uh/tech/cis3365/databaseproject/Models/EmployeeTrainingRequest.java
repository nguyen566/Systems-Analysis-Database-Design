package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Employee_Training_Request", schema = "dbo", catalog = "LadyBG")
public class EmployeeTrainingRequest implements Serializable {
    private int trainingRequestid;
    private int employeeId;
    private Date trainingDate;
    private String trainingDescription;
    private String trainingType;

    public EmployeeTrainingRequest() {
    }

    public EmployeeTrainingRequest(int trainingRequestid, int employeeId, Date trainingDate, String trainingDescription, String trainingType) {
        this.trainingRequestid = trainingRequestid;
        this.employeeId = employeeId;
        this.trainingDate = trainingDate;
        this.trainingDescription = trainingDescription;
        this.trainingType = trainingType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Training_RequestId", nullable = false)
    public int getTrainingRequestid() {
        return trainingRequestid;
    }

    public void setTrainingRequestid(int trainingRequestid) {
        this.trainingRequestid = trainingRequestid;
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
    @Column(name = "Training_Date", nullable = false)
    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    @Basic
    @Column(name = "Training_Description", nullable = false, length = 100)
    public String getTrainingDescription() {
        return trainingDescription;
    }

    public void setTrainingDescription(String trainingDescription) {
        this.trainingDescription = trainingDescription;
    }

    @Basic
    @Column(name = "Training_Type", nullable = false, length = 25)
    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeTrainingRequest that = (EmployeeTrainingRequest) o;
        return trainingRequestid == that.trainingRequestid &&
                employeeId == that.employeeId &&
                Objects.equals(trainingType, that.trainingType) &&
                Objects.equals(trainingDate, that.trainingDate) &&
                Objects.equals(trainingDescription, that.trainingDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingRequestid, trainingType, trainingDate, trainingDescription, employeeId);
    }
}
