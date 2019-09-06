package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Employee_Training_Status", schema = "dbo", catalog = "LadyBG")
public class EmployeeTrainingStatus {
    private int trainingStatusId;
    private int employeeId;
    private int trainingId;
    private String trainingStatus;

    @Id
    @Column(name = "Training_Status_ID", nullable = false)
    public int getTrainingStatusId() {
        return trainingStatusId;
    }

    public void setTrainingStatusId(int trainingStatusId) {
        this.trainingStatusId = trainingStatusId;
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
    @Column(name = "Training_ID", nullable = false)
    public int getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }

    @Basic
    @Column(name = "Training_Status", nullable = false, length = 25)
    public String getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(String trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeTrainingStatus that = (EmployeeTrainingStatus) o;
        return trainingStatusId == that.trainingStatusId &&
                employeeId == that.employeeId &&
                trainingId == that.trainingId &&
                Objects.equals(trainingStatus, that.trainingStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingStatusId, employeeId, trainingId, trainingStatus);
    }
}
