package app.framework.mybatis.mapper;

import app.framework.mybatis.domain.Person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

/**
 * @author pickjob@126.com
 * @date 2024-09-14
 **/
public interface PersonMapper {
    @Select("SELECT * FROM person")
    List<Person> selectAll();

    @SelectKey(statement = {"SELECT last_insert_rowid() AS id FROM person"}, keyProperty = "id", before = false, resultType = Integer.class)
    @Insert("INSERT INTO PERSON(name) VALUES (#{name})")
    int insertOne(Person person);
}