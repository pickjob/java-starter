package smpp.pdu.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import smpp.pdu.Pdu;

public class DeliverSmPdu extends Pdu {
    private static Logger logger = LogManager.getLogger(DeliverSmPdu.class);

    @Override
    public String toString() {
        return "DeliverSmPdu{" +
                "headerPdu=" + headerPdu +
                ", bodyPdu=" + bodyPdu +
                '}';
    }
}
