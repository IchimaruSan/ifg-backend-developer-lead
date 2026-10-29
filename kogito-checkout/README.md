# Technical Test — Question 2: Marketplace Checkout BPMN

Java 17 + Quarkus 3.15.3 + BPMN workflow example.

## Workflow

`Checkout Requested` → `Validate Checkout` → `Payment Approved?`

- If `paymentApproved == true`: `Complete Order` → `Order Completed`.
- Otherwise: `Reject Order` → `Order Rejected`.

The BPMN file is `src/main/resources/checkout.bpmn`. Its service tasks are named `validateCheckout`, `completeOrder`, and `rejectOrder`; those names correspond to methods on `CheckoutService`.

## Model

`CheckoutRequest` is the process data model. Input fields are `orderId`, `customerId`, `totalAmount`, and `paymentApproved`. Output fields `status` and `message` are filled by the workflow service methods.

## Run

Requirements: JDK 17, Maven 3.9+, and internet access for the first dependency download.

```bash
mvn clean test
mvn quarkus:dev
```

The application runs on port 8081.

Testing the Checkout API

The checkout process can be tested directly through the generated Kogito REST endpoint without using Swagger UI.

Endpoint

POST http://localhost:8081/checkout

## Sample input

Approved: `sample-checkout-approved.json`

Rejected: `sample-checkout-rejected.json`

Example:

```json
{
  "checkoutRequest": {
    "orderId": "ORD-1001",
    "customerId": "CUS-1001",
    "totalAmount": 125000.00,
    "paymentApproved": true
  }
}
```