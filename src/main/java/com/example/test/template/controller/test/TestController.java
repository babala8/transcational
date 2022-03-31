package com.example.test.template.controller.test;


import com.example.test.template.api.SaveService;
import com.example.test.template.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    @Autowired
    SaveService saveService;

    /**
     * 一个方法中，一个事务异常回滚
     */
    @GetMapping("/")
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public String test() {
        Person person = new Person();
        person.setAge(11);
        person.setName("11");
        saveService.save(person);
        Person person1 = new Person();
        person1.setAge(22);
        person1.setName("22");
        saveService.save(person1);
        int i = 1/0; // testB方法异常
        return "success";
    }

}
