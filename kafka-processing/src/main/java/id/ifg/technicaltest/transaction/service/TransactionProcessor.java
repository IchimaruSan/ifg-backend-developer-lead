package id.ifg.technicaltest.transaction.service;

import id.ifg.technicaltest.transaction.model.ProcessedTransaction;
import id.ifg.technicaltest.transaction.model.TransactionEvent;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

@ApplicationScoped
public class TransactionProcessor {
    private static final BigDecimal FEE_RATE = new BigDecimal("0.02");

    public ProcessedTransaction process(TransactionEvent event) {
        validate(event);

        BigDecimal amount = event.amount.setScale(2, RoundingMode.HALF_UP);
        BigDecimal fee = amount.multiply(FEE_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal netAmount = amount.subtract(fee).setScale(2, RoundingMode.HALF_UP);

        return new ProcessedTransaction(
                event.eventId.trim(),
                event.customerId.trim(),
                amount,
                fee,
                netAmount,
                event.currency.trim().toUpperCase(Locale.ROOT)
        );
    }

    private void validate(TransactionEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Transaction event must not be null");
        }
        if (isBlank(event.eventId)) {
            throw new IllegalArgumentException("eventId is required");
        }
        if (isBlank(event.customerId)) {
            throw new IllegalArgumentException("customerId is required");
        }
        if (event.amount == null || event.amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        if (isBlank(event.currency) || event.currency.trim().length() != 3) {
            throw new IllegalArgumentException("currency must be a 3-character code, e.g. IDR");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
