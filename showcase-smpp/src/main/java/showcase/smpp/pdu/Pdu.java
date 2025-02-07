package showcase.smpp.pdu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class Pdu {
    private static Logger logger = LogManager.getLogger(Pdu.class);
    protected HeaderPdu headerPdu;
    protected BodyPdu bodyPdu;

    public HeaderPdu getHeaderPdu() {
        return headerPdu;
    }

    public void setHeaderPdu(HeaderPdu headerPdu) {
        this.headerPdu = headerPdu;
    }

    public BodyPdu getBodyPdu() {
        return bodyPdu;
    }

    public void setBodyPdu(BodyPdu bodyPdu) {
        this.bodyPdu = bodyPdu;
    }
}
