package smpp.pdu.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import smpp.pdu.Pdu;

public class SubmitSmRespPdu extends Pdu {
    private static final Logger logger = LogManager.getLogger(SubmitSmRespPdu.class);

    @Override
    public String toString() {
        return "SubmitSmRespPdu{" +
                "headerPdu=" + headerPdu +
                ", bodyPdu=" + bodyPdu +
                '}';
    }
}
