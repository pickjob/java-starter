package app.standard.jca;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * MD: Message-Digest
 *
 * @author pickjob@126.com
 * @date 2024-09-10
 */
public class MessageDigestShowcase {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        String orgin = "123456";
        MessageDigest sha256 = MessageDigest.getInstance("SHA-1");
        sha256.update(orgin.getBytes());
        byte[] hash = sha256.digest();
        logger.info("orgin: {} hash: {}", orgin, Base64.getEncoder().encodeToString(hash));
    }
}