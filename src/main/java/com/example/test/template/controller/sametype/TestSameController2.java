package com.example.test.template.controller.sametype;


import com.example.test.template.api.SaveService;
import com.example.test.template.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Slf4j
public class TestSameController2 {

    @Autowired
    SaveService saveService;

    @Lazy
    @Autowired
    TestSameController2 testSameController2;

    /**
     * A()方法和B()方法同类：
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
        testSameController2.testB();
//        int i = 1/0; // testA方法异常
        return "success";
    }

    public void testB() {
        Person person = new Person();
        person.setAge(22);
        person.setName("22");
        saveService.save(person);
        int i = 1/0; // testB方法异常
    }
}
