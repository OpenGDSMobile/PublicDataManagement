package com.openGDSMobileApplicationServer.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by intruder on 16. 5. 5.
 */

@Controller
@RequestMapping("/")
public class PageControllers {

    @RequestMapping
    String index() {
        return "index";
    }
}
