package com.ifg.assessment.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class CheckoutServiceTest {

    private final CheckoutService checkoutService = new CheckoutService();

    @Test
    void validCheckoutMovesToPaymentPending() {
        CheckoutRequest request = new CheckoutRequest();
        request.setOrderId("ORD-1001");
        request.setCustomerId("CUS-1001");
        request.setTotalAmount(new BigDecimal("125000.00"));

        checkoutService.validateCheckout(request);

        assertEquals("PAYMENT_PENDING", request.getStatus());
        assertEquals(
                "Checkout validated; awaiting payment result",
                request.getMessage()
        );
    }

    @Test
    void checkoutRejectsZeroAmount() {
        CheckoutRequest request = new CheckoutRequest();
        request.setOrderId("ORD-1001");
        request.setCustomerId("CUS-1001");
        request.setTotalAmount(BigDecimal.ZERO);

        assertThrows(
                IllegalArgumentException.class,
                () -> checkoutService.validateCheckout(request)
        );
    }

    @Test
    void approvedPaymentConfirmsOrder() {
        CheckoutRequest request = new CheckoutRequest();

        checkoutService.completeOrder(request);

        assertEquals("CONFIRMED", request.getStatus());
    }

    @Test
    void rejectedPaymentMarksOrderRejected() {
        CheckoutRequest request = new CheckoutRequest();

        checkoutService.rejectOrder(request);

        assertEquals("PAYMENT_REJECTED", request.getStatus());
    }
}
