package br.com.fiap.mentorai.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "MENTORAI_COURSE")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "WORKLOAD_HOURS")
    private Integer workloadHours;

    @Column(length = 150)
    private String provider;

    @ManyToMany
    @JoinTable(
            name = "MENTORAI_COURSE_SKILL",
            joinColumns = @JoinColumn(name = "COURSE_ID"),
            inverseJoinColumns = @JoinColumn(name = "SKILL_ID")
    )
    private List<Skill> skills;
}