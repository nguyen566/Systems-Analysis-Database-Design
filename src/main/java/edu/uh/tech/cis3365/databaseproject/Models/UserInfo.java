package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "User_Info", schema = "dbo", catalog = "LadyBG")
public class UserInfo {
    private int userId;
    private String userLogin;
    private String userPassword;
    private String userStatus;
    private int employeeId;
    private String firstName;
    private String lastName;

    public UserInfo() {
    }

    public UserInfo(int userId,String firstName, String lastName, int employeeId, String userLogin, String userStatus) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = employeeId;
        this.userLogin = userLogin;
        this.userStatus = userStatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_ID", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "User_Login", nullable = false, length = 25)
    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Basic
    @Column(name = "User_Password", nullable = false, length = 60)
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Basic
    @Column(name = "User_Status", nullable = false, length = 25)
    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    @Basic
    @Column(name = "Employee_Id", nullable = false)
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Basic
    @Column(name = "First_Name", nullable = false, length = 25)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "Last_Name", nullable = false, length = 25)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return userId == userInfo.userId &&
                employeeId == userInfo.employeeId &&
                Objects.equals(userLogin, userInfo.userLogin) &&
                Objects.equals(userPassword, userInfo.userPassword) &&
                Objects.equals(userStatus, userInfo.userStatus) &&
                Objects.equals(firstName, userInfo.firstName) &&
                Objects.equals(lastName, userInfo.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userLogin, userPassword, userStatus, employeeId, firstName, lastName);
    }
}
