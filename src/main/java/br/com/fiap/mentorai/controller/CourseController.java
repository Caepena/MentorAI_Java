package br.com.fiap.mentorai.controller;

import br.com.fiap.mentorai.model.Course;
import br.com.fiap.mentorai.model.Skill;
import br.com.fiap.mentorai.repository.CourseRepository;
import br.com.fiap.mentorai.repository.SkillRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final SkillRepository skillRepository;

    public CourseController(CourseRepository courseRepository, SkillRepository skillRepository) {
        this.courseRepository = courseRepository;
        this.skillRepository = skillRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "courses/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("skills", skillRepository.findAll());
        return "courses/form";
    }

    @PostMapping
    public String save(
            Course course,
            @RequestParam(name = "skillIds", required = false) List<Long> skillIds
    ) {
        if (skillIds != null && !skillIds.isEmpty()) {
            List<Skill> skills = skillRepository.findAllById(skillIds);
            course.setSkills(skills);
        } else {
            course.setSkills(List.of());
        }

        courseRepository.save(course);
        return "redirect:/courses";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Course course = courseRepository.findById(id).orElseThrow();
        model.addAttribute("course", course);
        model.addAttribute("skills", skillRepository.findAll());
        return "courses/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        courseRepository.deleteById(id);
        return "redirect:/courses";
    }
}
