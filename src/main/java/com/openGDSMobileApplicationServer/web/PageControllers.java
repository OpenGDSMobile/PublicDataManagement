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

    @RequestMapping(value="/management")
    String management() {return "management"; }

    @RequestMapping(value="/original")
    String original() {return "vis/original"; }

    @RequestMapping(value="/chart")
    String chart() {return "vis/chart"; }

    @RequestMapping(value="/map")
    String map() {return "vis/map"; }


}
