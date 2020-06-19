package basic.crpto;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author pickjob@126.com
 * @time 2019-10-26
 */
public class AESShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(AESShowCase.class);

    @Override
    public void saySomething() {
        logger.info("AES加解密示例");
    }

    @Override
    public void showSomething() {
        try {
            String plainText = "今天天气很好阿";
            KeyGenerator desKeyGenerator = KeyGenerator.getInstance("AES");
            byte[] keyBytes = desKeyGenerator.generateKey().getEncoded();

            // 加密解密器
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] ivBytes = {0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf};
            IvParameterSpec aesIvParameterSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec aesSecretKeySpec = new SecretKeySpec(keyBytes, "AES");
            // 加密
//            cipher.init(Cipher.ENCRYPT_MODE, desSecretKey);
            cipher.init(Cipher.ENCRYPT_MODE, aesSecretKeySpec, aesIvParameterSpec);
            byte[] encryptData = cipher.doFinal(plainText.getBytes("UTF-8"));
            // 解密
//            cipher.init(Cipher.DECRYPT_MODE, desSecretKey);
            cipher.init(Cipher.DECRYPT_MODE, aesSecretKeySpec, aesIvParameterSpec);
            byte[] decryptData = cipher.doFinal(encryptData);

            logger.info("plainText: {}", new String(decryptData));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
