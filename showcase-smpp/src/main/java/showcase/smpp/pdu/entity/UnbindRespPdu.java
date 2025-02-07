package showcase.smpp.pdu.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import showcase.smpp.pdu.Pdu;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
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
