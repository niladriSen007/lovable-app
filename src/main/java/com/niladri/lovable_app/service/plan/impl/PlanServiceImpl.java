package com.niladri.lovable_app.service.plan.impl;

import com.niladri.lovable_app.dto.response.plan.PlanResponse;
import com.niladri.lovable_app.service.plan.IPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanServiceImpl implements IPlanService {
    @Override
    public List<PlanResponse> getAllActivePlans() {
        return List.of();
    }
}
