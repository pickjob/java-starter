package showcase.smpp.pdu.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import showcase.smpp.pdu.Pdu;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
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
