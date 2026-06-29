package com.niladri.lovable_app.dto.request.checkout;

import jakarta.validation.constraints.NotNull;

public record CheckoutRequest(
        @NotNull Long planId
) {
}