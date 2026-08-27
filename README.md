# Cyber Challenge

## Sprint Planning

### Goal: Find and Fix All Vulnerabilities

#### Devon
- BankTransferClient.java
- BankTransferException.java
- BatchPayoutJob.java
- MerchantController.java
- PaymentStatusEvent.java

#### Brian
- PayoutRepository.java
- PayoutApprovalService.java
- PayoutRequest.java
- PayoutStatusUpdater.java
- WebhookController.java

## Diagnosis and Fix
Include: 
- Line Number(s)
- The OWASP Top 10 Category
- The Problem Present
- Real-World Risk in English
- What the Fix Was

### BatchPayoutJob.java
- Lines 22-26
- A09:2025 – Security Logging and Monitoring Failures.
- Warning log when a transfer fails is not audit-friendly enough, along with counting the failure as "Paid" status.
- Failed bank transfers could be recorded as successful payouts. May create issues detecting missing payments or risks with fraud, compliance, and accounting.
- Logging a descriptive, structured failure event and marking the payout status as 'FAILED' instead of 'PAID'.
