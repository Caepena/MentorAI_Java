package br.com.fiap.mentorai.controller;

import br.com.fiap.mentorai.model.Enrollment;
import br.com.fiap.mentorai.repository.EnrollmentRepository;
import br.com.fiap.mentorai.repository.UserRepository;
import br.com.fiap.mentorai.repository.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentController(EnrollmentRepository enrollmentRepository,
                                UserRepository userRepository,
                                CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("enrollments", enrollmentRepository.findAll());
        return "enrollments/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("enrollment", new Enrollment());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("courses", courseRepository.findAll());
        return "enrollments/form";
    }

    @PostMapping
    public String save(Enrollment enrollment) {
        enrollmentRepository.save(enrollment);
        return "redirect:/enrollments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        enrollmentRepository.deleteById(id);
        return "redirect:/enrollments";
    }
}
