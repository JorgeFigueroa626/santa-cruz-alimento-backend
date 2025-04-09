package santa_cruz_alimento_backend.util.constant;

public enum ReplyProduction {
    EN_PROCESO("EN_PROCESO"),
    CANCELADO("CANCELADO"),
    PRODUCIDO("PRODUCIDO");


    private final String status;

    ReplyProduction(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
