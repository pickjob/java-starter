package smpp.util;

// Fixed Length Stat String
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
