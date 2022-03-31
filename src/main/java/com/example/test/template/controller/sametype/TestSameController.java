package com.example.test.template.controller.sametype;


import com.example.test.template.entity.Person;
import com.example.test.template.api.SaveService;
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
public class TestSameController {

    @Autowired
    SaveService saveService;

    @Lazy
    @Autowired
    TestSameController testSameController;

    /**
     * A()方法和B()方法同类：
     * 1.方法B上面有REQUIRED的@Transactional注解
     * 1.1 A()方法异常，A()和B()均不回滚，数据库存在2条记录
     * 1.2 B()方法异常，B()回滚，A()不回滚，数据库存在1条记录
     *
     * @return
     */
    @GetMapping("/test")
    public String testA() {
        Person person = new Person();
        person.setAge(11);
        person.setName("11");
        saveService.save(person);
        testSameController.testB();//
        int i = 1/0; // testA方法异常
        return "success";
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void testB() {
        Person person = new Person();
        person.setAge(22);
        person.setName("22");
        saveService.save(person);
//        int i = 1/0; // testB方法异常
    }
}
