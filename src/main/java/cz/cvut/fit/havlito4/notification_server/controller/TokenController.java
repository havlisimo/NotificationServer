package cz.cvut.fit.havlito4.notification_server.controller;

import cz.cvut.fit.havlito4.notification_server.controller.entity.TokenRequest;
import cz.cvut.fit.havlito4.notification_server.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/token", produces = "application/json")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity registerToken(@RequestBody TokenRequest body)  {
        tokenService.registerToken(body);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteToken(@RequestBody TokenRequest token)  {
        tokenService.deleteToken(token);
        return new ResponseEntity<>("{}", HttpStatus.ACCEPTED);
    }
}
