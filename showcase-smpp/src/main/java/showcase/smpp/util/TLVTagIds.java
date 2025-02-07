package showcase.smpp.util;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public interface TLVTagIds {
    // 有些状态报告MESSAGE_ID放在这
    static final short RECEIPTED_MESSAGE_ID = 0x001E;

    static final short DEST_ADDR_SUBUNIT = 0x0005;
    static final short DEST_NETWORK_TYPE = 0x0006;
    static final short DEST_BEARER_TYPE = 0x0007;
    static final short DEST_TELEMATICS_ID = 0x0008;
    static final short SOURCE_ADDR_SUBUNIT = 0x000D;
    static final short SOURCE_NETWORK_TYPE = 0x000E;
    static final short SOURCE_BEARER_TYPE = 0x000F;
    static final short SOURCE_TELEMATICS_ID = 0x0010;
    static final short QOS_TIME_TO_LIVE = 0x0017;
    static final short PAYLOAD_TYPE = 0x0019;
    static final short ADDITIONAL_STATUS_INFO_TEXT = 0x001D;
    static final short MS_MSG_WAIT_FACILITIES = 0x0030;
    static final short PRIVACY_INDICATOR = 0x0201;
    static final short SOURCE_SUBADDRESS = 0x0202;
    static final short DEST_SUBADDRESS = 0x0203;
    static final short USER_MESSAGE_REFERENCE = 0x0204;
    static final short USER_RESPONSE_CODE = 0x0205;
    static final short SOURCE_PORT = 0x020A;
    static final short DESTINATION_PORT = 0x020B;
    static final short SAR_MSG_REF_NUM = 0x020C;
    static final short LANGUAGE_INICATOR = 0x020D;
    static final short SAR_TOTAL_SEGMENT = 0x020E;
    static final short SAR_SEGMENT_SEQNUM = 0x020F;
    static final short SC_INTERFACE_VERSION = 0x0210;
    static final short CALLBACK_NUM_PRES_IND = 0x0302;
    static final short CALLBACK_NUM_ATAG = 0x0303;
    static final short NUMBER_OF_MESSAGES = 0x0304;
    static final short CALLBACK_NUM = 0x0381;
    static final short DPF_RESULT = 0x0420;
    static final short SET_DPF = 0x0421;
    static final short MS_AVAILABILITY = 0x0422;
    static final short NETWORK_ERROR_CODE = 0x0423;
    static final short MESSAGE_PAYLOAD = 0x0424;
    static final short DELIVERY_FAILURE_RESON = 0x0425;
    static final short MORE_MESSAGES_TO_SEND = 0x0426;
    static final short MESSAGE_STATE = 0x0427;
    static final short USSD_SERVICE_OP = 0x0501;
    static final short DISPLAY_TIME = 0x1201;
    static final short SMS_SIGNAL = 0x1203;
    static final short MS_VALIDITY = 0x1204;
    static final short ALTERT_ON_MESSAGE_DELIVERY = 0x130C;
    static final short ITS_REPLY_TYPE = 0x1380;
    static final short ITS_SESSION_INFO = 0x1383;

}
