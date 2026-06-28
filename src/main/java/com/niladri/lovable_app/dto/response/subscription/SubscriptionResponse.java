package com.niladri.lovable_app.dto.response.subscription;

import com.niladri.lovable_app.dto.response.plan.PlanResponse;

import java.time.Instant;

public record SubscriptionResponse(
        PlanResponse plan,
        String status,
        Instant periodEnd,
        Long tokensUsedThisCycle
) {
}
