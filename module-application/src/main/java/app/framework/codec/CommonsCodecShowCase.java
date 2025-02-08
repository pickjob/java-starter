package app.framework.codec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.net.URLCodec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Commons Codec: 编解码
 *      Hex
 *      Base64
 *      URLCodec
 *
 * @author: pickjob@126.com
 * @date: 2024-09-04
 **/
public class CommonsCodecShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        String hello = "Hello World";
        byte[] helloBytes = hello.getBytes();
        logger.info("hex: {}, base64: {}, url: {}",
                Hex.encodeHex(helloBytes),
                Base64.encodeBase64(helloBytes),
                URLCodec.encodeUrl(null, helloBytes));
    }
}