package frame.validator;

import app.common.IShowCase;
import frame.validator.domain.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


public class ValidatorShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(ValidatorShowCase.class);

    @Override
    public void showSomething() {
        logger.info("展示Bean Validator示例");
        Validator validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
        Set<ConstraintViolation<Person>> errors = validator.validate(new Person("AAA", null));
        for (ConstraintViolation<Person> err : errors) {
            logger.info("Person校验失败, 路径{}, 值:{}, 信息:{}", err.getPropertyPath(), err.getInvalidValue(), err.getMessage());
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
