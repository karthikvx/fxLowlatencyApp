package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class QuoteWindow {
    private List<FxQuote> quotes = new ArrayList<>();
    private BigDecimal sumMidPrices = BigDecimal.ZERO;
    private BigDecimal sumSquaredDiffs = BigDecimal.ZERO;

    public QuoteWindow addQuote(FxQuote quote) {
        quotes.add(quote);
        BigDecimal midPrice = quote.getBid().add(quote.getAsk()).divide(new BigDecimal("2"));
        sumMidPrices = sumMidPrices.add(midPrice);
        return this;
    }

    public BigDecimal getAverageMidPrice() {
        if (quotes.isEmpty()) return BigDecimal.ZERO;
        return sumMidPrices.divide(new BigDecimal(quotes.size()), 6, RoundingMode.HALF_UP);
    }

    public BigDecimal getVolatility() {
        if (quotes.size() < 2) return BigDecimal.ZERO;

        BigDecimal avg = getAverageMidPrice();
        BigDecimal variance = BigDecimal.ZERO;

        for (FxQuote quote : quotes) {
            BigDecimal midPrice = quote.getBid().add(quote.getAsk()).divide(new BigDecimal("2"));
            BigDecimal diff = midPrice.subtract(avg);
            variance = variance.add(diff.multiply(diff));
        }

        variance = variance.divide(new BigDecimal(quotes.size() - 1), 6, RoundingMode.HALF_UP);
        return new BigDecimal(Math.sqrt(variance.doubleValue())).setScale(6, RoundingMode.HALF_UP);
    }

    public String getTrend() {
        if (quotes.size() < 2) return "NEUTRAL";

        FxQuote first = quotes.get(0);
        FxQuote last = quotes.get(quotes.size() - 1);

        BigDecimal firstMid = first.getBid().add(first.getAsk()).divide(new BigDecimal("2"));
        BigDecimal lastMid = last.getBid().add(last.getAsk()).divide(new BigDecimal("2"));

        if (lastMid.compareTo(firstMid) > 0) return "UP";
        if (lastMid.compareTo(firstMid) < 0) return "DOWN";
        return "NEUTRAL";
    }
}
