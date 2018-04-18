package cz.cvut.fit.havlito4.notification_server.service.impl;

import cz.cvut.fit.havlito4.notification_server.controller.entity.TokenRequest;
import cz.cvut.fit.havlito4.notification_server.entity.TokenEntity;
import cz.cvut.fit.havlito4.notification_server.hibernate.HibernateCriteriaCreator;
import cz.cvut.fit.havlito4.notification_server.service.TokenService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TokenServiceImpl implements TokenService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HibernateCriteriaCreator hibernateCriteriaCreator;

    @Autowired
    private RestOperations restOperations;

    @Override
    @Transactional
    public void registerToken(String oauthToken, TokenRequest body) {
        String userId = getUserId(oauthToken);
        TokenEntity entity = new TokenEntity();
        entity.setUserId(userId);
        entity.setToken(body.getToken());
        entity.setTokenType(body.getType());
        entityManager.persist(entity);
    }

    private String getUserId(String oauthToken) {

        String url = "https://rozvoj.fit.cvut.cz/evolution-dev/classification-dev/api/v1/public/user-info";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oauthToken);
        HttpEntity request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restOperations.exchange(url, HttpMethod.GET, request, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            //TODO get user id from response
            return "havlito4";
        } else {
            throw new HttpClientErrorException(response.getStatusCode());
        }

    }

    @Override
    @Transactional
    public void deleteToken(String oauthToken, TokenRequest token) {
        String userId = getUserId(oauthToken);
        final Criteria criteria = hibernateCriteriaCreator.createCriteria(TokenEntity.class, "token");
        criteria.add(Restrictions.eq("token.userId", userId));
        criteria.add(Restrictions.eq("token.token", token.getToken()));
        criteria.add(Restrictions.eq("token.tokenType", token.getType()));
        final List<TokenEntity> list = criteria.list();
        for (TokenEntity entity : list) {
            entityManager.remove(entity);
        }
    }
}
