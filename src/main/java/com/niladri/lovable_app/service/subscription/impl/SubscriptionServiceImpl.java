package com.niladri.lovable_app.service.subscription.impl;

import com.niladri.lovable_app.dto.request.checkout.CheckoutRequest;
import com.niladri.lovable_app.dto.response.chceckout.CheckoutResponse;
import com.niladri.lovable_app.dto.response.file.FileContentResponse;
import com.niladri.lovable_app.dto.response.file.FileNode;
import com.niladri.lovable_app.dto.response.portal.PortalResponse;
import com.niladri.lovable_app.dto.response.subscription.SubscriptionResponse;
import com.niladri.lovable_app.service.file.IFileService;
import com.niladri.lovable_app.service.subscription.ISubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements ISubscriptionService {

    @Override
    public SubscriptionResponse getCurrentSubscription(Long userId) {
        return null;
    }

    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request, Long userId) {
        return null;
    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {
        return null;
    }
}
