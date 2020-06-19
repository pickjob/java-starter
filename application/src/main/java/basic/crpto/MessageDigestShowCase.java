package basic.crpto;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author pickjob@126.com
 * @time 2019-10-26
 */
public class MessageDigestShowCase implements IShowCase {
    private static Logger logger = LogManager.getLogger(MessageDigestShowCase.class);

    @Override
    public void saySomething() {
        logger.info("MessageDigest用于消息散列");
    }

    @Override
    public void showSomething() {
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
