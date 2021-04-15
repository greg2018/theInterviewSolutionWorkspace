package falcon.interview.impl;

import falcon.interview.api.PrintlnTradeApi;
import falcon.interview.util.InstrumentNameAndValue;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class TradeReportingTest {

    @Test
    public void tradesGetReportedCorrectly() throws InterruptedException {
        CapturingTradeApi capture = new CapturingTradeApi();
        new OptionReporter(capture).performReporting();
        capture.completed.await();
        checkResults(capture.reportedTrades, capture.errorTrades);
    }


    public Boolean checkResults(Map<Long, InstrumentNameAndValue> actualReportedTrades, Set<Long> actualErrorTrades) {
        Assert.assertThat(actualReportedTrades, CoreMatchers.equalTo(getExpectedResults()));
        Assert.assertThat(actualErrorTrades, CoreMatchers.equalTo(getExpectedErrors()));
        return true;
    }

    private static Set<Long> getExpectedErrors() {
        Set<Long> expectedErrors = new HashSet<>();
        expectedErrors.add(10L);
        expectedErrors.add(21L);
        expectedErrors.add(31L);
        expectedErrors.add(42L);
        expectedErrors.add(52L);
        expectedErrors.add(63L);
        expectedErrors.add(73L);
        expectedErrors.add(84L);
        expectedErrors.add(94L);
        return expectedErrors;
    }

    private static Map<Long, InstrumentNameAndValue> getExpectedResults() {
        Map<Long, InstrumentNameAndValue> expected = new HashMap<>();
        expected.put(1L, new InstrumentNameAndValue("Facebook", 200.0));
        expected.put(2L, new InstrumentNameAndValue("Amazon", 600.0));
        expected.put(3L, new InstrumentNameAndValue("Netflix", 1200.0));
        expected.put(4L, new InstrumentNameAndValue("Google", 2000.0));
        expected.put(5L, new InstrumentNameAndValue("Apple", 3000.0));
        expected.put(6L, new InstrumentNameAndValue("Tesla", 4200.0));
        expected.put(7L, new InstrumentNameAndValue("Boeing", 5600.0));
        expected.put(8L, new InstrumentNameAndValue("General Motors", 7200.0));
        expected.put(9L, new InstrumentNameAndValue("Ford", 9000.0));
        expected.put(11L, new InstrumentNameAndValue("Morgan Stanley", 2200.0));
        expected.put(12L, new InstrumentNameAndValue("Citigroup", 3600.0));
        expected.put(13L, new InstrumentNameAndValue("HSBC", 5200.0));
        expected.put(14L, new InstrumentNameAndValue("Bank of America", 7000.0));
        expected.put(15L, new InstrumentNameAndValue("Berkshire Hathaway", 9000.0));
        expected.put(16L, new InstrumentNameAndValue("General Electric", 11200.0));
        expected.put(17L, new InstrumentNameAndValue("McDonalds", 13600.0));
        expected.put(18L, new InstrumentNameAndValue("Exxon Mobil", 16200.0));
        expected.put(19L, new InstrumentNameAndValue("Coca-Cola", 19000.0));
        expected.put(20L, new InstrumentNameAndValue("Walmart", 2000.0));
        expected.put(22L, new InstrumentNameAndValue("Facebook", 300.0));
        expected.put(23L, new InstrumentNameAndValue("Amazon", 800.0));
        expected.put(24L, new InstrumentNameAndValue("Netflix", 1500.0));
        expected.put(25L, new InstrumentNameAndValue("Google", 2400.0));
        expected.put(26L, new InstrumentNameAndValue("Apple", 3500.0));
        expected.put(27L, new InstrumentNameAndValue("Tesla", 4800.0));
        expected.put(28L, new InstrumentNameAndValue("Boeing", 6300.0));
        expected.put(29L, new InstrumentNameAndValue("General Motors", 8000.0));
        expected.put(30L, new InstrumentNameAndValue("Ford", 900.0));
        expected.put(32L, new InstrumentNameAndValue("Morgan Stanley", 3300.0));
        expected.put(33L, new InstrumentNameAndValue("Citigroup", 4800.0));
        expected.put(34L, new InstrumentNameAndValue("HSBC", 6500.0));
        expected.put(35L, new InstrumentNameAndValue("Bank of America", 8400.0));
        expected.put(36L, new InstrumentNameAndValue("Berkshire Hathaway", 10500.0));
        expected.put(37L, new InstrumentNameAndValue("General Electric", 12800.0));
        expected.put(38L, new InstrumentNameAndValue("McDonalds", 15300.0));
        expected.put(39L, new InstrumentNameAndValue("Exxon Mobil", 18000.0));
        expected.put(40L, new InstrumentNameAndValue("Coca-Cola", 1900.0));
        expected.put(41L, new InstrumentNameAndValue("Walmart", 4000.0));
        expected.put(43L, new InstrumentNameAndValue("Facebook", 400.0));
        expected.put(44L, new InstrumentNameAndValue("Amazon", 1000.0));
        expected.put(45L, new InstrumentNameAndValue("Netflix", 1800.0));
        expected.put(46L, new InstrumentNameAndValue("Google", 2800.0));
        expected.put(47L, new InstrumentNameAndValue("Apple", 4000.0));
        expected.put(48L, new InstrumentNameAndValue("Tesla", 5400.0));
        expected.put(49L, new InstrumentNameAndValue("Boeing", 7000.0));
        expected.put(50L, new InstrumentNameAndValue("General Motors", 800.0));
        expected.put(51L, new InstrumentNameAndValue("Ford", 1800.0));
        expected.put(53L, new InstrumentNameAndValue("Morgan Stanley", 4400.0));
        expected.put(54L, new InstrumentNameAndValue("Citigroup", 6000.0));
        expected.put(55L, new InstrumentNameAndValue("HSBC", 7800.0));
        expected.put(56L, new InstrumentNameAndValue("Bank of America", 9800.0));
        expected.put(57L, new InstrumentNameAndValue("Berkshire Hathaway", 12000.0));
        expected.put(58L, new InstrumentNameAndValue("General Electric", 14400.0));
        expected.put(59L, new InstrumentNameAndValue("McDonalds", 17000.0));
        expected.put(60L, new InstrumentNameAndValue("Exxon Mobil", 1800.0));
        expected.put(61L, new InstrumentNameAndValue("Coca-Cola", 3800.0));
        expected.put(62L, new InstrumentNameAndValue("Walmart", 6000.0));
        expected.put(64L, new InstrumentNameAndValue("Facebook", 500.0));
        expected.put(65L, new InstrumentNameAndValue("Amazon", 1200.0));
        expected.put(66L, new InstrumentNameAndValue("Netflix", 2100.0));
        expected.put(67L, new InstrumentNameAndValue("Google", 3200.0));
        expected.put(68L, new InstrumentNameAndValue("Apple", 4500.0));
        expected.put(69L, new InstrumentNameAndValue("Tesla", 6000.0));
        expected.put(70L, new InstrumentNameAndValue("Boeing", 700.0));
        expected.put(71L, new InstrumentNameAndValue("General Motors", 1600.0));
        expected.put(72L, new InstrumentNameAndValue("Ford", 2700.0));
        expected.put(74L, new InstrumentNameAndValue("Morgan Stanley", 5500.0));
        expected.put(75L, new InstrumentNameAndValue("Citigroup", 7200.0));
        expected.put(76L, new InstrumentNameAndValue("HSBC", 9100.0));
        expected.put(77L, new InstrumentNameAndValue("Bank of America", 11200.0));
        expected.put(78L, new InstrumentNameAndValue("Berkshire Hathaway", 13500.0));
        expected.put(79L, new InstrumentNameAndValue("General Electric", 16000.0));
        expected.put(80L, new InstrumentNameAndValue("McDonalds", 1700.0));
        expected.put(81L, new InstrumentNameAndValue("Exxon Mobil", 3600.0));
        expected.put(82L, new InstrumentNameAndValue("Coca-Cola", 5700.0));
        expected.put(83L, new InstrumentNameAndValue("Walmart", 8000.0));
        expected.put(85L, new InstrumentNameAndValue("Facebook", 600.0));
        expected.put(86L, new InstrumentNameAndValue("Amazon", 1400.0));
        expected.put(87L, new InstrumentNameAndValue("Netflix", 2400.0));
        expected.put(88L, new InstrumentNameAndValue("Google", 3600.0));
        expected.put(89L, new InstrumentNameAndValue("Apple", 5000.0));
        expected.put(90L, new InstrumentNameAndValue("Tesla", 600.0));
        expected.put(91L, new InstrumentNameAndValue("Boeing", 1400.0));
        expected.put(92L, new InstrumentNameAndValue("General Motors", 2400.0));
        expected.put(93L, new InstrumentNameAndValue("Ford", 3600.0));
        expected.put(95L, new InstrumentNameAndValue("Morgan Stanley", 6600.0));
        expected.put(96L, new InstrumentNameAndValue("Citigroup", 8400.0));
        expected.put(97L, new InstrumentNameAndValue("HSBC", 10400.0));
        expected.put(98L, new InstrumentNameAndValue("Bank of America", 12600.0));
        expected.put(99L, new InstrumentNameAndValue("Berkshire Hathaway", 15000.0));
        expected.put(100L, new InstrumentNameAndValue("General Electric", 1600.0));
        return expected;
    }


}

class CapturingTradeApi extends PrintlnTradeApi {

    final Map<Long, InstrumentNameAndValue> reportedTrades = new ConcurrentHashMap<>();
    final Set<Long> errorTrades = Collections.newSetFromMap(new ConcurrentHashMap<>());
    final CountDownLatch completed = new CountDownLatch(1);

    @Override
    public void reportTradeValue(long tradeId, String instrumentName, double value) {
        super.reportTradeValue(tradeId, instrumentName, value);
        reportedTrades.put(tradeId, new InstrumentNameAndValue(instrumentName, value));
    }

    @Override
    public void reportErrorTrade(long tradeId) {
        super.reportErrorTrade(tradeId);
        errorTrades.add(tradeId);
    }

    @Override
    public void allTradesReported() {
        super.allTradesReported();
        completed.countDown();
    }
}

