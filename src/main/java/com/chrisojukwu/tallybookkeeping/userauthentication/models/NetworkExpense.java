package com.chrisojukwu.tallybookkeeping.userauthentication.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "expense_table")
@JsonIgnoreProperties( value = {"user"})
public class NetworkExpense {

    public NetworkExpense() {
    }

    public NetworkExpense(User user, String recordId, String date, String totalAmount, String amountPaid, String balanceDue, String description, String category, Set<ExpenseProduct> productList, Supplier supplier, Set<ExpensePayment> paymentList) {
        this.user = user;
        this.recordId = recordId;
        this.date = date;
        this.totalAmount = totalAmount;
        this.amountPaid = amountPaid;
        this.balanceDue = balanceDue;
        this.description = description;
        this.category = category;
        this.productList = productList;
        this.supplier = supplier;
        this.paymentList = paymentList;
    }

    @ManyToOne(fetch = FetchType.LAZY) //lazy fetch will return the post without the user details associated with it
    @JsonIgnore //this removes <User> as part of the post json response
    @JoinColumn(name = "user_id", nullable = false)
    private User user; //a user_id column is created in the post class to identify the owner of the post


    @JsonProperty("record_id")
    @Column(name="record_id", nullable = false, unique = true, updatable = false)
    @Id
    private String recordId;

    @JsonProperty("date")
    @Column(name="date", nullable = false)
    private String date;

    @JsonProperty("total_amount")
    @Column(name="total_amount", nullable = false)
    private String totalAmount;

    @JsonProperty("amount_paid")
    @Column(name="amount_paid", nullable = false)
    private String amountPaid;

    @JsonProperty("balance_due")
    @Column(name="balance_due", nullable = false)
    private String balanceDue;

    @JsonProperty("description")
    @Column(name="description", nullable = false)
    private String description;

    @JsonProperty("category")
    @Column(name="category")
    private String category;

    @JsonProperty("product_list")
    @Column(name="product_list")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExpenseProduct> productList;

    @JsonProperty("supplier")
    @JoinColumn(name="supplier")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Supplier supplier;

    @JsonProperty("payment_list")
    @Column(name="payment_list", nullable = false)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExpensePayment> paymentList;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getBalanceDue() {
        return balanceDue;
    }

    public void setBalanceDue(String balanceDue) {
        this.balanceDue = balanceDue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<ExpenseProduct> getProductList() {
        return productList;
    }

    public void setProductList(Set<ExpenseProduct> productList) {
        this.productList = productList;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Set<ExpensePayment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(Set<ExpensePayment> paymentList) {
        this.paymentList = paymentList;
    }


}
