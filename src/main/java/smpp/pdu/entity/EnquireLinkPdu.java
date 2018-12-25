package smpp.pdu.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import smpp.pdu.Pdu;

public class EnquireLinkPdu extends Pdu {
    private static final Logger logger = LogManager.getLogger(EnquireLinkPdu.class);

    @Override
    public String toString() {
        return "EnquireLinkPdu{" +
                "headerPdu=" + headerPdu +
                ", bodyPdu=" + bodyPdu +
                '}';
    }
}
