package de.generosoft.mobileComputingBackend.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public Messages messages() {
        return new Messages(new Message[] {new Message("waberklassenraum", "ideeh", "wubidiwabern ist geil xDDDDD")});
    }

}
