package jmx.notify;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class Hello extends NotificationBroadcasterSupport implements HelloMBean {
    private static final Logger logger = LogManager.getLogger(Hello.class);

    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        Notification n =
                new AttributeChangeNotification(this,
                        System.currentTimeMillis(),
                        System.currentTimeMillis(),
                        "name changed",
                        "name",
                        "string",
                        this.name,
                        name);
        sendNotification(n);
        this.name = name;
    }


}
