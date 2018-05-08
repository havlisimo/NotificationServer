package cz.cvut.fit.havlito4.notification_server.service.impl;

import cz.cvut.fit.havlito4.notification_server.controller.entity.TokenRequest;
import cz.cvut.fit.havlito4.notification_server.entity.TokenEntity;
import cz.cvut.fit.havlito4.notification_server.hibernate.HibernateCriteriaCreator;
import cz.cvut.fit.havlito4.notification_server.service.TokenService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

public class TokenServiceImpl implements TokenService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HibernateCriteriaCreator hibernateCriteriaCreator;

    @Autowired
    private RestOperations restOperations;

    @Resource
    private Set<String> tokenTypes;

    @Value("${notification_server.appserver.url}")
    private String appServerUrl;

    @Override
    @Transactional
    public void registerToken(String oauthToken, TokenRequest body) {
        String token = body.getToken();
        String type = body.getType();

        if (token ==null || type == null || token.isEmpty() || !tokenTypes.contains(type)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        String userId = getUserId(oauthToken);

        final Criteria criteria = hibernateCriteriaCreator.createCriteria(TokenEntity.class, "token");
        criteria.add(Restrictions.eq("token.token", token));
        criteria.add(Restrictions.eq("token.tokenType", type));
        final List<TokenEntity> list = criteria.list();
        for (TokenEntity entity : list) {
            entityManager.remove(entity);
        }

        TokenEntity entity = new TokenEntity();
        entity.setUserId(userId);
        entity.setToken(token);
        entity.setTokenType(type);
        entityManager.persist(entity);
    }

    private String getUserId(String oauthToken) {

        String url = appServerUrl + "public/user-info";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oauthToken);
        HttpEntity request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restOperations.exchange(url, HttpMethod.GET, request, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            //TODO get user id from response
            return "TODO";
        } else {
            throw new HttpClientErrorException(response.getStatusCode());
        }

    }

    @Override
    @Transactional
    public void deleteToken(String oauthToken, TokenRequest body) {
        String token = body.getToken();
        String type = body.getType();

        if (token ==null || type == null || token.isEmpty() || !tokenTypes.contains(type)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        String userId = getUserId(oauthToken);
        final Criteria criteria = hibernateCriteriaCreator.createCriteria(TokenEntity.class, "token");
        criteria.add(Restrictions.eq("token.userId", userId));
        criteria.add(Restrictions.eq("token.token", token));
        criteria.add(Restrictions.eq("token.tokenType", type));
        final List<TokenEntity> list = criteria.list();
        if (list.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        for (TokenEntity entity : list) {
            entityManager.remove(entity);
        }
    }
}
