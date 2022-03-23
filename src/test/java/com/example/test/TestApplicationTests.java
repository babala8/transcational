package com.example.test;

import com.example.test.template.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
class TestApplicationTests {

    @Test
    void contextLoads() {
        StringBuilder sb = new StringBuilder();
        Person person = new Person();
        sb.append(1);
        sb.append("+");
        if(Objects.nonNull(person.getAge())){
            sb.append(person.getAge());
        }
        sb.append("+");
        if(person.getName() != null){
            sb.append(person.getName());
        }
        System.out.println(sb.toString());
    }

}
