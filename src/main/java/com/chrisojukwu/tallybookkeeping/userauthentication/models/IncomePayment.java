package com.chrisojukwu.tallybookkeeping.userauthentication.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity(name = "income_payment_table")
public class IncomePayment {

    public IncomePayment() {
    }

    public IncomePayment(String id, String paymentAmount, String paymentDate, PaymentMode paymentMode) {
        this.id = id;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
        this.paymentMode = paymentMode;
    }

    @JsonProperty("id")
    @Column(name = "id", nullable = false)
    @Id
    private String id;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "record_id", nullable = false)
//    private NetworkIncome networkIncome;

    @JsonProperty("payment_amount")
    @Column(name = "payment_amount", nullable = false)
    private String paymentAmount;

    @JsonProperty("payment_date")
    @Column(name = "payment_date", nullable = false)
    private String paymentDate;

    @JsonProperty("payment_mode")
    @Column(name = "payment_mode", nullable = false)
    private PaymentMode paymentMode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

}

/*

@Entity(name = "payment_table")
public class Payment {

    public Payment() {
    }

    public Payment(String id, String paymentAmount, String paymentDate, PaymentMode paymentMode) {
        this.id = id;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
        this.paymentMode = paymentMode;
    }

    @JsonProperty("id")
    @Column(name = "id", nullable = false)
    @Id
    private String id;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "record_id", nullable = false)
//    private NetworkIncome networkIncome;

    @JsonProperty("payment_amount")
    @Column(name = "payment_amount", nullable = false)
    private String paymentAmount;

    @JsonProperty("payment_date")
    @Column(name = "payment_date", nullable = false)
    private String paymentDate;

    @JsonProperty("payment_mode")
    @Column(name = "payment_mode", nullable = false)
    private PaymentMode paymentMode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

}
 */
