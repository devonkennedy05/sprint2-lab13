package com.neueda.leap.merchantportal;

public class PayoutApprovalService {

    private PayoutRepository payoutRepository;

    public PayoutApprovalService(PayoutRepository payoutRepository) {
        this.payoutRepository = payoutRepository;
    }

    public void approve(Long payoutId, Long approvingUserId) {
        if (payoutId == null || payoutId <= 0) {
            throw new IllegalArgumentException("Invalid payout ID");
        }
        if (approvingUserId == null || approvingUserId <= 0) {
            throw new IllegalArgumentException("Invalid approving user ID");
        }
        
        if (!authorizationService.hasRole(approvingUserId, "PAYOUT_APPROVER")) {
            throw new UnauthorizedException("User not authorized to approve payouts");
        }
        
        PayoutRequest payout = payoutRepository.findById(payoutId)
                .orElseThrow(() -> new PayoutNotFoundException("Payout not found"));

        payout.setApprovalStatus("APPROVED");
        payout.setApprovedByUserId(approvingUserId);
        payoutRepository.save(payout);

        // Log successful approval with timestamp, amount, and user
        auditLogger.log("PAYOUT_APPROVED", payoutId, approvingUserId, 
                        "Amount: " + payout.getAmount() + ", Merchant: " + payout.getMerchantId());
    }
}
