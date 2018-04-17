package cz.cvut.fit.havlito4.notification_server.service.impl;

import cz.cvut.fit.havlito4.notification_server.entity.TestEntity;
import cz.cvut.fit.havlito4.notification_server.hibernate.HibernateCriteriaCreator;
import cz.cvut.fit.havlito4.notification_server.service.TestService;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TestServiceImpl implements TestService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HibernateCriteriaCreator hibernateCriteriaCreator;

    @Override
    public void test() {
        final TestEntity testEntity = entityManager.find(TestEntity.class, 1L);
        final Criteria criteria = hibernateCriteriaCreator.createCriteria(TestEntity.class, "test");
//        criteria.createAlias("test.differentTable", "differentTable", JoinType.LEFT_OUTER_JOIN);
//        criteria.add(Restrictions.eq("test.testAttribure", "AHOJ"));
        criteria.add(Restrictions.ilike("test.testAttribute", "a", MatchMode.ANYWHERE));
        final List<TestEntity> list = criteria.list();

        for (TestEntity entity : list) {
            System.out.println(entity);
        }

        System.out.println(list.size());

        System.out.println(testEntity);
    }
}
