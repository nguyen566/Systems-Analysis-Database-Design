package edu.uh.tech.cis3365.databaseproject.Models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "Payment_Method", schema = "dbo", catalog = "LadyBG")
public class PaymentMethod {
    private int paymentId;
    private int clientId;
    private BigDecimal paymentAmount;
    private String paymentType;

    @Id
    @Column(name = "Payment_ID", nullable = false)
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    @Basic
    @Column(name = "Client_ID", nullable = false)
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "Payment_Amount", nullable = false, precision = 2)
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Basic
    @Column(name = "Payment_Type", nullable = false, length = 25)
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentMethod that = (PaymentMethod) o;
        return paymentId == that.paymentId &&
                clientId == that.clientId &&
                Objects.equals(paymentAmount, that.paymentAmount) &&
                Objects.equals(paymentType, that.paymentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, clientId, paymentAmount, paymentType);
    }
}
