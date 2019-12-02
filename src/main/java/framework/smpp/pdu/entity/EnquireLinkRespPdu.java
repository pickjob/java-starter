package framework.smpp.pdu.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import framework.smpp.pdu.Pdu;

public class EnquireLinkRespPdu extends Pdu {
    private static final Logger logger = LogManager.getLogger(EnquireLinkRespPdu.class);

    @Override
    public String toString() {
        return "EnquireLinkRespPdu{" +
                "headerPdu=" + headerPdu +
                ", bodyPdu=" + bodyPdu +
                '}';
    }
}
