package cz.cvut.fit.havlito4.notification_server.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/test", produces = "application/json")
public class TestController {

    @RequestMapping(value = "/{query}", method = RequestMethod.GET)
    public String test(@PathVariable(value = "query", required = false) String query)  {
       return "Hey " + query;
    }
}
