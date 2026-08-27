package com.neueda.leap.merchantportal;

public interface PayoutStatusUpdater {
    // Implementations must only transition payouts that are currently APPROVED (see PayoutRequest.markPaid()).
    void markSettled(Long payoutId, PayoutStatus status);
}
