package br.com.fiap.mentorai.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecommendedCourseDto {
    private Long id;
    private String title;
    private String reason;
    private Integer workloadHours;
    private String provider;
    private List<String> skills;
}
