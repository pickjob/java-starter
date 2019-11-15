package smpp.pdu.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import smpp.pdu.Pdu;

public class DeliverSmRespPdu extends Pdu {
    private static Logger logger = LogManager.getLogger(DeliverSmRespPdu.class);

    @Override
    public String toString() {
        return "DeliverSmRespPdu{" +
                "headerPdu=" + headerPdu +
                ", bodyPdu=" + bodyPdu +
                '}';
    }
}
