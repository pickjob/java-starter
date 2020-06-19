package basic.crpto;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * @author pickjob@126.com
 * @time 2019-10-26
 */
public class CipherShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(CipherShowCase.class);

    @Override
    public void saySomething() {
        logger.info("Cipher加密");
    }

    @Override
    public void showSomething() {
        try {
            KeyGenerator aesKeyGenerator = KeyGenerator.getInstance("AES");
            aesKeyGenerator.init(128);
            SecretKey aesSecretKey = aesKeyGenerator.generateKey();
            Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            aesCipher.init(Cipher.ENCRYPT_MODE, aesSecretKey);
            byte[] aesClipherText = aesCipher.doFinal("hello".getBytes());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
