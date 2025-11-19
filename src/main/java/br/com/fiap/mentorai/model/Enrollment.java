package br.com.fiap.mentorai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "MENTORAI_ENROLLMENT")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    @Column(length = 50)
    private String status;

}
