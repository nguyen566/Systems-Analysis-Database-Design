package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class County {
    private int countyId;
    private String countyName;

    @Id
    @Column(name = "County_ID", nullable = false)
    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    @Basic
    @Column(name = "County_Name", nullable = false, length = 25)
    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        County county = (County) o;
        return countyId == county.countyId &&
                Objects.equals(countyName, county.countyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countyId, countyName);
    }
}
