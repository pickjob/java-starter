package framework.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import framework.validator.domain.Person;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


public class ValidatorShowCase {
    private static final Logger logger = LogManager.getLogger(ValidatorShowCase.class);

    public static void main(String[] args) {
        Validator validator = Validation.buildDefaultValidatorFactory()
                                        .getValidator();
        Set<ConstraintViolation<Person>> errors = validator.validate(new Person("AAA", null));
        for (ConstraintViolation<Person> err : errors) {
            logger.info("Person校验失败, 路径{}, 值:{}, 信息:{}", err.getPropertyPath(), err.getInvalidValue(), err.getMessage());
        }
    }
}
