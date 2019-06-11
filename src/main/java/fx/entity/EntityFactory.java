package fx.entity;

import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pickjob@126.com
 * @time 2019-06-04
 **/
public class EntityFactory {

    public static List<Person> generatePersons() {
        List<Person> result = new ArrayList<>();
        result.add(new Person("Helen", 14));
        result.add(new Person("Jack", 21));
        result.add(new Person("Bob", 32));
        return result;
    }

    public static TreeItem<Person> generateTreePerson() {
        TreeItem<Person> grandFather = new TreeItem<>(new Person("GrandFather", 51));
        TreeItem<Person> father = new TreeItem<>(new Person("Father", 41));
        TreeItem<Person> brother = new TreeItem<>(new Person("Brother", 17));
        TreeItem<Person> me = new TreeItem<>(new Person("Me", 14));
        father.getChildren().addAll(brother, me);
        grandFather.getChildren().add(father);
        grandFather.setExpanded(true);
        return grandFather;
    }
}
