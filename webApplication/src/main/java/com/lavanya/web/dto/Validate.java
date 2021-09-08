package com.lavanya.web.dto;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.UUID;

public class Validate {

    private UUID userToValidateId;
    private Boolean profileStatus;
    private Integer currentPage;

    public Validate() {

    }

    public Validate(UUID userToValidateId) {
        this.userToValidateId = userToValidateId;
    }

    public UUID getUserToValidateId() {
        return userToValidateId;
    }

    public void setUserToValidateId(UUID userToValidateId) {
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
