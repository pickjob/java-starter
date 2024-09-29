package jmx_implement;

import jmx_interface.NameConfigurationMXBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

/**
 * JMX Notification MBean Implement
 *
 * @author: pickjob@126.com
 * @date: 2024-09-11
 */
public class NotificationNameConfiguration extends NotificationBroadcasterSupport implements NameConfigurationMXBean {
    private static final Logger logger = LogManager.getLogger();

    private String name;

    @Override
    public void setName(String name) {
        logger.info("NotificationMXBean set name: {}", name);
        Notification notification =
                new AttributeChangeNotification(this,
                        System.currentTimeMillis(),
                        System.currentTimeMillis(),
                        "name changed",
                        "name",
                        "string",
                        this.name,
                        name);
        sendNotification(notification);
        this.name = name;
    }

    @Override
    public String getName() {
        logger.info("StandardMXBean get name: {}", name);
        return name;
    }
}