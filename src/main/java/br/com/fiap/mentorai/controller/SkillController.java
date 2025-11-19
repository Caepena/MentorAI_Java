package br.com.fiap.mentorai.controller;

import br.com.fiap.mentorai.model.Skill;
import br.com.fiap.mentorai.repository.SkillRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/skills")
public class SkillController {

    private final SkillRepository repository;

    public SkillController(SkillRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("skills", repository.findAll());
        return "skills/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("skill", new Skill());
        return "skills/form";
    }

    @PostMapping
    public String save(Skill skill) {
        repository.save(skill);
        return "redirect:/skills";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("skill", repository.findById(id).orElseThrow());
        return "skills/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/skills";
    }
}
