package showcase.smpp.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import showcase.smpp.pdu.Pdu;
import showcase.smpp.pdu.PduFactory;

import java.util.List;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class StreamDecoder extends ByteToMessageDecoder {
    private static Logger logger = LogManager.getLogger();

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf buf, List<Object> out) throws Exception {
        logger.info("接收字节流\n{}", ByteBufUtil.prettyHexDump(buf));
        try {
            Pdu pdu = PduFactory.decode(buf);
            logger.info(pdu);
            out.add(pdu);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}