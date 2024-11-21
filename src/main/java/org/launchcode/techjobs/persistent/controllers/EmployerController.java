package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller //manages CRUD operations for employer objects and connects the view with the data layer
@RequestMapping("employers")
public class EmployerController {

    @Autowired
    private EmployerRepository employerRepository;

    @GetMapping("/") //display a list of all employers in the database
    public String index(Model model) {
        model.addAttribute("employers", employerRepository.findAll()); //add a list of all Employer objects to the model from the repository
        return "employers/index";
    }

    @GetMapping("add") //render a form to add a new employer
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer()); //adds a new employer object to the form
        return "employers/add";
    }

    @PostMapping("add") //process form submission and add new employer objects to the database
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                    Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "employers/add";
        }

        employerRepository.save(newEmployer); //save the valid employer object
        return "redirect:";
    }

    @GetMapping("view/{employerId}") //display the contents of an individual employer object
    public String displayViewEmployer(Model model, @PathVariable int employerId) {
        Optional<Employer> optEmployer = employerRepository.findById(employerId); //look for a given employer object from the data layer

        if (optEmployer.isPresent()) { //checks if employer exists
            Employer employer = (Employer) optEmployer.get(); //retrieves the object
            model.addAttribute("employer", employer); //adds it to the model
            return "employers/view";
        } else {
            return "redirect:../";
        }

    }
}
