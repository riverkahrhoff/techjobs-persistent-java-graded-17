package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.launchcode.techjobs.persistent.models.JobData;

import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "list")
public class ListController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private SkillRepository skillRepository;


    static HashMap<String, String> columnChoices = new HashMap<>(); //store filter categories and their display names

    public ListController () { //initialize the columnChoices map

        columnChoices.put("all", "All");
        columnChoices.put("employer", "Employer");
        columnChoices.put("skill", "Skill");

    }

    @RequestMapping("") //display the list overview page
    public String list(Model model) {

        //pass all employers and skills to the view
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());

        return "list";
    }

    @RequestMapping(value = "jobs") //display jobs filtered by column and value
    public String listJobsByColumnAndValue(Model model,
                                           @RequestParam String column, //column name to filter by (employer, skill)
                                           @RequestParam String value) { //value to match the specified column (employer: launchcode, skill: java)
        Iterable<Job> jobs;
        if (column.toLowerCase().equals("all")){ //if user selected 'all jobs', fetch all jobs
            jobs = jobRepository.findAll();
            model.addAttribute("title", "All Jobs");
        } else {
            jobs = JobData.findByColumnAndValue(column, value, jobRepository.findAll()); //fetch jobs matching the specified column and value
            model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);
        }
        model.addAttribute("jobs", jobs); //add the resulting objects to the model to pass to the view

        return "list-jobs";
    }
}
