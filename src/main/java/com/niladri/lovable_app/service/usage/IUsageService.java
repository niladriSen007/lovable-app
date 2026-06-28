package com.niladri.lovable_app.service.usage;

import com.niladri.lovable_app.dto.response.plan.PlanLimitsResponse;
import com.niladri.lovable_app.dto.response.usage.UsageTodayResponse;

public interface IUsageService {
    UsageTodayResponse getTodayUsageOfUser(Long userId);

    PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId);
}
