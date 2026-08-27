package com.neueda.leap.merchantportal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MerchantController {

    @Autowired
    private PayoutRepository payoutRepository;

    @GetMapping("/api/payouts/{payoutId}")
    public PayoutRequest getPayout(@PathVariable Long payoutId, Long userId) {
        PayoutRequest payout = payoutRepository.findById(payoutId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!canViewPayout(userId, payout)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return payout;
    }

    private boolean canViewPayout(Long userId, PayoutRequest payout) {
        // compare userId to payout owner/merchant
        return payout.getMerchantId().equals(userId);
    }
}
