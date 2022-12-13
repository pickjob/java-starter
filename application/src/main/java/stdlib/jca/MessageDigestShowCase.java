package stdlib.jca;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * MD: Message-Digest
 *
 * @author pickjob@126.com
 * @time 2022-12-13
 */
public class MessageDigestShowCase {
    private static Logger logger = LogManager.getLogger(MessageDigestShowCase.class);

    public static void main(String[] args) {
        try {
            String orgin = "123456";
            MessageDigest sha256 = MessageDigest.getInstance("SHA-1");
            sha256.update(orgin.getBytes());
            byte[] hash = sha256.digest();
            logger.info("orgin: {} hash: {}", orgin, Base64.getEncoder().encodeToString(hash));
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
