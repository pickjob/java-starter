package framework.smpp.pdu.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import framework.smpp.pdu.Pdu;

public class UnbindPdu extends Pdu {
    private static Logger logger = LogManager.getLogger(UnbindPdu.class);

    @Override
    public String toString() {
        return "UnbindPdu{" +
                "headerPdu=" + headerPdu +
                ", bodyPdu=" + bodyPdu +
                '}';
    }
}
