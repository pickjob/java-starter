package smpp.pdu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
