package basic.crpto;

import basic.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

/**
 * @author pickjob@126.com
 * @time 2019-10-26
 */
public class HmacShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(HmacShowCase.class);

    @Override
    public void saySomething() {
        logger.info("Hmac示例");
    }

    @Override
    public void showSomething() {
        try {
            KeyGenerator hmacSHA256KeyGenerator = KeyGenerator.getInstance("HmacSHA256");
            hmacSHA256KeyGenerator.init(1024);
            SecretKey hmacSHA256SecretKey = hmacSHA256KeyGenerator.generateKey();
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(hmacSHA256SecretKey);
            byte[] result = mac.doFinal("hello world".getBytes());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
