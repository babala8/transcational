package com.example.test.template.controller.test;


import com.example.test.template.api.SaveService;
import com.example.test.template.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Slf4j
public class TestAOPController2 {

    @Autowired
    SaveService saveService;
    /**
     * 调用本类方法导致传播行为失效
     * 同一个 Service 的两个方法之间调用，就会出现这个问题，
     * 原因还是在代理对象这里，我们期待的调用是一个代理类的调用，
     * 但是我们若是直接在方法中内部调用，不好意思，被调用的方法的事务失效，
     * 没有被 AOP 增强,
     * 解决方法：
     * 1.改进方案就是自己调用自己，自己注入自己，使用注解如下：
     * @Lazy
     * @Autowired
     * TestSameController2 testSameController2;
     *
     * 2.通过 AopContext 拿到代理对象，然后再调用。这里要注意，使用这种方式需要开启暴露代理
     *
     * @EnableAspectJAutoProxy(exposeProxy = true)
     * 	public class Application {}
     *
     *  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
     * 	public void a (){
     *   	 	 ((TestSameController2)AopContext.currentProxy()).b();
     *    }
     */

//    @EnableAspectJAutoProxy(exposeProxy = true)
//    public class Application {
//
//    }

    /**
     * A()方法和B()方法同类：
     * 1.方法A上面有REQUIRED的@Transactional注解
     * 1.1 A()方法异常，A()和B()均回滚，数据库不存在记录
     * 1.2 B()方法异常，A()和B()均不回滚，数据库存在记录
     */
    @GetMapping("/testSelf")
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public String testA() {
        Person person = new Person();
        person.setAge(11);
        person.setName("11");
        saveService.save(person);
        ((TestAOPController2) AopContext.currentProxy()).testB();// todo 通过代理对象调用
        int i = 1/0; // testA方法异常
        return "success";
    }

    public void testB() {
        Person person = new Person();
        person.setAge(22);
        person.setName("22");
        saveService.save(person);
//        int i = 1/0; // testB方法异常
    }
}
