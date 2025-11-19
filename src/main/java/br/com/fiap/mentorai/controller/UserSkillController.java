package br.com.fiap.mentorai.controller;

import br.com.fiap.mentorai.model.UserSkill;
import br.com.fiap.mentorai.repository.UserRepository;
import br.com.fiap.mentorai.repository.SkillRepository;
import br.com.fiap.mentorai.repository.UserSkillRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user-skills")
public class UserSkillController {

    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public UserSkillController(UserSkillRepository userSkillRepository,
                               UserRepository userRepository,
                               SkillRepository skillRepository) {
        this.userSkillRepository = userSkillRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("userSkills", userSkillRepository.findAll());
        return "user-skills/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("userSkill", new UserSkill());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        return "user-skills/form";
    }

    @PostMapping
    public String save(UserSkill userSkill) {
        userSkillRepository.save(userSkill);
        return "redirect:/user-skills";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        userSkillRepository.deleteById(id);
        return "redirect:/user-skills";
    }
}
