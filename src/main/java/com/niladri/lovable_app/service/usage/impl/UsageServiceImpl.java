package com.niladri.lovable_app.service.usage.impl;

import com.niladri.lovable_app.dto.response.plan.PlanLimitsResponse;
import com.niladri.lovable_app.dto.response.usage.UsageTodayResponse;
import com.niladri.lovable_app.service.usage.IUsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsageServiceImpl implements IUsageService {
    @Override
    public UsageTodayResponse getTodayUsageOfUser(Long userId) {
        return null;
    }

    @Override
    public PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId) {
        return null;
    }
}
