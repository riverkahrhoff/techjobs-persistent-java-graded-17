package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Employer extends AbstractEntity {


    @NotNull
    @Size(min = 2, max = 100)
    private String location;

    @OneToMany
    @JoinColumn(name = "employer_id")
    private List<Job> jobs = new ArrayList<>();

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
