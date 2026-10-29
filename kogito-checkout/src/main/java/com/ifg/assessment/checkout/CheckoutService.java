package com.ifg.assessment.checkout;

import java.math.BigDecimal;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CheckoutService {

    public void validateCheckout(CheckoutRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Checkout request is required");
        }

        if (request.getOrderId() == null || request.getOrderId().isBlank()) {
            throw new IllegalArgumentException("orderId is required");
        }

        if (request.getCustomerId() == null || request.getCustomerId().isBlank()) {
            throw new IllegalArgumentException("customerId is required");
        }

        BigDecimal totalAmount = request.getTotalAmount();

        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("totalAmount must be greater than zero");
        }

        request.setStatus("PAYMENT_PENDING");
        request.setMessage("Checkout validated; awaiting payment result");
    }

    public void completeOrder(CheckoutRequest request) {
        request.setStatus("CONFIRMED");
        request.setMessage("Payment approved and order confirmed");
    }

    public void rejectOrder(CheckoutRequest request) {
        request.setStatus("PAYMENT_REJECTED");
        request.setMessage("Payment was rejected; order was not confirmed");
    }
}
