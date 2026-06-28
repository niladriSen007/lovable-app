package com.niladri.lovable_app.service.subscription;

import com.niladri.lovable_app.dto.request.checkout.CheckoutRequest;
import com.niladri.lovable_app.dto.response.chceckout.CheckoutResponse;
import com.niladri.lovable_app.dto.response.portal.PortalResponse;
import com.niladri.lovable_app.dto.response.subscription.SubscriptionResponse;

public interface ISubscriptionService {
    SubscriptionResponse getCurrentSubscription(Long userId);

    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request, Long userId);

    PortalResponse openCustomerPortal(Long userId);
}
