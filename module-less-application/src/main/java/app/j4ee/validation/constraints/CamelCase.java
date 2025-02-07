package app.j4ee.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Constraint: Bean Validation 核心注解, 注解Validation Annotation
 *      validatedBy: ConstraintValidator 实现 Bean Validation逻辑类
 *      注解Validation Annotation必须包含
 *          String message() default "{全路径类名.message}";  // 校验失败信息
 *          Class<?>[] groups() default {};
 *          Class<? extends Payload>[] payload() default {}; // 校验额外信息
 * Bean Validation 支持注解:
 *      @AssertFalse、@AssertTrue
 *      @DecimalMax(value=, inclusive=)、@DecimalMin(value=, inclusive=)
 *      @Digits(integer=, fraction=)
 *      @Email
 *      @Max(value=)、@Min(value=)
 *      @NotBlank、@NotEmpty
 *      @Negative、@NegativeOrZero、@Positive、@PositiveOrZero
 *      @Null
 *      @Future、@Past
 *      @Pattern(regex=, flags=)
 *      @Size(min=, max=)
 *
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CamelCaseValidator.class)
public @interface CamelCase {
    String message() default "{j4ee.validation.constraints.CamelCase.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}