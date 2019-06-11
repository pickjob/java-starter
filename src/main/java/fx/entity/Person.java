package fx.entity;

import javafx.beans.property.*;

/**
 * @author pickjob@126.com
 * @time 2019-06-04
 **/
public class Person {
    private StringProperty name;
    private IntegerProperty age;
    private BooleanProperty audit;

    public Person(String name, Integer age) {
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.audit = new SimpleBooleanProperty(age >= 18);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public boolean isAudit() {
        return audit.get();
    }

    public BooleanProperty auditProperty() {
        return audit;
    }

    public void setAudit(boolean audit) {
        this.audit.set(audit);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", audit=" + audit +
                '}';
    }
}
