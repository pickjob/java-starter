package basic.crpto;

import basic.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;

/**
 * @author pickjob@126.com
 * @time 2019-10-26
 */
public class SignatureShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(SignatureShowCase.class);

    @Override
    public void saySomething() {
        logger.info("Signature示例");
    }

    @Override
    public void showSomething() {
        try {
            String plainText = "今天天气很好阿";
            KeyPairGenerator rsaKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
            rsaKeyPairGenerator.initialize(2048);
            KeyPair keyPair = rsaKeyPairGenerator.generateKeyPair();

            Signature sha1withRSASignature = Signature.getInstance("SHA1withRSA");
            sha1withRSASignature.initSign(keyPair.getPrivate());
            sha1withRSASignature.update(plainText.getBytes());
            byte[] signature = sha1withRSASignature.sign();

            sha1withRSASignature.initVerify(keyPair.getPublic());
            sha1withRSASignature.update(plainText.getBytes());
            logger.info("verifyResult: {}", sha1withRSASignature.verify(signature));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
