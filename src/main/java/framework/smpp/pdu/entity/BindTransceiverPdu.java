package framework.smpp.pdu.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import framework.smpp.pdu.Pdu;

public class BindTransceiverPdu extends Pdu {
    private static final Logger logger = LogManager.getLogger(BindTransceiverPdu.class);

    @Override
    public String toString() {
        return "BindTransceiverPdu{" +
                "headerPdu=" + headerPdu +
                ", bodyPdu=" + bodyPdu +
                '}';
    }
}
