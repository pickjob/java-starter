package fx.entity;

import fx.entity.Person;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

/**
 * @author pickjob@126.com
 * @time 2019-06-05
 **/
public class MyTextFieldListCell extends TextFieldListCell<Person> {

    public MyTextFieldListCell() {
        super(new StringConverter<Person>() {

            @Override
            public String toString(Person object) {
                return object.getName() + "-" + object.getAge();
            }

            @Override
            public Person fromString(String string) {
                return new Person(string, 11);
            }
        });
    }

    @Override
    public void updateItem(Person item, boolean empty) {
        super.updateItem(item, empty);
        setAlignment(Pos.CENTER);
        if (item != null ) setTooltip(new Tooltip(item.toString()));
    }
}
