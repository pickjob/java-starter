package app.j4ee.validation;

import app.j4ee.validation.domain.Person;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.util.Set;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class BeanValidationShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        try (ValidatorFactory factory = Validation.byDefaultProvider().configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Person>> errors = validator.validate(new Person("AAA", null));
            for (ConstraintViolation<Person> err : errors) {
                logger.info("Person校验失败, 路径: {}, 值: {}, 信息: {}", err.getPropertyPath(), err.getInvalidValue(), err.getMessage());
            }
        }
    }
}