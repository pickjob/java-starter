package app.standard.jca;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

/**
 * HMAC: Keyed-hash Message Authentication Code
 *
 * @author: pickjob@126.com
 * @date: 2024-09-10
 */
public class HmacShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        KeyGenerator hmacSHA256KeyGenerator = KeyGenerator.getInstance("HmacSHA256");
        hmacSHA256KeyGenerator.init(1024);
        SecretKey hmacSHA256SecretKey = hmacSHA256KeyGenerator.generateKey();
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(hmacSHA256SecretKey);
        byte[] result = mac.doFinal("hello world".getBytes());
    }
}