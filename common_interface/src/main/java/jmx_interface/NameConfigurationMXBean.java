package jmx_interface;

/**
 * JMX Standard MBean Interface
 *      MBean: 管理简单类型
 *      MXBean: 管理复杂类型
 *
 * @author: pickjob@126.com
 * @date: 2024-09-11
 */
public interface NameConfigurationMXBean {

    void setName(String name);

    String getName();
}