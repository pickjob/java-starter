package showcase.smpp.pdu;

import io.netty.buffer.ByteBuf;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public class HeaderPdu implements Stream {
    private int commandLength;
    private int commandId;
    private int commandStatus;
    private int sequenceNumber;

    public int getCommandLength() {
        return commandLength;
    }

    public void setCommandLength(int commandLength) {
        this.commandLength = commandLength;
    }

    public int getCommandId() {
        return commandId;
    }

    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }

    public int getCommandStatus() {
        return commandStatus;
    }

    public void setCommandStatus(int commandStatus) {
        this.commandStatus = commandStatus;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public void encoding(ByteBuf buf) {
        buf.writeInt(commandLength);
        buf.writeInt(commandId);
        buf.writeInt(commandStatus);
        buf.writeInt(sequenceNumber);
    }

    @Override
    public void decoding(ByteBuf buffer) {
        commandLength = buffer.readInt();
        commandId = buffer.readInt();
        commandStatus = buffer.readInt();
        sequenceNumber = buffer.readInt();
    }

    @Override
    public String toString() {
        return "HeaderPdu{" +
                "commandLength=" + commandLength +
                ", commandId=" + commandId +
                ", commandStatus=" + commandStatus +
                ", sequenceNumber=" + sequenceNumber +
                '}';
    }
}
