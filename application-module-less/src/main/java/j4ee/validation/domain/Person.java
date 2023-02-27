package j4ee.validation.domain;

import j4ee.validation.constraints.CamelCase;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author: pickjob@126.com
 * @date: 2022-12-08
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
