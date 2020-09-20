package com.thoughtworks.lkn.quiz.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "educations")
public class EducationDao {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long year;
    private String title;
    private String description;
    @ManyToOne
    private UserDao userDao;
}
