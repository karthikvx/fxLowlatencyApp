package org.example;
public class ProcessedFxData {
    private String symbol;
    private BigDecimal midPrice;
    private BigDecimal volatility;
    private String trend;
    private LocalDateTime processedAt;

    // Constructors and getters/setters similar to FxQuote
    public ProcessedFxData() {}

    public ProcessedFxData(String symbol, BigDecimal midPrice, BigDecimal volatility, String trend) {
        this.symbol = symbol;
        this.midPrice = midPrice;
        this.volatility = volatility;
        this.trend = trend;
        this.processedAt = LocalDateTime.now();
    }

    // Getters and setters...
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public BigDecimal getMidPrice() { return midPrice; }
    public void setMidPrice(BigDecimal midPrice) { this.midPrice = midPrice; }

    public BigDecimal getVolatility() { return volatility; }
    public void setVolatility(BigDecimal volatility) { this.volatility = volatility; }

    public String getTrend() { return trend; }
    public void setTrend(String trend) { this.trend = trend; }

    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
}

