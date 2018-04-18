package cz.cvut.fit.havlito4.notification_server.service;

import cz.cvut.fit.havlito4.notification_server.controller.entity.NotificationRequest;

public interface NotificationService {

    void sendNotification(NotificationRequest notificationRequest);

}
