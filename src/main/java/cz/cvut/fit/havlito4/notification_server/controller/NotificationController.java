package cz.cvut.fit.havlito4.notification_server.controller;

import cz.cvut.fit.havlito4.notification_server.controller.entity.NotificationRequest;
import cz.cvut.fit.havlito4.notification_server.service.NotificationService;
import cz.cvut.fit.havlito4.notification_server.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
@RequestMapping(value = "/notification", produces = "application/json")
public class NotificationController {

    @Value("${notification_server.appserver.secret}")
    private String classificationAuthCode;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity sendNotification(@RequestHeader("Authorization") String authorization, @RequestBody NotificationRequest body)  {

        if (!("Bearer " + classificationAuthCode).equals(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            notificationService.sendNotification(body);
        } catch (HttpStatusCodeException ex) {
            return new ResponseEntity(HttpStatus.valueOf(ex.getStatusCode().value()));
        }
        return new ResponseEntity<>("", HttpStatus.ACCEPTED);
    }


    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteNotification(@RequestHeader("Authorization") String authorization, @RequestBody NotificationRequest body)  {

        if (!("Bearer " + classificationAuthCode).equals(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        body.setType("Hidden");
        try {
            notificationService.sendNotification(body);
        } catch (HttpStatusCodeException ex) {
            return new ResponseEntity(HttpStatus.valueOf(ex.getStatusCode().value()));
        }
        return new ResponseEntity<>("", HttpStatus.ACCEPTED);
    }
}
