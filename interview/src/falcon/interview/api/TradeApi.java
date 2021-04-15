package falcon.interview.api;

import java.util.List;

public interface TradeApi {

    /**
     * Get a list of all instruments.
     * @return a list of the entire instrument universe.  You can assume this list never changes.
     */
    List<Instrument> getAllInstruments();

    /**
     * Get the next trade.
     * @return the next trade.  Eventually it returns null which means there are no more today.
     */
    Trade getNextTrade();

    /**
     * Value an instrument.
     * @param instrument The instrument to value.
     * @return The value of the instrument.
     */
    double valueInstrument(Instrument instrument);

    /**
     * Report the value of a trade. You must call this once for each trade when you have valued it.
     * @param tradeId The id of the trade which was returned by getNextTrade
     * @param instrumentName The name of the instrument
     * @param value The value of the trade.  Should be the value of the instrument * the quantity of the trade.
     */
    void reportTradeValue(long tradeId, String instrumentName, double value);

    /**
     * Report an error trade. You can call this once for each trade instead of reportTradeValue if there is a problem.
     * @param tradeId The id of the trade.
     */
    void reportErrorTrade(long tradeId);

    /**
     * Record the fact that all trades have been reported and the task is complete.
     */
    void allTradesReported();
}
