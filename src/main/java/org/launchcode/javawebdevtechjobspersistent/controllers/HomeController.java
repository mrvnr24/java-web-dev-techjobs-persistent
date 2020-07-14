package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.launchcode.javawebdevtechjobspersistent.models.Job;
import org.launchcode.javawebdevtechjobspersistent.models.Skill;
import org.launchcode.javawebdevtechjobspersistent.models.data.EmployerRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.JobRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.SkillRepository;
import org.launchcode.javawebdevtechjobspersistent.models.dto.JobSkillDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

  @Autowired
  JobRepository jobRepository;

  @Autowired
  EmployerRepository employerRepository;

  @Autowired
  SkillRepository skillRepository;

    @RequestMapping("")
    public String index(@RequestParam(required = false) Integer employerId, Model model) {

      if (employerId == null) {
        model.addAttribute("title", "My Jobs");
        model.addAttribute("jobs", jobRepository.findAll());
      } else {
        Optional<Employer> optEmployer = employerRepository.findById(employerId);
        if (optEmployer.isEmpty()) {
          model.addAttribute("title", "Invalid Employer ID: " + employerId);
        } else {
          Employer employer = optEmployer.get();
          model.addAttribute("title", employer.getName());
          model.addAttribute("jobs", employer.getJobs());
        }
      }


        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            model.addAttribute("job", newJob); //?
            model.addAttribute(new Job());
            return "add";
        }

        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillObjs);
        jobRepository.save(newJob);
        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

      Optional optJob = jobRepository.findById(jobId);

      if (optJob.isPresent()) {
        Job job = (Job) optJob.get();
        model.addAttribute("job", job);
        return "view";
      } else {
        return "redirect:../";
      }

    }
//
//    public String displayAddSkillForm(@RequestParam Integer jobId, Model model) {
//      Optional<Job> optionalJob = jobRepository.findById(jobId);
//      Job job = optionalJob.get();
//      model.addAttribute("title", "Add Skill to: " + job.getName());
//      model.addAttribute("skills", skillRepository.findAll());
//      model.addAttribute("job", job);
//      model.addAttribute(new JobSkillDTO());
//      return "add-skill.html";
//    }


}
