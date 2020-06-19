package smpp.pdu.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import smpp.pdu.Pdu;

public class SubmitSmPdu extends Pdu {
    private static final Logger logger = LogManager.getLogger(SubmitSmPdu.class);

    @Override
    public String toString() {
        return "SubmitSmPdu{" +
                "headerPdu=" + headerPdu +
                ", bodyPdu=" + bodyPdu +
                '}';
    }
}
