# IFG Technical Test — Question 2: Marketplace Checkout BPMN

Java 17 + Quarkus 3.15.3 + Kogito/jBPM 10.1 BPMN workflow example.

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

The application uses port `8081`. Swagger UI, when exposed by the selected extension, is at `http://localhost:8081/q/swagger-ui/`. Check startup logs for the generated process endpoint for process ID `checkout`; the exact generated endpoint depends on the installed Kogito/jBPM extension version.

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

## Important implementation notes

- The payment provider is represented by the input field `paymentApproved`; no real payment is charged.
- `CheckoutService` validates required IDs and requires a positive amount.
- The BPMN contains BPMN DI layout data so visual BPMN editors can render the diagram.
- Payment integration in production should use an adapter, idempotency keys, timeouts, bounded retries, and reconciliation. Avoid treating a client-supplied payment decision as authoritative in a real system.
- Kogito APIs and extension coordinates are version-sensitive. Verify the Maven build with `mvn clean test` in the target environment before submission.
