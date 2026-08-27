package com.neueda.leap.merchantportal;

import java.util.Optional;

public interface PayoutRepository {
    Optional<PayoutRequest> findByIdAndMerchantId(Long payoutId, Long merchantId, Long requestingUserId);
    void updateApprovalStatus(Long payoutId, String approvalStatus, Long approvedByUserId);
}
