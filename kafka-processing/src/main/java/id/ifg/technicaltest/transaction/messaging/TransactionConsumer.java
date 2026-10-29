package id.ifg.technicaltest.transaction.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ifg.technicaltest.transaction.model.TransactionEvent;
import id.ifg.technicaltest.transaction.service.TransactionStorageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class TransactionConsumer {
    private static final Logger LOG = Logger.getLogger(TransactionConsumer.class);

    @Inject
    ObjectMapper objectMapper;

    @Inject
    TransactionStorageService storageService;

    @Incoming("transactions-in")
    public void consume(String payload) {
        try {
            TransactionEvent event = objectMapper.readValue(payload, TransactionEvent.class);
            storageService.processAndStore(event);
        } catch (Exception exception) {
            LOG.errorf(exception, "Could not process Kafka message: %s", payload);
            throw new RuntimeException("Transaction event processing failed", exception);
        }
    }
}
