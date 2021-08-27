package com.lavanya.web.dto;

import javax.persistence.criteria.CriteriaBuilder;

public class Validate {

    private Integer userToValidateId;
    private Boolean profileStatus;
    private Integer currentPage;

    public Validate() {

    }

    public Validate(Integer userToValidateId) {
        this.userToValidateId = userToValidateId;
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

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
