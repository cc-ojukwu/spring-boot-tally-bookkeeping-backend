package com.chrisojukwu.tallybookkeeping.userauthentication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;


@Entity(name = "user_table")
@JsonIgnoreProperties( value = {"id", "password"}, allowSetters = true )
public class User {

    public User() {
    }

    public User(Long id, String userId, String email, String password, String businessName, String businessAddress, String businessPhone, String firstName, String lastName, String role, boolean enabled, Provider provider) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.businessPhone = businessPhone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.enabled = enabled;
        this.provider = provider;
    }

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="user_id", nullable = false, unique = true)
    @JsonProperty("user_id")
    private String userId;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(length = 100)
    @JsonProperty("business_name")
    private String businessName = "My Business Name";

    @Column(length = 200)
    @JsonProperty("business_address")
    private String businessAddress = "";

    @Column(length = 35)
    @JsonProperty("business_phone")
    private String businessPhone = "";

    @Column(name = "first_name", nullable = false, length = 30)
    @JsonProperty("first_name")
    private String firstName = "";

    @Column(name = "last_name", nullable = false, length = 30)
    @JsonProperty("last_name")
    private String lastName = "";

    private String role;
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }
}


