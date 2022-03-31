package com.example.test.template.impl;

import com.example.test.template.api.SaveService;
import com.example.test.template.api.TestService;
import com.example.test.template.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("testService1111")
public class TestService1Impl implements TestService {

    @Autowired
    SaveService saveService;

    @Override
    public void testB() {
        Person person = new Person();
        person.setAge(22);
        person.setName("22");
        saveService.save(person);
        int i = 1/0; // testB方法异常
    }
}
