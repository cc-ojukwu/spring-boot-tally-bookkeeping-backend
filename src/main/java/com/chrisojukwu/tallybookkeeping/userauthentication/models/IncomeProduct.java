package com.chrisojukwu.tallybookkeeping.userauthentication.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity(name = "income_product_table")
public class IncomeProduct {

    public IncomeProduct() {
    }

    public IncomeProduct(String id, String productName, String productPrice, int productQuantity, String productTotalPrice) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productTotalPrice = productTotalPrice;
    }

    @JsonProperty("id")
    @Column(name = "id", nullable = false, unique = true)
    @Id
    @Size(max = 50)
    private String id;

    @JsonProperty("product_name")
    @Column(name = "product_name", nullable = false)
    private String productName;

    @JsonProperty("product_price")
    @Column(name = "product_price", nullable = false)
    private String productPrice;

    @JsonProperty("product_quantity")
    @Column(name = "product_quantity", nullable = false)
    private int productQuantity;

    @JsonProperty("product_total_price")
    @Column(name = "product_total_price", nullable = false)
    private String productTotalPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(String productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

}


/*

@Entity(name = "product_table")
public class Product {

    public Product() {
    }

    public Product(String id, String productName, String productPrice, int productQuantity, String productTotalPrice) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productTotalPrice = productTotalPrice;
    }

    @JsonProperty("id")
    @Column(name = "id", nullable = false, unique = true)
    @Id
    @Size(max = 50)
    private String id;

    @JsonProperty("product_name")
    @Column(name = "product_name", nullable = false)
    private String productName;

    @JsonProperty("product_price")
    @Column(name = "product_price", nullable = false)
    private String productPrice;

    @JsonProperty("product_quantity")
    @Column(name = "product_quantity", nullable = false)
    private int productQuantity;

    @JsonProperty("product_total_price")
    @Column(name = "product_total_price", nullable = false)
    private String productTotalPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(String productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

}
 */
