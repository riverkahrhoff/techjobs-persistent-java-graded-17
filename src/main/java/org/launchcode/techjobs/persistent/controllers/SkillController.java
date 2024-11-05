package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("skills")
public class SkillController {

    // Autowire the SkillRepository to interact with the database
    @Autowired
    private SkillRepository skillRepository;

    // Index method to list all skills
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("skills", skillRepository.findAll()); // Get all skills from the database
        return "skills/index"; // Render the 'skills/index' template
    }

    // Method to display the form to add a new skill
    @GetMapping("add")
    public String displayAddSkillForm(Model model) {
        model.addAttribute(new Skill()); // Add an empty Skill object to the model
        return "skills/add"; // Render the 'skills/add' template
    }

    // Method to process the form and save a new skill
    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill,
                                      Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "skills/add"; // Return to the form if there are validation errors
        }

        // Save the new skill to the database
        skillRepository.save(newSkill);

        return "redirect:"; // Redirect to the index page after successful save
    }

    // Method to display a single skill's details
    @GetMapping("view/{skillId}")
    public String displayViewSkill(Model model, @PathVariable int skillId) {
        Optional<Skill> optSkill = skillRepository.findById(skillId); // Find skill by ID

        if (optSkill.isPresent()) {
            Skill skill = optSkill.get();
            model.addAttribute("skill", skill); // Add the skill object to the model
            return "skills/view"; // Render the 'skills/view' template
        } else {
            return "redirect:../"; // Redirect to the main page if the skill is not found
        }
    }
}
