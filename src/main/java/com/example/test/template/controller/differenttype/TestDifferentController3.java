package com.example.test.template.controller.differenttype;


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
@RequestMapping("test1")
@Slf4j
public class TestDifferentController3 {

    @Autowired
    SaveService saveService;

    @GetMapping("/test3")
    public String test() {
        testA();
        return "success";
    }

    /**
     * A()方法和B()方法不同类：
     * 1.方法A上面有REQUIRES_NEW的@Transactional注解
     * 1.1 A()方法异常，A()和B()均不回滚，数据库存在记录
     * 1.2 B()方法异常，A()和B()均不回滚，数据库存在记录
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void testA() {
        Person person = new Person();
        person.setAge(11);
        person.setName("11");
        saveService.save(person);
        testB();
//        int i = 1/0; // testA方法异常
    }

    public void testB() {
        Person person = new Person();
        person.setAge(22);
        person.setName("22");
        saveService.save(person);
        int i = 1/0; // testB方法异常
    }
}
