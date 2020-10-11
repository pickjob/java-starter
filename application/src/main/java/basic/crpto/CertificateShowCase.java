package basic.crpto;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class CertificateShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(CertificateShowCase.class);

    @Override
    public void showSomething() {
        logger.info("示例Certificate用法");
        try {
            URL url = new URL("https://cn.bing.com");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            // 使用自定义验证证书
            TrustManager[] myTrustManager = {new MyTrustManaer()};
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, myTrustManager, new SecureRandom());
            connection.setSSLSocketFactory(context.getSocketFactory());
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            inputStream.close();
            logger.info("response: {}", stringBuilder.toString());
        } catch (Exception e) {
            logger.error("error", e);
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}

class MyTrustManaer implements X509TrustManager {
    private static final Logger logger = LogManager.getLogger(MyTrustManaer.class);
    private static final Map<String, Certificate> map = new HashMap<>();

    static {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(new FileInputStream(System.getProperty("java.home") + "/lib/security/cacerts"), null);
            Enumeration<String> enumeration = keyStore.aliases();
            while (enumeration.hasMoreElements()) {
                Certificate certificate = keyStore.getCertificate(enumeration.nextElement());
                if (certificate instanceof X509Certificate) {
                    X509Certificate x509Certificate = (X509Certificate) certificate;
                    map.put(x509Certificate.getIssuerX500Principal().getName(), x509Certificate);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        for (int i = 0; i < chain.length; i++) {
            X509Certificate certificate = chain[i] ;
            Certificate parentCertificate = null;
            if (i < chain.length -1) {
                parentCertificate = chain[i+1];
            } else {
                try {
                    parentCertificate = map.get(certificate.getIssuerX500Principal().getName());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            try {
                certificate.verify(parentCertificate.getPublicKey());
                logger.info("{} 校验通过", certificate.getSubjectX500Principal().getName());
            } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | SignatureException e) {
                logger.error(e.getMessage(), e);
                throw new CertificateException(e);
            }
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}