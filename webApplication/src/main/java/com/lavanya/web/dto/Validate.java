package com.lavanya.web.dto;

import javax.persistence.criteria.CriteriaBuilder;

public class Validate {

    private Integer userConnectedId;
    private Integer userToValidateId;
    private Boolean profileStatus;

    public Validate() {

    }

    public Validate(Integer userConnectedId, Integer userToValidateId) {
        this.userConnectedId = userConnectedId;
        this.userToValidateId = userToValidateId;
    }

    public Integer getUserConnectedId() {
        return userConnectedId;
    }

    public void setUserConnectedId(Integer userConnectedId) {
        this.userConnectedId = userConnectedId;
    }

    public Integer getUserToValidateId() {
        return userToValidateId;
    }

    public void setUserToValidateId(Integer userToValidateId) {
        this.userToValidateId = userToValidateId;
    }

    public Boolean getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(Boolean profileStatus) {
        this.profileStatus = profileStatus;
    }
}
