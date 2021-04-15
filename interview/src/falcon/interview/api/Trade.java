package falcon.interview.api;

public class Trade {

    private final long id;
    private final int instrumentId;
    private final int quantity;

    public Trade(long id, int instrumentId, int quantity) {
        this.id = id;
        this.instrumentId = instrumentId;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public int getInstrumentId() {
        return instrumentId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", instrumentId=" + instrumentId +
                ", quantity=" + quantity +
                '}';
    }
}
