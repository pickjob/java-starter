module pickjob.java.starter.base {
    requires org.apache.logging.log4j;
    requires org.apache.commons.lang3;
    exports app.common;
    exports app.snowflake;
    exports app.snowflake.supplier;
    exports app.utils;
}