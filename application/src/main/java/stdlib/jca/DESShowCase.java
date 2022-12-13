package stdlib.jca;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES: Data Encryption Standard
 *
 * @author pickjob@126.com
 * @date 2022-12-13
 */
public class DESShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            String plainText = "今天天气很好阿";
            KeyGenerator desKeyGenerator = KeyGenerator.getInstance("DES");
            byte[] keyBytes = desKeyGenerator.generateKey().getEncoded();

            // 加密解密器
//            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            byte[] ivBytes = {0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7};
            IvParameterSpec desIvParameterSpec = new IvParameterSpec(ivBytes);
            DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory desSecretKeyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey desSecretKey = desSecretKeyFactory.generateSecret(desKeySpec);
            // 加密
//            cipher.init(Cipher.ENCRYPT_MODE, desSecretKey);
            cipher.init(Cipher.ENCRYPT_MODE, desSecretKey, desIvParameterSpec);
            byte[] encryptData = cipher.doFinal(plainText.getBytes());
            // 解密
//            cipher.init(Cipher.DECRYPT_MODE, desSecretKey);
            cipher.init(Cipher.DECRYPT_MODE, desSecretKey, desIvParameterSpec);
            byte[] decryptData = cipher.doFinal(encryptData);

            logger.info("plainText: {}", new String(decryptData));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
