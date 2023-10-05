package com.vivekanandpv.springbootapijpafileiovalidation.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String identityProofPath;
    private String addressProofPath;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentityProofPath() {
        return identityProofPath;
    }

    public void setIdentityProofPath(String identityProofPath) {
        this.identityProofPath = identityProofPath;
    }

    public String getAddressProofPath() {
        return addressProofPath;
    }

    public void setAddressProofPath(String addressProofPath) {
        this.addressProofPath = addressProofPath;
    }
}
