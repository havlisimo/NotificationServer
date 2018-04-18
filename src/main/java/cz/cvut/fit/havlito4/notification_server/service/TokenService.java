package cz.cvut.fit.havlito4.notification_server.service;

import cz.cvut.fit.havlito4.notification_server.controller.entity.TokenRequest;

public interface TokenService {

    void registerToken(String oauthToken, TokenRequest body);

    void deleteToken(String oauthToken, TokenRequest token);
}
