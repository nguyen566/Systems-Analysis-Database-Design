package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "User_Login", schema = "dbo", catalog = "LadyBG")
public class UserLogin {
    private int loginId;
    private Time loginTime;
    private Time logoutTime;
    private int userId;

    @Id
    @Column(name = "Login_ID", nullable = false)
    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    @Basic
    @Column(name = "Login_Time", nullable = false)
    public Time getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Time loginTime) {
        this.loginTime = loginTime;
    }

    @Basic
    @Column(name = "Logout_Time", nullable = false)
    public Time getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Time logoutTime) {
        this.logoutTime = logoutTime;
    }

    @Basic
    @Column(name = "User_ID", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLogin userLogin = (UserLogin) o;
        return loginId == userLogin.loginId &&
                userId == userLogin.userId &&
                Objects.equals(loginTime, userLogin.loginTime) &&
                Objects.equals(logoutTime, userLogin.logoutTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginId, loginTime, logoutTime, userId);
    }
}
