package basic.crpto;

import basic.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author pickjob@126.com
 * @time 2019-10-26
 */
public class RSAShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(RSAShowCase.class);

    @Override
    public void saySomething() {
        logger.info("RSA加解密示例");
    }

    @Override
    public void showSomething() {
        try {
            String plainText = "今天天气很好阿";
            KeyPairGenerator rsaKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
            rsaKeyPairGenerator.initialize(2048);
            KeyPair keyPair = rsaKeyPairGenerator.generateKeyPair();
            byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
            byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();

            KeyFactory rsaKeyFactory = KeyFactory.getInstance("RSA");
            // RSA公钥 X509编码
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = rsaKeyFactory.generatePublic(publicKeySpec);
            // 私钥 PKCS8编码
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = rsaKeyFactory.generatePrivate(privateKeySpec);
            // 加密解密器
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // 加密
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptData = cipher.doFinal(plainText.getBytes());
            // 解密
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptData = cipher.doFinal(encryptData);

            logger.info("plainText: {}", new String(decryptData));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
