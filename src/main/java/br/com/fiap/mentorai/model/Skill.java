package br.com.fiap.mentorai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "MENTORAI_SKILL")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(length = 500)
    private String description;
}
