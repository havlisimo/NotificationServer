package cz.cvut.fit.havlito4.notification_server.controller;

import cz.cvut.fit.havlito4.notification_server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/test", produces = "application/json")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/{query}", method = RequestMethod.GET)
    public String test(@PathVariable(value = "query") String query)  {
        testService.test();
        return "Hey " + query;
    }
}
