package app.standard.jca;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;

/**
 * @author pickjob@126.com
 * @date 2024-09-10
 */
public class SignatureShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        String plainText = "今天天气很好阿";

        Signature sha1withRSASignature = Signature.getInstance("SHA1withRSA");
        KeyPairGenerator rsaKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
        rsaKeyPairGenerator.initialize(2048);
        KeyPair keyPair = rsaKeyPairGenerator.generateKeyPair();

        sha1withRSASignature.initSign(keyPair.getPrivate());
        sha1withRSASignature.update(plainText.getBytes());
        byte[] signature = sha1withRSASignature.sign();

        sha1withRSASignature.initVerify(keyPair.getPublic());
        sha1withRSASignature.update(plainText.getBytes());
        logger.info("verifyResult: {}", sha1withRSASignature.verify(signature));
    }
}