package app.framework.mybatis.domain;

import org.apache.ibatis.type.Alias;

/**
 * @author pickjob@126.com
 * @date 2024-09-14
 **/
@Alias("persion")
public class Person {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return STR."Person{id=\{id}, name='\{name}\{'\''}\{'}'}";
    }
}
