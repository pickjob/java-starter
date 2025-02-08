package app.standard.jca;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA:
 *
 * @author: pickjob@126.com
 * @date: 2024-09-10
 */
public class RSAShowcase {
    private static final Logger logger = LogManager.getLogger();

    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCy+UP+qqdfIjWtSAle8aSjBdrA" +
            "GC19ZMUHrii96JbMTgJx3gkx6x/PfBKeKKH/GQKI6XBRwwK9ugWtBtHXZwUomiJn" +
            "Sw2xwEkmEKmqIpiiJGfVkjgzPN76PVwaXYT80xCGIObu/LotyrwLazB2eOeuBEk5" +
            "8NjQmxAl+f1wH9CZ0QIDAQAB";

    private static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALL5Q/6qp18iNa1I" +
            "CV7xpKMF2sAYLX1kxQeuKL3olsxOAnHeCTHrH898Ep4oof8ZAojpcFHDAr26Ba0G" +
            "0ddnBSiaImdLDbHASSYQqaoimKIkZ9WSODM83vo9XBpdhPzTEIYg5u78ui3KvAtr" +
            "MHZ4564ESTnw2NCbECX5/XAf0JnRAgMBAAECgYAEYsNdzETJugbiti80mmtUa2JO" +
            "7si+l+eUJpps/PQlEYxRygUjqgVfikTSh2Bg5fX/8OiFuGeqypI1CUO/KZEgwRdR" +
            "n6zLqp468XZ/i6Ci+0eyJcmc/nVRysyLl0znl5nbBfPKe2adJwILVvznqQ5dEmAi" +
            "/SP6o/SS3WpnTj9y2QJBANycy2EqirnzZIuIhD21INLWbwS6xwcYk0crh4v9kyOt" +
            "H89Wg8N30UvqHrE0ww/BUjrfGHVfBeHK3QdYgL1Qek8CQQDPrp7xeMbW2X37frc/" +
            "uAz34DyNhy5Zv/x2S5wWoWmawQdYcrNm/gyJJlo7D10FXDdTQ6giDmPCpChudP/b" +
            "2EHfAkAqZcvEUbu1fuw80LCxd0TmoCVX4FltqLBtRhVXtxT3D0eEvli2USmdEFJz" +
            "tfMi7QFTpRp7PBbSTP1rcvvS5kuLAkEAhPPSFt3t1S8o4q7NW0JVMv5fZW8r8hLg" +
            "6U4gH7C3rXVzYWEUjpOGrOpbi0GbmEnftTMz2JGDteBnbccC4NfB2wJALq2c6lYV" +
            "hdvGxJ1g2p9OU/djPlqPg5ByqL7niUYkNnoPS/p6NyuvwfcTBLnZfV4xu8UsCYQr" +
            "9nJ8BNRAl4p9pQ==";

    public static void main(String[] args) throws Exception {
        String plainText = "今天天气很好阿";

        // 加密解密器
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        KeyFactory rsaKeyFactory = KeyFactory.getInstance("RSA");
        // RSA公钥 X509编码
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        PublicKey publicKey = rsaKeyFactory.generatePublic(publicKeySpec);
        // 私钥 PKCS8编码
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        PrivateKey privateKey = rsaKeyFactory.generatePrivate(privateKeySpec);

        // 加密
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptData = cipher.doFinal(plainText.getBytes());
        // 解密
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptData = cipher.doFinal(encryptData);

        logger.info("plainText: {}", new String(decryptData));
    }
}