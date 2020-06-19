package smpp.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import smpp.pdu.Pdu;
import smpp.pdu.PduFactory;

public class StreamEncoder extends MessageToByteEncoder<Pdu> {
	private final static Logger logger = LogManager.getLogger(StreamEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext context, Pdu pdu, ByteBuf buf) throws Exception {
		try {
			PduFactory.encode(buf, pdu);
			logger.info(pdu);
			logger.info("发送字节流\n{}", ByteBufUtil.prettyHexDump(buf));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
