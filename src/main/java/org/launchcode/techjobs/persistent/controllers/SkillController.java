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

    @Autowired
    private SkillRepository skillRepository;


    @GetMapping("/") //display list of all skills in the database
    public String index(Model model) {
        model.addAttribute("skills", skillRepository.findAll());
        return "skills/index";
    }


    @GetMapping("add") //display the form to add a new skill
    public String displayAddSkillForm(Model model) {
        model.addAttribute(new Skill()); // Add an empty Skill object to the model
        return "skills/add"; // Render the 'skills/add' template
    }


    @PostMapping("add") //process the form and save a new skill
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill,
                                      Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "skills/add"; // Return to the form if there are validation errors
        }

        // Save the new skill to the database
        skillRepository.save(newSkill);

        return "redirect:"; // Redirect to the index page after successful save
    }


    @GetMapping("view/{skillId}") //display the details of an individual skill object
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
