package framework.smpp.pdu.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import framework.smpp.pdu.Pdu;

public class BindTransceiverRespPdu extends Pdu {
    private static final Logger logger = LogManager.getLogger(BindTransceiverRespPdu.class);

    @Override
    public String toString() {
        return "BindTransceiverRespPdu{" +
                "headerPdu=" + headerPdu +
                ", bodyPdu=" + bodyPdu +
                '}';
    }
}
