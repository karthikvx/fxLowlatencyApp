package org.example;

// 6. Kafka Producer Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FxProducerService {

    private static final String FX_QUOTES_TOPIC = "fx-quotes";

    @Autowired
    private KafkaTemplate<String, FxQuote> kafkaTemplate;

    public Mono<Void> sendQuote(FxQuote quote) {
        return Mono.fromFuture(() ->
                kafkaTemplate.send(FX_QUOTES_TOPIC, quote.getSymbol(), quote)
                        .completable()
        ).then();
    }
}
