package falcon.interview.impl;

import falcon.interview.api.Instrument;
import falcon.interview.api.PrintlnTradeApi;
import falcon.interview.api.Trade;
import falcon.interview.api.TradeApi;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * You have been presented the following code which is meant to solve the requirement of reporting trade values.
 * The developer who wrote this was asked to leave the company and you need to refactor it to make it work.
 * You have been provided with the API documented in TradeApi and the implementation PrintlnTradeApi which
 * will record your reporting with println statements.
 *
 * You are free to inspect the source of the falcon.interview.api package but will be marked down if you change it.
 * Assume this has been provided in some library JAR which you don't control.
 *
 */
public class OptionReporter {

    TradeApi api;
    LinkedBlockingQueue<Trade> queue = new LinkedBlockingQueue<>();
    //int queueDepth = 0;

    public OptionReporter(TradeApi api) {
        this.api = api;
    }

    AtomicBoolean publisherNotCompleted =new  AtomicBoolean(true);

    public void performReporting() {
        List<Instrument> allInstruments = api.getAllInstruments();

        Map<Integer, Instrument> allInstrumentMap = new HashMap<>();
        for(Instrument instrument : allInstruments){
            allInstrumentMap.put(instrument.getId(), instrument);
        }
        new Thread(() -> {
            Trade nextTrade = api.getNextTrade();
            while (nextTrade != null) {
                queue.add(nextTrade);
                //queueDepth++;
                nextTrade = api.getNextTrade();
               // System.out.println("++++++Thread01->"+ queueDepth);
                System.out.println("++++++Thread01->");
            }
            publisherNotCompleted.set(false);
        }).start();
        new Thread(() -> {
            while (publisherNotCompleted.get() || !queue.isEmpty()) {
                Trade trade = null;
                try {
                    trade = queue.take();
                    //trade = queue.poll();
                    Instrument instrument = allInstrumentMap.get(trade.getInstrumentId());
                    if(instrument == null)
                        throw new RuntimeException("Failed to report trade Id" +trade.getId());
                    double unitValue = api.valueInstrument(instrument);
                    String name = instrument.getName();
                    double value = unitValue * trade.getQuantity();
                    api.reportTradeValue(trade.getId(), name, value);
                } catch (Throwable t) {
                    api.reportErrorTrade(trade.getId());
                }

            }
            api.allTradesReported();
            publisherNotCompleted.set(true);


        }).start();
    }

    private String getName(List<Instrument> allInstruments, Trade x) {
        return allInstruments.stream()
                //.filter(inst -> inst.getId() == x.getId())
                .filter(inst -> inst.getId() == x.getInstrumentId())

                .map(inst -> inst.getName())
                .collect(Collectors.toList()).get(0);
    }

    public static void main(String[] args) {
        new OptionReporter(new PrintlnTradeApi()).performReporting();
        System.out.println("all done");
    }

}
