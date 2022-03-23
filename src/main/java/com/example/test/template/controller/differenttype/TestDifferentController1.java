package com.example.test.template.controller.differenttype;


import com.example.test.template.api.SaveService;
import com.example.test.template.api.TestService;
import com.example.test.template.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test1")
@Slf4j
public class TestDifferentController1 {

    @Autowired
    SaveService saveService;
    @Autowired
    TestService testService;

    @GetMapping("/test1")
    public String test() {
        testA();
        return "success";
    }

    /**
     * A()方法和B()方法不同类：
     * 1.方法B上面有REQUIRES_NEW的@Transactional注解
     * 1.1 A()方法异常，A()和B()均不回滚，数据库存在记录
     * 1.2 B()方法异常，A()和B()均不回滚，数据库存在记录
     */
    public void testA() {
        Person person = new Person();
        person.setAge(11);
        person.setName("11");
        saveService.save(person);
        testService.testB();
        int i = 1/0; // testA方法异常
    }
}
