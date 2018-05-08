package cz.cvut.fit.havlito4.notification_server.controller;

import cz.cvut.fit.havlito4.notification_server.controller.entity.TokenRequest;
import cz.cvut.fit.havlito4.notification_server.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
@RequestMapping(value = "/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity registerToken(@RequestHeader("Authorization") String oauthToken, @RequestBody TokenRequest body)  {
        if (oauthToken==null || !oauthToken.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        oauthToken = oauthToken.replace("Bearer ", "");
        try {
            tokenService.registerToken(oauthToken, body);
        } catch (HttpStatusCodeException ex) {
            return new ResponseEntity(HttpStatus.valueOf(ex.getStatusCode().value()));
        }
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteToken(@RequestHeader("Authorization") String oauthToken, @RequestBody TokenRequest token)  {
        if (oauthToken==null || !oauthToken.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        oauthToken = oauthToken.replace("Bearer ", "");
        try {
            tokenService.deleteToken(oauthToken, token);
        } catch (HttpStatusCodeException ex) {
            return new ResponseEntity(HttpStatus.valueOf(ex.getStatusCode().value()));
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
