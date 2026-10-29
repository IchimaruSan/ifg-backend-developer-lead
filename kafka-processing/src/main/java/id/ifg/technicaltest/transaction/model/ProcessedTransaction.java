package id.ifg.technicaltest.transaction.model;

import java.math.BigDecimal;

public class ProcessedTransaction {
    public String eventId;
    public String customerId;
    public BigDecimal amount;
    public BigDecimal fee;
    public BigDecimal netAmount;
    public String currency;

    public ProcessedTransaction() {
    }

    public ProcessedTransaction(
            String eventId,
            String customerId,
            BigDecimal amount,
            BigDecimal fee,
            BigDecimal netAmount,
            String currency) {
        this.eventId = eventId;
        this.customerId = customerId;
        this.amount = amount;
        this.fee = fee;
        this.netAmount = netAmount;
        this.currency = currency;
    }
}
