package framework.smpp.pdu.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import framework.smpp.pdu.Pdu;

public class GenericNackPdu extends Pdu {
    private static final Logger logger = LogManager.getLogger(GenericNackPdu.class);

    @Override
    public String toString() {
        return "GenericNackPdu{" +
                "headerPdu=" + headerPdu +
                ", bodyPdu=" + bodyPdu +
                '}';
    }
}
