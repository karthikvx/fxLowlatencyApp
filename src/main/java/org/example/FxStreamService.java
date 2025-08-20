package org.example;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

@Service
@EnableKafkaStreams
public class FxStreamService {

    private final Sinks.Many<ProcessedFxData> processedDataSink =
            Sinks.many().multicast().onBackpressureBuffer();

    @Bean
    public KStream<String, FxQuote> processQuotes(StreamsBuilder builder) {
        JsonSerde<FxQuote> quoteSerde = new JsonSerde<>(FxQuote.class);
        JsonSerde<ProcessedFxData> processedSerde = new JsonSerde<>(ProcessedFxData.class);

        KStream<String, FxQuote> quoteStream = builder
                .stream("fx-quotes", Consumed.with(Serdes.String(), quoteSerde));

        // Process quotes with windowing for volatility calculation
        quoteStream
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofSeconds(10)))
                .aggregate(
                        () -> new QuoteWindow(),
                        (key, quote, window) -> window.addQuote(quote),
                        Materialized.with(Serdes.String(), new JsonSerde<>(QuoteWindow.class))
                )
                .toStream()
                .map((windowedKey, window) -> {
                    String symbol = windowedKey.key();
                    ProcessedFxData processed = calculateMetrics(symbol, window);

                    // Emit to reactive stream
                    processedDataSink.tryEmitNext(processed);

                    return KeyValue.pair(symbol, processed);
                })
                .to("fx-processed", Produced.with(Serdes.String(), processedSerde));

        return quoteStream;
    }

    private ProcessedFxData calculateMetrics(String symbol, QuoteWindow window) {
        BigDecimal midPrice = window.getAverageMidPrice();
        BigDecimal volatility = window.getVolatility();
        String trend = window.getTrend();

        return new ProcessedFxData(symbol, midPrice, volatility, trend);
    }

    public Flux<ProcessedFxData> getProcessedDataStream(String symbol) {
        return processedDataSink.asFlux()
                .filter(data -> data.getSymbol().equals(symbol));
    }
}