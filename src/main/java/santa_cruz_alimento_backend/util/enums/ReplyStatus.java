package santa_cruz_alimento_backend.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ReplyStatus {
    INACTIVE(0),
    ACTIVE(1);

    private final int value;

    ReplyStatus(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static ReplyStatus fromValue(Object value) {
        if (value instanceof Integer) {
            for (ReplyStatus status : ReplyStatus.values()) {
                if (status.value == (Integer) value) {
                    return status;
                }
            }
        } else if (value instanceof String) {
            try {
                return ReplyStatus.valueOf(((String) value).toUpperCase()); // Convierte cadenas como "ACTIVE"
            } catch (IllegalArgumentException e) {
                try {
                    int intValue = Integer.parseInt((String) value);
                    return fromValue(intValue);
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Invalid ReplyStatus value: " + value);
                }
            }
        }
        throw new IllegalArgumentException("Invalid ReplyStatus value: " + value);
    }
}
