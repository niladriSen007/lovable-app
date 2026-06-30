package com.niladri.lovable_app.service.session;

public interface ISessionService {
    public void generateNewSession(String email, String refreshToken);

    public boolean validateSession(String refreshToken);
}
