package br.com.fiap.mentorai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "MENTORAI_USER_SKILL")
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "SKILL_ID")
    private Skill skill;

}
