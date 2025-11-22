package br.com.fiap.mentorai.controller;

import br.com.fiap.mentorai.model.User;
import br.com.fiap.mentorai.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        String login = Optional.ofNullable(principal.getAttribute("login"))
                .map(Object::toString)
                .orElse(null);

        String email = login != null ? login + "@github.com" : null;

        User user = null;
        if (email != null) {
            user = userRepository.findByEmail(email).orElse(null);
        }

        // esse "user" é o que seu profile.html espera
        model.addAttribute("user", user);

        // se quiser usar também os dados crus do GitHub:
        model.addAttribute("githubName", principal.getAttribute("name"));
        model.addAttribute("githubLogin", login);

        return "profile"; // vai renderizar templates/profile.html
    }
}
