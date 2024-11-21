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
        model.addAttribute("skills", skillRepository.findAll()); //add all skills to the model
        return "skills/index";
    }


    @GetMapping("add") //display the form to add a new skill
    public String displayAddSkillForm(Model model) {
        model.addAttribute(new Skill()); // Add an empty Skill object to the model to bind form inputs
        return "skills/add";
    }


    @PostMapping("add") //process the form and save a new skill
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill,
                                      Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "skills/add";
        }

        // Save the new skill to the database
        skillRepository.save(newSkill);

        return "redirect:";
    }


    @GetMapping("view/{skillId}") //display the details of an individual skill object
    public String displayViewSkill(Model model, @PathVariable int skillId) {
        Optional<Skill> optSkill = skillRepository.findById(skillId); // Find skill by ID

        if (optSkill.isPresent()) { // Check if a skill with the given ID exists
            Skill skill = optSkill.get(); // Retrieve the skill object
            model.addAttribute("skill", skill); // Add the skill object to the model
            return "skills/view";
        } else {
            return "redirect:../";
        }
    }
}
