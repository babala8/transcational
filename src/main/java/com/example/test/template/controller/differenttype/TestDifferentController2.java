package com.example.test.template.controller.differenttype;


import com.example.test.template.api.SaveService;
import com.example.test.template.api.TestService;
import com.example.test.template.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("test1")
@Slf4j
public class TestDifferentController2 {

    @Autowired
    SaveService saveService;
    @Resource(name = "testService1111")
    TestService testService;


    /**
     * A()方法和B()方法不同类：
     * 1.方法A上面有REQUIRED的@Transactional注解
     * 1.1 A()方法异常，A()和B()均回滚，数据库不存在记录
     * 1.2 B()方法异常，A()和B()均回滚，数据库不存在记录
     */
    @GetMapping("/test2")
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public String testA() {
        Person person = new Person();
        person.setAge(11);
        person.setName("11");
        saveService.save(person);
        testService.testB();
        int i = 1/0; // testA方法异常
        return "success";
    }

}
