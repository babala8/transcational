package com.example.test.template.impl;

import com.example.test.template.entity.Person;
import com.example.test.template.api.SaveService;
import com.example.test.template.dao.SaveServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveServiceImpl implements SaveService {

    @Autowired
    SaveServiceDAO saveServiceDAO;

    @Override
    public void save(Person person) {
        saveServiceDAO.save(person);
    }
}
