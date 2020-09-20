package com.thoughtworks.lkn.quiz.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserDao {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long age;
    private String avatar;
    private String description;
}
