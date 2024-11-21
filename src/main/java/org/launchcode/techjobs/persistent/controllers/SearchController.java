package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.JobData;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.launchcode.techjobs.persistent.controllers.ListController.columnChoices;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("") //display the search form
    public String search(Model model) {
        model.addAttribute("columns", columnChoices); //passes column choices to the view, imported from ListController
        return "search";
    }


    @PostMapping("results") //process the form and display results
    public String displaySearchResults(Model model,
                                       @RequestParam String searchType, //column to search
                                       @RequestParam String searchTerm){ //term to search for
        Iterable<Job> jobs; //to hold search results
        if (searchTerm.toLowerCase().equals("all") || searchTerm.equals("")){ //if search term is all or empty, fetch all jobs
            jobs = jobRepository.findAll();
        } else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm, jobRepository.findAll()); //filter jobs by specified column and value
        }

        //pass necessary data to the view
        model.addAttribute("columns", columnChoices); //column choices to repopulate form
        model.addAttribute("title", "Jobs with " + columnChoices.get(searchType) + ": " + searchTerm); //page title
        model.addAttribute("jobs", jobs); //search results

        return "search";
    }
}
