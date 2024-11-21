package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("/") //display a list of all jobs
    public String index(Model model) {
        List<Job> jobs = (List<Job>) jobRepository.findAll(); //get all jobs from the database
        model.addAttribute("title", "MyJobs"); //add a title for the page
        model.addAttribute("jobs", jobs); //pass the list of jobs to the view

        return "index";
    }

    @GetMapping("add") //display a form to add jobs to the database
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");

        // Fetch and add all employers and skills to the model for selection in the form
        List<Employer> employers = (List<Employer>) employerRepository.findAll();
        model.addAttribute("employers", employers);

        List<Skill> skills = (List<Skill>) skillRepository.findAll();
        model.addAttribute("skills", skills);

        model.addAttribute(new Job()); //add a new job object, allowing the form to bind input fields to the properties of the object
        return "add";
    }

    @PostMapping("add") //process the submission of the form
    public String processAddJobForm(@ModelAttribute @Valid Job newJob, //bind form data to a job object and validate it
                                    Errors errors,
                                    Model model,
                                    @RequestParam int employerId,
                                    @RequestParam List<Integer> skills) {


        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");

            return "add";

        }
        Optional<Employer> employer = employerRepository.findById(employerId); // Fetch the selected employer by ID
        if (employer.isPresent()) {
            newJob.setEmployer(employer.get()); // Associate the employer with the job
        }

        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);  // Fetch the selected skills by their IDs
        newJob.setSkills(skillObjs); // Associate the skills with the job


        jobRepository.save(newJob); // Save the new Job to the database

        return "redirect:";
    }

    @GetMapping("view/{jobId}") //display details of a specific job
    public String displayViewJob(Model model, @PathVariable int jobId) {

        Optional<Job> job = jobRepository.findById(jobId); // Find the job by ID

        if (job.isPresent()) {
            model.addAttribute("job", job.get()); // Add the job object to the model
            return "view";
        } else {
            return "redirect:/";
        }


    }

}
