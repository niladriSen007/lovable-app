package com.niladri.lovable_app.controller;


import com.niladri.lovable_app.dto.response.ApiResponse;
import com.niladri.lovable_app.dto.response.plan.PlanLimitsResponse;
import com.niladri.lovable_app.dto.response.usage.UsageTodayResponse;
import com.niladri.lovable_app.service.usage.IUsageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usage")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsageController {

    IUsageService usageService;

    @GetMapping("/today")
    public ResponseEntity<ApiResponse<UsageTodayResponse>> getTodayUsage() {
        Long userId = 1L;
        return ResponseEntity.ok(
                ApiResponse.success(usageService.getTodayUsageOfUser(userId), HttpStatus.OK.value())
        );
    }

    @GetMapping("/limits")
    public ResponseEntity<ApiResponse<PlanLimitsResponse>> getPlanLimits() {
        Long userId = 1L;
        return ResponseEntity.ok(
                ApiResponse.success(usageService.getCurrentSubscriptionLimitsOfUser(userId), HttpStatus.OK.value())
        );
    }
}
