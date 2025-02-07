package showcase.smpp.util;

/**
 * @author: pickjob@126.com
 * @date: 2025-02-07
 */
public enum DeliveryReceiptStatesEnum {
    DELIVERED("DELIVRD"),
    EXPIRED("EXPIRED"),
    DELETED("DELETED"),
    UNDELIVERABLE("UNDELIV"),
    ACCEPTED("ACCEPTD"),
    UNKNOWN("UNKNOWN"),
    REJECTED("REJECTD");

    @Override
    public String toString() {
        return fixName;
    }

    private DeliveryReceiptStatesEnum(String fixName){
        this.fixName = fixName;
    }

    private String fixName;
}
