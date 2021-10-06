package com.lavanya.web.dto;

/**
 * Bean representing ValidateChildcare Object.
 * ValidateChildcare has all attributes required to validate or not a childcare request.
 * @author lavanya
 */
public class ValidateChildcare {

    private Integer childcareToValidateId;
    private Boolean childcareStatus;

    public ValidateChildcare() {
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
