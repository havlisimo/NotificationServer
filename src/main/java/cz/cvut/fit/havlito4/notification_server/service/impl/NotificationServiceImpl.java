package cz.cvut.fit.havlito4.notification_server.service.impl;

import cz.cvut.fit.havlito4.notification_server.controller.entity.FirebaseRequest;
import cz.cvut.fit.havlito4.notification_server.controller.entity.FirebaseRequestData;
import cz.cvut.fit.havlito4.notification_server.controller.entity.NotificationRequest;
import cz.cvut.fit.havlito4.notification_server.entity.TokenEntity;
import cz.cvut.fit.havlito4.notification_server.hibernate.HibernateCriteriaCreator;
import cz.cvut.fit.havlito4.notification_server.service.NotificationService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationServiceImpl implements NotificationService {

    @Value("${notification_server.firebase.serverkey}")
    private String serverKey;

    @Autowired
    private HibernateCriteriaCreator hibernateCriteriaCreator;

    @Autowired
    private RestOperations restOperations;

    @Override
    public void sendNotification(NotificationRequest notificationRequest) {
        List<TokenEntity> receiverTokens = getTokens(notificationRequest.getReceiverId());

        sendNotificationAndroid(notificationRequest, receiverTokens.stream().filter(tokenEntity -> tokenEntity.getTokenType().equals(TokenEntity.TYPE_ANDROID)).collect(Collectors.toList()));
    }

    private void sendNotificationAndroid(NotificationRequest notificationRequest, List<TokenEntity> tokenEntities) {
        for (TokenEntity entity : tokenEntities) {
            String url = "https://fcm.googleapis.com/fcm/send";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "key=" + serverKey);
            FirebaseRequest requestBody = new FirebaseRequest();
            requestBody.setTo(entity.getToken());
            requestBody.setData(new FirebaseRequestData(notificationRequest.getNotidficationId(), notificationRequest.getType()));
            HttpEntity request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restOperations.postForEntity(url, request, String.class);
        }
    }

    private List<TokenEntity> getTokens(String receiverId) {
        final Criteria criteria = hibernateCriteriaCreator.createCriteria(TokenEntity.class, "token");
        criteria.add(Restrictions.eq("token.userId", receiverId));
        return criteria.list();
    }
}
