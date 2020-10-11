package basic.jmx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class NotificationHello extends NotificationBroadcasterSupport implements NotificationHelloMBean {
    private static final Logger logger = LogManager.getLogger(NotificationHello.class);

    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
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


}
