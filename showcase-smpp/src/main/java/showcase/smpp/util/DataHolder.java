package showcase.smpp.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class DataHolder {
    private static final Logger logger = LogManager.getLogger();
    private static Map<String, String> seq2Content = new ConcurrentHashMap<String, String>();
    private static Map<String, String> messageId2seq = new ConcurrentHashMap<>();

    public static void putSeqToContent(String seq, String content) {
        seq2Content.put(seq, content);
    }

    public static void putMessageIdToSeq(String messageId, String seq) {
        messageId2seq.put(messageId, seq);
    }

    public static String getContent(String messageId) {
        String seq = messageId2seq.get(messageId);
        if (StringUtils.isNotBlank(seq)) return seq2Content.get(seq);
        return null;
    }

    public static void remove(String messageId) {
        messageId2seq.remove(messageId);
    }

    public static int getSize() {
        return messageId2seq.keySet().size();
    }

}
