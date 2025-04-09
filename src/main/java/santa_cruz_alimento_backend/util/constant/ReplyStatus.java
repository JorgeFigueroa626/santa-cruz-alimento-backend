package santa_cruz_alimento_backend.util.constant;

public enum ReplyStatus {
    INACTIVE(0),
    ACTIVE(1);

    private final int value;

    ReplyStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
