package validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validator.annotation.CamelCase;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CamelCaseValiditor implements ConstraintValidator<CamelCase, String> {
    private static final Logger logger = LogManager.getLogger(CamelCaseValiditor.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.info("value:{}", value);
        return false;
    }

}
