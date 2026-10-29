package id.ifg.technicaltest.transaction.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "processed_transactions")
public class TransactionRecord extends PanacheEntityBase {
    @Id
    @Column(name = "event_id", nullable = false, length = 100)
    public String eventId;

    @Column(name = "customer_id", nullable = false, length = 100)
    public String customerId;

    @Column(nullable = false, precision = 19, scale = 2)
    public BigDecimal amount;

    @Column(nullable = false, precision = 19, scale = 2)
    public BigDecimal fee;

    @Column(name = "net_amount", nullable = false, precision = 19, scale = 2)
    public BigDecimal netAmount;

    @Column(nullable = false, length = 3)
    public String currency;

    @Column(name = "processed_at", nullable = false)
    public Instant processedAt;
}
