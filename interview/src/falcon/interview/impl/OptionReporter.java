package falcon.interview.impl;

import falcon.interview.api.Instrument;
import falcon.interview.api.PrintlnTradeApi;
import falcon.interview.api.Trade;
import falcon.interview.api.TradeApi;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
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
    int queueDepth = 0;

    public OptionReporter(TradeApi api) {
        this.api = api;
    }

    public void performReporting() {
        List<Instrument> allInstruments = api.getAllInstruments();
        new Thread(() -> {
            Trade nextTrade = api.getNextTrade();
            while (nextTrade != null) {
                queue.add(nextTrade);
                queueDepth++;
                nextTrade = api.getNextTrade();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                if (queueDepth == 0) {
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                    }
                } else {
                    Trade x = queue.poll();
                    queueDepth--;
                    double y = api.valueInstrument(allInstruments.get(x.getInstrumentId()));
                    String z = getName(allInstruments, x);
                    try {
                        double v = y * x.getQuantity();
                        api.reportTradeValue(x.getId(), z, v);
                    } catch (Throwable t) {
                        api.reportErrorTrade(x.getId());
                    }
                    api.allTradesReported();
                }
            }
        }).start();
    }

    private String getName(List<Instrument> allInstruments, Trade x) {
        return allInstruments.stream()
                .filter(inst -> inst.getId() == x.getId())
                .map(inst -> inst.getName())
                .collect(Collectors.toList()).get(0);
    }

    public static void main(String[] args) {
        new OptionReporter(new PrintlnTradeApi()).performReporting();
        System.out.println("all done");
    }

}
