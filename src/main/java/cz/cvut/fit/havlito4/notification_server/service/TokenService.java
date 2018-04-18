package cz.cvut.fit.havlito4.notification_server.service;

import cz.cvut.fit.havlito4.notification_server.controller.entity.TokenRequest;

public interface TokenService {

    void registerToken(TokenRequest body);

    void deleteToken(TokenRequest token);
}
