package smpp.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

/**
 * @author pickjob@126.com
 * @time 2019-01-03
 */
public class DataHolder {
    private static final Logger logger = LogManager.getLogger(DataHolder.class);
    private static HashMap<String, String> seq2Content = new HashMap<>();
    private static HashMap<String, String> messageId2seq = new HashMap<>();

    public static void putSeqToContent(String seq, String content) {
        seq2Content.put(seq, content);
    }

    public static void putMessageIdToSeq(String MessageId, String seq) {
        messageId2seq.put(MessageId, seq);
    }

    public static String getContent(String messageId) {
        String seq = messageId2seq.get(messageId);
        if (StringUtils.isNotBlank(seq)) return seq2Content.get(seq);
        return null;
    }

}
