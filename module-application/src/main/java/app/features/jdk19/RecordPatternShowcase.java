package app.features.jdk19;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @date 2024-09-03
 **/
public class RecordPatternShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Shape shape = new Circle(10);
        switch(shape) {
            case Circle(double radius):
                logger.info(STR."The shape is Circle with area: \{Math.PI * radius * radius}");
                break;
            case Square(double side):
                logger.info(STR."The shape is Square with area: \{side * side}");
                break;
            case Rectangle(double length, double width):
                logger.info(STR."The shape is Rectangle with area: + \{length * width}");
                break;
            default:
                logger.info("Unknown Shape");
                break;
        }

    }

    interface Shape {}
    record Circle(double radius) implements Shape { }
    record Square(double side) implements Shape { }
    record Rectangle(double length, double width) implements Shape { }
}