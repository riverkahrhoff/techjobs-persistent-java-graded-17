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
    @Size(min = 2, max = 100, message = "this field is required")
    private String location;

    @OneToMany
    @JoinColumn(name = "employer_id") //column in job table that will hold the reference to the employer
    private List<Job> jobs = new ArrayList<>();

    public Employer() {
    }

    public Employer(String location, List<Job> jobs) {
        this.location = location;
        this.jobs = jobs;
    }

    public @NotNull @Size(min = 2, max = 100) String getLocation() {
        return location;
    }

    public void setLocation(@NotNull @Size(min = 2, max = 100) String location) {
        this.location = location;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
