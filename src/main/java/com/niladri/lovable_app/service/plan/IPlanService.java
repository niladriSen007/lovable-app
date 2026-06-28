package com.niladri.lovable_app.service.plan;

import com.niladri.lovable_app.dto.response.plan.PlanResponse;

import java.util.List;

public interface IPlanService {
    List<PlanResponse> getAllActivePlans();
}