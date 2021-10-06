package com.lavanya.web.dto;

/**
 * Bean representing a data transfer Object UserToRegister.
 * UserToRegister has all attributes required to register a user.
 * @author lavanya
 */
public class UserToRegister {

    private String lastName;
    private String firstName;
    private String address;
    private String city;
    private String area;
    private String email;
    private String password;
    private Boolean isValidated;
    private Boolean situation;
    private String roles;

    public UserToRegister() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public Boolean getValidated() {
        return isValidated;
    }

    public void setValidated(Boolean validated) {
        isValidated = validated;
    }

    public Boolean getSituation() {
        return situation;
    }

    public void setSituation(Boolean situation) {
        this.situation = situation;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
