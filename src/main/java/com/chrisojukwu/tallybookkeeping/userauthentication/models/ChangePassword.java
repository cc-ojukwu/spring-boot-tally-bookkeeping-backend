package com.chrisojukwu.tallybookkeeping.userauthentication.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePassword {

    public ChangePassword() {
    }

    public ChangePassword(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    @JsonProperty("old_password")
    String oldPassword;

    @JsonProperty("new_password")
    String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
