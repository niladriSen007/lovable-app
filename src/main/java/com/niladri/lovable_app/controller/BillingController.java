package com.niladri.lovable_app.controller;

import com.niladri.lovable_app.dto.request.checkout.CheckoutRequest;
import com.niladri.lovable_app.dto.response.ApiResponse;
import com.niladri.lovable_app.dto.response.chceckout.CheckoutResponse;
import com.niladri.lovable_app.dto.response.plan.PlanResponse;
import com.niladri.lovable_app.dto.response.portal.PortalResponse;
import com.niladri.lovable_app.dto.response.subscription.SubscriptionResponse;
import com.niladri.lovable_app.service.plan.IPlanService;
import com.niladri.lovable_app.service.subscription.ISubscriptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BillingController {

    IPlanService planService;
    ISubscriptionService subscriptionService;

    @GetMapping("/api/plans")
    public ResponseEntity<ApiResponse<List<PlanResponse>>> getAllPlans() {
        return ResponseEntity.ok(ApiResponse.success(planService.getAllActivePlans(), HttpStatus.OK.value()));
    }

    @GetMapping("/api/me/subscription")
    public ResponseEntity<ApiResponse<SubscriptionResponse>> getMySubscription() {
        Long userId = 1L;
        return ResponseEntity.ok(ApiResponse.success(subscriptionService.getCurrentSubscription(userId), HttpStatus.OK.value()));
    }

    @PostMapping("/api/stripe/checkout")
    public ResponseEntity<ApiResponse<CheckoutResponse>> createCheckoutResponse(
            @RequestBody CheckoutRequest request
    ) {
        Long userId = 1L;
        return ResponseEntity.ok(ApiResponse.success(subscriptionService.createCheckoutSessionUrl(request, userId), HttpStatus.OK.value()));
    }

    @PostMapping("/api/stripe/portal")
    public ResponseEntity<ApiResponse<PortalResponse>> openCustomerPortal() {
        Long userId = 1L;
        return ResponseEntity.ok(ApiResponse.success(subscriptionService.openCustomerPortal(userId), HttpStatus.OK.value()));
    }

}
