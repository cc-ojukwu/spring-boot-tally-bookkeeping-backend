package com.chrisojukwu.tallybookkeeping.userauthentication.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.minidev.json.annotate.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity(name = "income_table")
@JsonIgnoreProperties( value = {"user"})
public class NetworkIncome {

    public NetworkIncome() {
    }

    public NetworkIncome(User user, String recordId, String date, String totalAmount, String amountReceived, String discount, String subTotal, String balanceDue, String description, Set<IncomeProduct> productList, Customer customer, Set<IncomePayment> paymentList) {
        this.user = user;
        this.recordId = recordId;
        this.date = date;
        this.totalAmount = totalAmount;
        this.amountReceived = amountReceived;
        this.discount = discount;
        this.subTotal = subTotal;
        this.balanceDue = balanceDue;
        this.description = description;
        this.productList = productList;
        this.customer = customer;
        this.paymentList = paymentList;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false) //lazy fetch will return income without the user associated with it
    @JsonIgnore //this removes <User> as part of the json response
    @JoinColumn(name = "user_id", nullable = false) //a foreign-key column is created in the income class (with user_id as column name) and link to the primary key of the User class
    private User user;

    @JsonProperty("record_id")
    @Id
    @Column(name = "record_id", nullable = false, unique = true)
    @Size(max = 50)
    private String recordId;

    @JsonProperty("date")
    @Column(name = "date", nullable = false)
    private String date;

    @JsonProperty("total_amount")
    @Column(name = "total_amount", nullable = false)
    private String totalAmount;

    @JsonProperty("amount_received")
    @Column(name = "amount_received", nullable = false)
    private String amountReceived;

    @JsonProperty("discount")
    @Column(name = "discount", nullable = false)
    private String discount;

    @JsonProperty("subtotal")
    @Column(name = "subtotal", nullable = false)
    private String subTotal;

    @JsonProperty("balance_due")
    @Column(name = "balance_due", nullable = false)
    private String balanceDue;

    @JsonProperty("description")
    @Size(max = 5000)
    @Column(name = "description", nullable = false)
    private String description;

    @JsonProperty("product_list")
    @Column(name = "product_list")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IncomeProduct> productList;

    @JsonProperty("customer")
    @JoinColumn(name = "customer")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Customer customer;

    @JsonProperty("payment_list")
    @Column(name = "payment", nullable = false)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IncomePayment> paymentList;


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

    public String getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(String amountReceived) {
        this.amountReceived = amountReceived;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
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

    public Set<IncomeProduct> getProductList() {
        return productList;
    }

    public void setProductList(Set<IncomeProduct> productList) {
        this.productList = productList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<IncomePayment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(Set<IncomePayment> paymentList) {
        this.paymentList = paymentList;
    }
}
