package com.neueda.leap.merchantportal;

public class PayoutRequest {
    private Long id;
    private Long merchantId;
    private Long requestedByUserId;
    private String approvalStatus; // PENDING, APPROVED, REJECTED
    private Long approvedByUserId;
    private double amount;

    public PayoutRequest(Long id, Long merchantId, Long requestedByUserId, double amount) {
        this.id = id;
        this.merchantId = merchantId;
        this.requestedByUserId = requestedByUserId;
        this.amount = amount;
        this.approvalStatus = "PENDING";
    }

    public Long getId() { return id; }
    public Long getMerchantId() { return merchantId; }
    public Long getRequestedByUserId() { return requestedByUserId; }
    public String getApprovalStatus() { return approvalStatus; }
    public Long getApprovedByUserId() { return approvedByUserId; }
    public double getAmount() { return amount; }

    // Enforces the PENDING -> APPROVED transition and blocks self-approval instead of trusting callers.
    public void approve(Long approvingUserId) {
        if (!"PENDING".equals(approvalStatus)) {
            throw new IllegalStateException("Only pending payouts can be approved");
        }
        if (approvingUserId == null || approvingUserId.equals(requestedByUserId)) {
            throw new IllegalArgumentException("A payout cannot be approved by its requester");
        }
        this.approvalStatus = "APPROVED";
        this.approvedByUserId = approvingUserId;
    }

    public void reject(Long rejectingUserId) {
        if (!"PENDING".equals(approvalStatus)) {
            throw new IllegalStateException("Only pending payouts can be rejected");
        }
        this.approvalStatus = "REJECTED";
        this.approvedByUserId = rejectingUserId;
    }

    // Only an already-approved payout may be marked paid; the approver identity is never overwritten.
    public void markPaid() {
        if (!"APPROVED".equals(approvalStatus)) {
            throw new IllegalStateException("Only approved payouts can be marked paid");
        }
        this.approvalStatus = "PAID";
    }
}
