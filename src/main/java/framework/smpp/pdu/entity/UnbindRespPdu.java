package framework.smpp.pdu.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import framework.smpp.pdu.Pdu;

public class UnbindRespPdu extends Pdu {
    private static Logger logger = LogManager.getLogger(UnbindRespPdu.class);

    @Override
    public String toString() {
        return "UnbindRespPdu{" +
                "headerPdu=" + headerPdu +
                ", bodyPdu=" + bodyPdu +
                '}';
    }
}
