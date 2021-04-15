package falcon.interview.api;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PrintlnTradeApi implements TradeApi {

    public static final int MAX_TRADE_ID = 100;

    private final Map<Integer, Instrument> instruments;
    private final AtomicLong loaded = new AtomicLong(0);
    private final Set<Long> reportedTrades = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public PrintlnTradeApi() {
        instruments = new HashMap<>();
        instruments.put(1, new Instrument(1, "Facebook"));
        instruments.put(2, new Instrument(2, "Amazon"));
        instruments.put(3, new Instrument(3, "Netflix"));
        instruments.put(4, new Instrument(4, "Google"));
        instruments.put(5, new Instrument(5, "Apple"));
        instruments.put(6, new Instrument(6, "Tesla"));
        instruments.put(7, new Instrument(7, "Boeing"));
        instruments.put(8, new Instrument(8, "General Motors"));
        instruments.put(9, new Instrument(9, "Ford"));
        instruments.put(15, new Instrument(15, "Berkshire Hathaway"));
        instruments.put(16, new Instrument(16, "General Electric"));
        instruments.put(17, new Instrument(17, "McDonalds"));
        instruments.put(18, new Instrument(18, "Exxon Mobil"));
        instruments.put(19, new Instrument(19, "Coca-Cola"));
        instruments.put(20, new Instrument(20, "Walmart"));
        instruments.put(10, new Instrument(10, "Goldman Sachs"));
        instruments.put(11, new Instrument(11, "Morgan Stanley"));
        instruments.put(12, new Instrument(12, "Citigroup"));
        instruments.put(13, new Instrument(13, "HSBC"));
        instruments.put(14, new Instrument(14, "Bank of America"));
    }

    public List<Instrument> getAllInstruments() {
        return new ArrayList(instruments.values());
    }

    public Trade getNextTrade() {
        long tradeId = loaded.incrementAndGet();
        if (tradeId > MAX_TRADE_ID) {
            return null;
        }
        int instrumentId = (int) (tradeId % (instruments.size() + 1));
        int quantity = 100 * ((int) (tradeId % 10) + 1);
        Trade trade = new Trade(tradeId, instrumentId, quantity);
        try {
            Thread.sleep((tradeId % 10) * 10);
        } catch (InterruptedException e) {
        }
        return trade;
    }

    public double valueInstrument(Instrument instrument) {
        try {
            Thread.sleep((instrument.getId() % 10) * 50);
        } catch (InterruptedException e) {
        }
        if (instrument.getId() == 10) {
            throw new RuntimeException("Can't value instrument 10");
        }
        return instrument.getId() * 1.0;
    }

    public void reportTradeValue(long tradeId, String instrumentName, double value) {
        checkSingleReportPerTrade(tradeId);
        System.out.println(String.format("Thank you for reporting trade %d for instrument %s which has value %f", tradeId, instrumentName, value));
    }

    public void reportErrorTrade(long tradeId) {
        checkSingleReportPerTrade(tradeId);
        System.err.println(String.format("Thank you for reporting an error for trade %d", tradeId));
    }

    public void allTradesReported() {
        System.out.println("Thank you and goodbye.");
    }

    void checkSingleReportPerTrade(long tradeId) {
        if (!reportedTrades.add(tradeId)) {
            throw new IllegalArgumentException(String.format("trade %d has already been reported", tradeId));
        }
    }

}
