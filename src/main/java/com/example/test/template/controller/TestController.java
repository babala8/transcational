package com.example.test.template.controller;


import com.example.test.template.entity.Person;
import com.example.test.template.api.SaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    @GetMapping("/test")
    public String test() {
        testA();
        return "success";
    }

    @Autowired
    SaveService saveService;

    public void testA() {
        Person person = new Person();
        person.setAge(11);
        person.setName("11");
        saveService.save(person);
        testB();
    }
    @Transactional(rollbackFor = Exception.class)
    public void testB() {
        Person person = new Person();
        person.setAge(22);
        person.setName("22");
        saveService.save(person);
        int i = 1/0; // testB方法异常
    }
}
