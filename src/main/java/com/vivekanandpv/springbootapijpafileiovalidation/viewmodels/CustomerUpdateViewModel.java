package com.vivekanandpv.springbootapijpafileiovalidation.viewmodels;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerUpdateViewModel {
    @NotBlank(message = "Cannot accept null, empty, or whitespace for first name")
    @Size(min = 3, max = 200)
    private String firstName;

    @Size(max = 200)
    private String lastName;

    @Email
    @Size(max = 200)
    private String email;

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
}
