package com.chrisojukwu.tallybookkeeping.userauthentication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity(name = "supplier_table")
@JsonIgnoreProperties( value = {"supplierId"})
public class Supplier {

    public Supplier() {
    }

    public Supplier(String supplierName, Long supplerId, String supplierPhone) {
        this.supplierName = supplierName;
        this.supplerId = supplerId;
        this.supplierPhone = supplierPhone;
    }

    @JsonProperty("supplier_name")
    private String supplierName;

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplerId;

    @JsonProperty("supplier_phone")
    private String supplierPhone;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public Long getSupplerId() {
        return supplerId;
    }

    public void setSupplerId(Long supplerId) {
        this.supplerId = supplerId;
    }
}
