package app.standard.annotation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Documented
 *     @Inherited
 *     @Target
 *         ElementType.ANNOTATION_TYPE
 *         ElementType.CONSTRUCTOR
 *         ElementType.FIELD
 *         ElementType.LOCAL_VARIABLE
 *         ElementType.METHOD
 *         ElementType.PACKAGE
 *         ElementType.PARAMETER
 *         ElementType.TYPE
 *         ElementType.TYPE_PARAMETER
 *         ElementType.TYPE_USE
 *     @Retention
 *         RetentionPolicy.CLASS
 *         RetentionPolicy.RUNTIME
 *         RetentionPolicy.SOURCE
 *     @Native
 *     @Repeatable
 *
 * @author: pickjob@126.com
 * @date: 2024-09-08
 */
public class AnnotationShowcase {
    private static final Logger logger = LogManager.getLogger();
}