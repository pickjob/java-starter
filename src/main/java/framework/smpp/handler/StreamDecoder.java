package framework.smpp.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import framework.smpp.pdu.Pdu;
import framework.smpp.pdu.PduFactory;

import java.util.List;

public class StreamDecoder extends ByteToMessageDecoder {
	private static Logger logger = LogManager.getLogger(StreamDecoder.class);

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