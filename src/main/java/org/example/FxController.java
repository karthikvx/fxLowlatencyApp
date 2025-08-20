package org.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;

@RestController
@RequestMapping("/api/fx")
public class FxController {

    @Autowired
    private FxProducerService producerService;

    @Autowired
    private FxStreamService streamService;

    // High-throughput quote ingestion
    @PostMapping("/quotes")
    public Mono<String> publishQuote(@RequestBody FxQuote quote) {
        return producerService.sendQuote(quote)
                .map(result -> "Quote published: " + quote.getSymbol())
                .onErrorReturn("Failed to publish quote");
    }

    // Batch quote ingestion
    @PostMapping("/quotes/batch")
    public Mono<String> publishQuotes(@RequestBody Flux<FxQuote> quotes) {
        return quotes
                .flatMap(producerService::sendQuote)
                .count()
                .map(count -> "Published " + count + " quotes");
    }

    // Real-time processed data stream
    @GetMapping(value = "/stream/{symbol}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProcessedFxData> streamProcessedData(@PathVariable String symbol) {
        return streamService.getProcessedDataStream(symbol)
                .delayElements(Duration.ofMillis(100)); // Throttle to 10 updates/sec
    }

    // Health check
    @GetMapping("/health")
    public Mono<String> health() {
        return Mono.just("FX Service is running");
    }
}
