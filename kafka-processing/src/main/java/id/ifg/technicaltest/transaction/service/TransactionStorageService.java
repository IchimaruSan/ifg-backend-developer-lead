package id.ifg.technicaltest.transaction.service;

import id.ifg.technicaltest.transaction.entity.TransactionRecord;
import id.ifg.technicaltest.transaction.model.ProcessedTransaction;
import id.ifg.technicaltest.transaction.model.TransactionEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.Instant;

@ApplicationScoped
public class TransactionStorageService {
    private static final Logger LOG = Logger.getLogger(TransactionStorageService.class);

    @Inject
    TransactionProcessor processor;

    /**
     * Persist one processed event atomically.
     * eventId is the idempotency key: duplicate Kafka deliveries are ignored.
     */
    @Transactional
    public void processAndStore(TransactionEvent event) {
        if (event != null && event.eventId != null) {
            TransactionRecord existing = TransactionRecord.findById(event.eventId.trim());
            if (existing != null) {
                LOG.infof("Ignoring duplicate transaction event: %s", event.eventId);
                return;
            }
        }

        ProcessedTransaction result = processor.process(event);
        TransactionRecord record = new TransactionRecord();
        record.eventId = result.eventId;
        record.customerId = result.customerId;
        record.amount = result.amount;
        record.fee = result.fee;
        record.netAmount = result.netAmount;
        record.currency = result.currency;
        record.processedAt = Instant.now();
        record.persist();

        LOG.infof("Processed transaction %s; net amount=%s %s",
                result.eventId, result.netAmount, result.currency);
    }
}
