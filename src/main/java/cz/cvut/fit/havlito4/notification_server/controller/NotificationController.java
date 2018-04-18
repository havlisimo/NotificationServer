package cz.cvut.fit.havlito4.notification_server.controller;

import cz.cvut.fit.havlito4.notification_server.controller.entity.NotificationRequest;
import cz.cvut.fit.havlito4.notification_server.service.NotificationService;
import cz.cvut.fit.havlito4.notification_server.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/notification", produces = "application/json")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity registerToken(@RequestBody NotificationRequest body)  {
        notificationService.sendNotification(body);

        return new ResponseEntity<>("{}", HttpStatus.ACCEPTED);
    }
}
