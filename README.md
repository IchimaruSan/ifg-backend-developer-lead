# IFG Backend Developer Lead — Technical Assessment

This repository contains my solutions for the technical assessment for the Backend Developer Lead position at IFG.

The assessment consists of two independent projects demonstrating backend development, event-driven data processing, and business process orchestration using Java and the Quarkus ecosystem.

## Assessment Overview

### Question 1 — Kafka Data Processing with Quarkus

A backend service that consumes data from a Kafka topic, performs data transformation and processing, and publishes the processed data to Kafka or persists it to a database.

**Technology stack:**
- Java 17
- Quarkus
- Apache Kafka
- Database: See the project documentation

For implementation details, configuration, and testing instructions, see [`kafka-processing/README.md`](./kafka-processing/README.md).

### Question 2 — Marketplace Checkout with BPMN and Kogito

A marketplace checkout workflow modeled using BPMN and implemented with Kogito.

The workflow covers checkout request validation, payment-result evaluation, and order confirmation or rejection based on the payment outcome.

**Technology stack:**
- Java
- Quarkus
- Kogito
- BPMN

For the BPMN workflow, service descriptions, setup instructions, and testing procedures, see [`kogito-checkout/README.md`](./kogito-checkout/README.md).

## Repository Structure

```text
.
├── kafka-processing/
│   ├── README.md
│   └── ...
├── kogito-checkout/
│   ├── README.md
│   └── ...
└── README.md
```

## Running and Testing

Each project is maintained independently and has its own configuration, dependencies, and test procedures.

Please refer to the README in each project directory for the required prerequisites and instructions to build, run, and test the implementation.

## Submission

This repository is prepared as the submission for the IFG Backend Developer Lead technical assessment.

