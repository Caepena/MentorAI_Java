package br.com.fiap.mentorai.service;

import br.com.fiap.mentorai.dto.RecommendedCourseDto;
import br.com.fiap.mentorai.model.Course;
import br.com.fiap.mentorai.repository.CourseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MentorAiChatService {

    private final ChatClient chatClient;
    private final CourseRepository courseRepository;

    private final String systemMessage = """
            Você é o assistente MentorAI, especializado em requalificação profissional.
            Sua função é recomendar cursos da plataforma MentorAI com base nas necessidades do usuário.
            
            REGRAS:
            - Responda SEMPRE em português.
            - Seja claro, objetivo e didático.
            - Considere apenas os cursos que eu fornecer no contexto.
            - Quando recomendar, explique em poucas frases por que o curso faz sentido para o usuário.
            - Se nenhum curso for adequado, explique isso de forma gentil e sugira caminhos gerais.
            """;

    private final ChatMemory chatMemory =
            MessageWindowChatMemory.builder()
                    .maxMessages(20)
                    .build();

    private final ChatOptions chatOptions = ChatOptions.builder()
            .model("gpt-4o-mini")
            .temperature(0.5)
            .presencePenalty(0.8)
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MentorAiChatService(ChatClient.Builder builder,
                               CourseRepository courseRepository) {

        this.courseRepository = courseRepository;

        this.chatClient = builder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor()
                )
                .defaultOptions(chatOptions)
                .defaultSystem(systemMessage)
                .build();
    }

    // ✅ Agora retorna List<RecommendedCourseDto>, compatível com o seu controller
    public List<RecommendedCourseDto> recommend(String userMessage) {
        String coursesContext = buildCoursesContext();

        String prompt = """
                Esses são os cursos disponíveis na plataforma MentorAI:

                %s

                Usuário descreveu o que busca com a seguinte mensagem:
                "%s"

                Com base APENAS na lista de cursos acima, gere de 1 a 3 recomendações
                no seguinte formato JSON EXATO (sem texto fora do JSON):

                [
                  { "id": 1, "title": "Nome do curso", "reason": "Por que esse curso é adequado" }
                ]

                Regras:
                - Use SOMENTE IDs que existam na lista de cursos.
                - "reason" deve ser um texto curto em português explicando a escolha.
                - NÃO escreva nada fora do JSON.
                """.formatted(coursesContext, userMessage);

        String content = chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();

        try {
            // tenta converter a resposta em uma lista de RecommendedCourseDto
            return objectMapper.readValue(content, new TypeReference<List<RecommendedCourseDto>>() {});
        } catch (Exception e) {
            // fallback: IA não obedeceu o formato JSON, então devolvemos a resposta crua
            List<RecommendedCourseDto> fallback = new ArrayList<>();
            RecommendedCourseDto dto = new RecommendedCourseDto();
            dto.setId(null);
            dto.setTitle("Recomendação gerada pela IA");
            dto.setReason(content);
            fallback.add(dto);
            return fallback;
        }
    }

    private String buildCoursesContext() {
        List<Course> courses = courseRepository.findAll();

        if (courses.isEmpty()) {
            return "Nenhum curso cadastrado ainda.";
        }

        StringBuilder sb = new StringBuilder();
        for (Course c : courses) {
            sb.append("- [ID: ").append(c.getId()).append("]\n");
            sb.append("  Título: ").append(c.getTitle()).append("\n");
            sb.append("  Descrição: ")
                    .append(c.getDescription() != null ? c.getDescription() : "Sem descrição")
                    .append("\n");
            sb.append("  Carga horária: ")
                    .append(c.getWorkloadHours() != null ? c.getWorkloadHours() + "h" : "N/A")
                    .append("\n");
            sb.append("  Fornecedor: ")
                    .append(c.getProvider() != null ? c.getProvider() : "N/A")
                    .append("\n\n");
        }
        return sb.toString();
    }
}
