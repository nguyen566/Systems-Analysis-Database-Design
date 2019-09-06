package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class PostalCode {
    private int postalCodeId;
    private String county;
    private String postalCode;

    @Id
    @Column(name = "PostalCode_ID", nullable = false)
    public int getPostalCodeId() {
        return postalCodeId;
    }

    public void setPostalCodeId(int postalCodeId) {
        this.postalCodeId = postalCodeId;
    }

    @Basic
    @Column(name = "County", nullable = false, length = 25)
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Basic
    @Column(name = "PostalCode", nullable = false, length = 25)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostalCode that = (PostalCode) o;
        return postalCodeId == that.postalCodeId &&
                Objects.equals(county, that.county) &&
                Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postalCodeId, county, postalCode);
    }
}
