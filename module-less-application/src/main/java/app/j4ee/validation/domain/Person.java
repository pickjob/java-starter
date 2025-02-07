package app.j4ee.validation.domain;

import app.j4ee.validation.constraints.CamelCase;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class Person {

    @NotNull(message = "非空")
    @CamelCase
    private String name;

    @NotNull(message = "非空")
    private Integer age;

    public Person() {}

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}