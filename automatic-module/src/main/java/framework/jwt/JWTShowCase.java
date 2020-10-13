package framework.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

/**
 * @author pickjob@126.com
 * @time 2019-05-27
 */
public class JWTShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(JWTShowCase.class);

    @Override
    public void showSomething() {
        logger.info("JWT格式: base64url_encode(Header) + '.' + base64url_encode(Claims) + '.' + base64url_encode(Signature)");
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer("auth0")
                .withExpiresAt(new Date(1559004981791l))
                .sign(algorithm);
        logger.info("token: {}", token);
        String[] tokenSplits = token.split("\\.");
        try {
            logger.info(Arrays.toString(tokenSplits));
            Base64.Decoder decoder = Base64.getUrlDecoder();
            byte[] headerBytes = decoder.decode(tokenSplits[0]);
            byte[] playloadBytes = decoder.decode(tokenSplits[1]);
            byte[] signBytes = decoder.decode(tokenSplits[2]);
            logger.info("Base64解码: header: {}, playload: {}, sign: {}",
                    new String(headerBytes, "utf-8"),
                    new String(playloadBytes, "utf-8"),
                    new String(signBytes, "utf-8"));
            DecodedJWT jwt = JWT.decode(token);
            logger.info("JWT解码: header: {}, playload: {}, sign: {}",
                    jwt.getHeader(),
                    jwt.getPayload(),
                    jwt.getSignature());
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        try {
            JWTVerifier rightVerifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = rightVerifier.verify(token);
            logger.info("rightVerifier pass");
        } catch (TokenExpiredException e) {
            logger.error(e.getMessage(), e);
            logger.error("Token expired");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info("rightVerifier failed");
        }
        try {
            algorithm = Algorithm.HMAC256("wrong");
            JWTVerifier wrongVerifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = wrongVerifier.verify(token);
            logger.info("wrongVerifier pass");
        } catch (Exception e) {
            logger.info("wrongVerifier failed");
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
