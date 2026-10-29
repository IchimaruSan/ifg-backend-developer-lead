package id.ifg.technicaltest.transaction.service;

import id.ifg.technicaltest.transaction.model.ProcessedTransaction;
import id.ifg.technicaltest.transaction.model.TransactionEvent;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionProcessorTest {
    private final TransactionProcessor processor = new TransactionProcessor();

    @Test
    void calculatesFeeAndNetAmount() {
        TransactionEvent event = new TransactionEvent();
        event.eventId = "evt-1001";
        event.customerId = "cust-2001";
        event.amount = new BigDecimal("100000.00");
        event.currency = "idr";

        ProcessedTransaction result = processor.process(event);

        assertEquals("evt-1001", result.eventId);
        assertEquals(new BigDecimal("100000.00"), result.amount);
        assertEquals(new BigDecimal("2000.00"), result.fee);
        assertEquals(new BigDecimal("98000.00"), result.netAmount);
        assertEquals("IDR", result.currency);
    }

    @Test
    void rejectsZeroOrNegativeAmount() {
        TransactionEvent event = new TransactionEvent();
        event.eventId = "evt-1002";
        event.customerId = "cust-2001";
        event.amount = BigDecimal.ZERO;
        event.currency = "IDR";

        assertThrows(IllegalArgumentException.class, () -> processor.process(event));
    }

    @Test
    void rejectsMissingEventId() {
        TransactionEvent event = new TransactionEvent();
        event.customerId = "cust-2001";
        event.amount = new BigDecimal("100.00");
        event.currency = "IDR";

        assertThrows(IllegalArgumentException.class, () -> processor.process(event));
    }
}
