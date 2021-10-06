package com.lavanya.web.dto;

import java.util.UUID;

/**
 * Bean representing Validate Object.
 * Validate has all attributes required to validate or not a user profile.
 * @author lavanya
 */
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
