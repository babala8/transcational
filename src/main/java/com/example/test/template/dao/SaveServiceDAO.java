package com.example.test.template.dao;

import com.example.test.template.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SaveServiceDAO {

    public static final String INSERT_PERSON="insert into t_test_person(age, name) VALUES (?,?)";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void save(Person person) {
        jdbcTemplate.update(INSERT_PERSON,person.getAge(),person.getName());
    }
}
