package app.j4ee.validation.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class CamelCaseValidator implements ConstraintValidator<CamelCase, String> {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null != value && !"".equals(value.trim())) {
            return value.charAt(0) >= 'a' && value.charAt(0) <= 'z';
        }
        return false;
    }
}