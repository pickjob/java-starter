package showcase.smpp.pdu;

import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import showcase.smpp.pdu.body.*;
import showcase.smpp.pdu.entity.*;
import showcase.smpp.util.CommandId;
import showcase.smpp.util.SequenceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class PduFactory {
    private static final Logger logger = LogManager.getLogger(PduFactory.class);
    private static final Map<Integer, Class<? extends BodyPdu>> registedBodyClass = new HashMap<>();
    private static final Map<Integer, Class<? extends Pdu>> registedPduClass = new HashMap<>();

    public static Pdu decode(ByteBuf buf) {
        HeaderPdu headerPdu = new HeaderPdu();
        headerPdu.decoding(buf);
        Pdu pdu = newPduInstance(headerPdu.getCommandId());
        pdu.setHeaderPdu(headerPdu);
        BodyPdu bodyPdu = newBodyInstance(headerPdu.getCommandId());
        bodyPdu.decoding(buf);
        pdu.setBodyPdu(bodyPdu);
        return pdu;
    }

    public static void encode(ByteBuf buf, Pdu pdu) {
        HeaderPdu headerPdu = pdu.getHeaderPdu();
        BodyPdu bodyPdu = pdu.getBodyPdu();
        headerPdu.setCommandLength(bodyPdu.getSize() + 16);
        headerPdu.encoding(buf);
        bodyPdu.encoding(buf);
    }

    public static Pdu newPduInstance(Integer commandId) {
        Pdu pdu = null;
        Class<? extends Pdu> pduCls = registedPduClass.get(commandId);
        try {
            pdu = pduCls.newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return pdu;
    }

    public static HeaderPdu newHeaderInstance(Integer commandId) {
        HeaderPdu headerPdu = new HeaderPdu();
        headerPdu.setCommandId(commandId);
        headerPdu.setCommandStatus(0);
        headerPdu.setSequenceNumber(SequenceUtil.getNextSquence());
        return headerPdu;
    }

    public static BodyPdu newBodyInstance(Integer commandId) {
        BodyPdu bodyPdu = null;
        Class<? extends BodyPdu> bodyCls = registedBodyClass.get(commandId);
        try {
            bodyPdu = bodyCls.newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return bodyPdu;
    }

    static {
        registClass(CommandId.BIND_TRANSCEIVER, BindTransceiverPdu.class, BindTransceiverBody.class);
        registClass(CommandId.BIND_TRANSCEIVER_RESP, BindTransceiverRespPdu.class, BindTransceiverRespBody.class);
        registClass(CommandId.ENQUIRE_LINK, EnquireLinkPdu.class, NOOPBody.class);
        registClass(CommandId.ENQUIRE_LINK_RESP, EnquireLinkRespPdu.class, NOOPBody.class);
        registClass(CommandId.GENERIC_NACK, GenericNackPdu.class, NOOPBody.class);
        registClass(CommandId.UNBIND, UnbindPdu.class, NOOPBody.class);
        registClass(CommandId.UNBIND_RESP, UnbindRespPdu.class, NOOPBody.class);
        registClass(CommandId.SUBMIT_SM, SubmitSmPdu.class, SubmitAndDeliverBody.class);
        registClass(CommandId.SUBMIT_SM_RESP, SubmitSmRespPdu.class, SubmitAndDeliverRespBody.class);
        registClass(CommandId.DELIVER_SM, DeliverSmPdu.class, SubmitAndDeliverBody.class);
        registClass(CommandId.DELIVER_SM_RESP, DeliverSmRespPdu.class, SubmitAndDeliverRespBody.class);
    }

    private static void registClass(Integer commandId, Class<? extends Pdu> pduClass, Class<? extends BodyPdu> bodyClass) {
        registedBodyClass.put(commandId, bodyClass);
        registedPduClass.put(commandId, pduClass);
    }
}