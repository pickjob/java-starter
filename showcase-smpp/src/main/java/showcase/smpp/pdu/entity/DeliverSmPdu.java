package showcase.smpp.pdu.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import showcase.smpp.pdu.Pdu;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class DeliverSmPdu extends Pdu {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String toString() {
        return "DeliverSmPdu{" +
                "headerPdu=" + headerPdu +
                ", bodyPdu=" + bodyPdu +
                '}';
    }
}
