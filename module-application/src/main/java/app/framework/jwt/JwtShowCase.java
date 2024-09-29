package app.framework.jwt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * Jwt(Json Web Token):
 *      base64url_encode(Header) + '.' + base64url_encode(Claims / Payload) + '.' + base64url_encode(Signature)
 *          Header:
 *              alg(algorithm): 签名算法
 *          Claims / Payload:
 *              jti(JWT ID)：编号
 *              sub(subject)：主题
 *              aud(audience)：受众
 *              iss(issuer)：签发人
 *              nbf(Not Before)：生效时间
 *              exp(expiration time)：过期时间
 *              iat(Issued At)：签发时间
 *          Signature
 *
 * JWS: Signed JWT签名过的jwt
 * JWE: Encrypted JWT部分payload经过加密的jwt；目前加密payload的操作不是很普及；
 * JWK: JWT的密钥，也就是我们常说的 secret；
 *
 * @author pickjob@126.com
 * @date 2024-09-04
 **/
public class JwtShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        //
        // JSON Web Token is a compact URL-safe means of representing claims/attributes to be transferred between two parties.
        //
        // 生成RSA 密钥对
        RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
        // Key id
        rsaJsonWebKey.setKeyId("k1");
        // JWT Claims
        JwtClaims claims = new JwtClaims();
        claims.setGeneratedJwtId();
        claims.setSubject("subject");
        claims.setClaim("email","mail@example.com");
        List<String> groups = Arrays.asList("group-one", "other-group", "group-three");
        claims.setStringListClaim("groups", groups);
        claims.setIssuer("Issuer");
        claims.setAudience("Audience");
        claims.setExpirationTimeMinutesInTheFuture(10);
        claims.setIssuedAtToNow();
        claims.setNotBeforeMinutesInThePast(2);
        // Signature
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(rsaJsonWebKey.getPrivateKey());
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        String jwt = jws.getCompactSerialization();

        logger.info("JWT: {}", jwt);

        // Use JwtConsumerBuilder to construct an appropriate JwtConsumer
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // the JWT must have an expiration time
                .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                .setRequireSubject() // the JWT must have a subject claim
                .setExpectedIssuer("Issuer") // whom the JWT needs to have been issued by
                .setExpectedAudience("Audience") // to whom the JWT is intended for
                .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
                .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
                        AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.RSA_USING_SHA256) // which is only RS256 here
                .build(); // create the JwtConsumer instance
        try {
            //  Validate the JWT and process it to the Claims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
            logger.info("JWT validation succeeded! {}", jwtClaims);
        } catch (InvalidJwtException e) {
            // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
            // Hopefully with meaningful explanations(s) about what went wrong.
            logger.error("Invalid JWT! ", e);
            if (e.hasExpired()) {
                logger.error("JWT expired at {}", e.getJwtContext().getJwtClaims().getExpirationTime());
            }
            if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
                logger.error("JWT had wrong audience: {}", e.getJwtContext().getJwtClaims().getAudience());
            }
        }
    }
}