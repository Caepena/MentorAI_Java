package br.com.fiap.mentorai.controller;

import br.com.fiap.mentorai.dto.RecommendationRequest;
import br.com.fiap.mentorai.service.MentorAiChatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ai")
public class AiRecommendationController {

    private final MentorAiChatService mentorAiChatService;

    public AiRecommendationController(MentorAiChatService mentorAiChatService) {
        this.mentorAiChatService = mentorAiChatService;
    }

    @GetMapping("/recommend")
    public String showForm(Model model) {
        model.addAttribute("request", new RecommendationRequest());
        return "ai/recommend";
    }

    @PostMapping("/recommend")
    public String processForm(@ModelAttribute("request") RecommendationRequest request, Model model) {

        var recs = mentorAiChatService.recommend(request.getMessage());
        request.setRecommendations(recs);

        model.addAttribute("request", request);
        return "ai/recommend";
    }

    @GetMapping
    public String redirectToRecommend() {
        return "redirect:/ai/recommend";
    }
}
