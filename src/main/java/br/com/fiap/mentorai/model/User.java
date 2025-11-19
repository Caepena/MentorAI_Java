package br.com.fiap.mentorai.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "MENTORAI_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserSkill> skills;

    @OneToMany(mappedBy = "user")
    private List<Enrollment> enrollments;
}
