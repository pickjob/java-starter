package app.standard.jca;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES: Advanced Encryption Standard
 *
 * @author pickjob@126.com
 * @date 2024-09-10
 */
public class AESShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        String plainText = "今天天气很好阿";

        // 加密解密器
        // Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        KeyGenerator desKeyGenerator = KeyGenerator.getInstance("AES");
        byte[] keyBytes = desKeyGenerator.generateKey().getEncoded();
        byte[] ivBytes = {0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf};
        IvParameterSpec aesIvParameterSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec aesSecretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // 加密
        // cipher.init(Cipher.ENCRYPT_MODE, desSecretKey);
        cipher.init(Cipher.ENCRYPT_MODE, aesSecretKeySpec, aesIvParameterSpec);
        byte[] encryptData = cipher.doFinal(plainText.getBytes("UTF-8"));
        // 解密
        // cipher.init(Cipher.DECRYPT_MODE, desSecretKey);
        cipher.init(Cipher.DECRYPT_MODE, aesSecretKeySpec, aesIvParameterSpec);
        byte[] decryptData = cipher.doFinal(encryptData);

        logger.info("plainText: {}", new String(decryptData));
    }
}