package showcase.smpp.util;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public interface CommandId {
    final static int GENERIC_NACK = 0x80000000;

    final static int BIND_RECEIVER = 0x00000001;
    final static int BIND_RECEIVER_RESP = 0x80000001;

    final static int BIND_TRANSMITTER = 0x00000002;
    final static int BIND_TRANSMITTER_RESP = 0x80000002;

    final static int QUERY_SM = 0x00000003;
    final static int QUERY_SM_RESP = 0x80000003;

    final static int SUBMIT_SM = 0x00000004;
    final static int SUBMIT_SM_RESP = 0x80000004;

    final static int DELIVER_SM = 0x00000005;
    final static int DELIVER_SM_RESP = 0x80000005;

    final static int UNBIND = 0x00000006;
    final static int UNBIND_RESP = 0x80000006;

    final static int REPLACE_SM = 0x00000007;
    final static int REPLACE_SM_RESP = 0x80000007;

    final static int CANCEL_SM = 0x00000008;
    final static int CANCEL_SM_RESP = 0x80000008;

    final static int BIND_TRANSCEIVER = 0x00000009;
    final static int BIND_TRANSCEIVER_RESP = 0x80000009;

    final static int OUTBIND = 0x0000000B;

    final static int ENQUIRE_LINK = 0x00000015;
    final static int ENQUIRE_LINK_RESP = 0x80000015;

    final static int SUBMIT_MULTI = 0x00000021;
    final static int SUBMIT_MULIT = 0x80000021;

    final static int ALERT_NOTIFICATION = 0x00000102;

    final static int DATA_SM = 0x00000103;
    final static int DATA_SM_RESP = 0x80000103;

}
