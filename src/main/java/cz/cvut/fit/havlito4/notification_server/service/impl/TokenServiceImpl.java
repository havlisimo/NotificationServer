package cz.cvut.fit.havlito4.notification_server.service.impl;

import cz.cvut.fit.havlito4.notification_server.controller.entity.TokenRequest;
import cz.cvut.fit.havlito4.notification_server.entity.TestEntity;
import cz.cvut.fit.havlito4.notification_server.entity.TokenEntity;
import cz.cvut.fit.havlito4.notification_server.hibernate.HibernateCriteriaCreator;
import cz.cvut.fit.havlito4.notification_server.service.TokenService;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TokenServiceImpl implements TokenService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HibernateCriteriaCreator hibernateCriteriaCreator;


    @Override
    @Transactional
    public void registerToken(TokenRequest body) {
        TokenEntity entity  = new TokenEntity();
        entity.setUserId("havlito4");
        entity.setToken(body.getToken());
        entity.setTokenType(body.getType());
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void deleteToken(TokenRequest token) {
        final Criteria criteria = hibernateCriteriaCreator.createCriteria(TokenEntity.class, "token");
        criteria.add(Restrictions.eq("token.token", token.getToken()));
        criteria.add(Restrictions.eq("token.tokenType", token.getType()));
        final List<TokenEntity> list = criteria.list();
        for (TokenEntity entity : list) {
            entityManager.remove(entity);
        }
    }
}
