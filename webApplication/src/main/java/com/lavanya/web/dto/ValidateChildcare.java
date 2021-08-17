package com.lavanya.web.dto;

public class ValidateChildcare {

    private Integer userConnectedId;
    private Integer childcareToValidateId;
    private Boolean childcareStatus;

    public ValidateChildcare() {
    }

    public Integer getUserConnectedId() {
        return userConnectedId;
    }

    public void setUserConnectedId(Integer userConnectedId) {
        this.userConnectedId = userConnectedId;
    }

    public Integer getChildcareToValidateId() {
        return childcareToValidateId;
    }

    public void setChildcareToValidateId(Integer childcareToValidateId) {
        this.childcareToValidateId = childcareToValidateId;
    }

    public Boolean getChildcareStatus() {
        return childcareStatus;
    }

    public void setChildcareStatus(Boolean childcareStatus) {
        this.childcareStatus = childcareStatus;
    }
}
