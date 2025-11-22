package br.com.fiap.mentorai.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecommendationRequest {

    private String message;

    private List<RecommendedCourseDto> recommendations = new ArrayList<>();
}
