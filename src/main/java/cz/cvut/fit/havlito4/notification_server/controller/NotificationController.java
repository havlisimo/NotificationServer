package cz.cvut.fit.havlito4.notification_server.controller;

import cz.cvut.fit.havlito4.notification_server.controller.entity.NotificationRequest;
import cz.cvut.fit.havlito4.notification_server.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/notification", produces = "application/json")
public class NotificationController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity registerToken(@RequestBody NotificationRequest body)  {
        return new ResponseEntity<>("{}", HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteToken(@RequestBody NotificationRequest token)  {
        return new ResponseEntity<>("{}", HttpStatus.ACCEPTED);
    }
}