package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Employer extends AbstractEntity {


    @NotNull
    @Size(min = 2, max = 100)
    private String location;

    public Employer() {
    }

    public Employer(String location) {
        this.location = location;
    }

    public @NotNull @Size(min = 2, max = 100) String getLocation() {
        return location;
    }

    public void setLocation(@NotNull @Size(min = 2, max = 100) String location) {
        this.location = location;
    }
}
