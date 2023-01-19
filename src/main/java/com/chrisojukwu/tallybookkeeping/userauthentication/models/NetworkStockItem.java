package com.chrisojukwu.tallybookkeeping.userauthentication.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity(name = "inventory_table")
@JsonIgnoreProperties( value = {"user"})
public class NetworkStockItem {

    public NetworkStockItem() {
    }

    public NetworkStockItem(User user, String sku, String stockName, String costPrice, String sellingPrice, String quantity, String imageUrl) {
        this.user = user;
        this.sku = sku;
        this.stockName = stockName;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }


    @ManyToOne(fetch = FetchType.LAZY) //lazy fetch will return income without the user associated with it
    @JsonIgnore //this removes <User> as part of the json response
    @JoinColumn(name = "user_id", nullable = false) //a foreign-key column is created in the income class (with user_id as column name) and link to the primary key of the User class
    private User user;

    @JsonProperty("sku")
    @Id
    @Column(name = "sku", nullable = false, unique = true)
    @Size(max = 50)
    private String sku;

    @JsonProperty("stock_name")
    @Column(name = "stock_name", nullable = false)
    private String stockName;

    @JsonProperty("cost_price")
    @Column(name = "cost_price", nullable = false)
    private String costPrice;

    @JsonProperty("selling_price")
    @Column(name = "selling_price", nullable = false)
    private String sellingPrice;

    @JsonProperty("quantity")
    @Column(name = "quantity", nullable = false)
    private String quantity;

    @JsonProperty("image_url")
    @Column(name = "image_url")
    private String imageUrl;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
