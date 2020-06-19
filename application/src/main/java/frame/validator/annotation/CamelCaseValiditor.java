package frame.validator.annotation;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CamelCaseValiditor implements ConstraintValidator<CamelCase, String> {
    private static final Logger logger = LogManager.getLogger(CamelCaseValiditor.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isNotBlank(value)) {
            if (value.charAt(0) >= 'a' && value.charAt(0) <= 'z') return true;
        }
        return false;
    }

}
