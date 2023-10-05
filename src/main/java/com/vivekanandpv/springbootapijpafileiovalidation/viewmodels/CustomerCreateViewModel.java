package com.vivekanandpv.springbootapijpafileiovalidation.viewmodels;

import org.springframework.web.multipart.MultipartFile;

public class CustomerCreateViewModel {
    private String firstName;
    private String lastName;
    private String email;
    private MultipartFile identityProof;
    private MultipartFile addressProof;

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

    public MultipartFile getIdentityProof() {
        return identityProof;
    }

    public void setIdentityProof(MultipartFile identityProof) {
        this.identityProof = identityProof;
    }

    public MultipartFile getAddressProof() {
        return addressProof;
    }

    public void setAddressProof(MultipartFile addressProof) {
        this.addressProof = addressProof;
    }
}
