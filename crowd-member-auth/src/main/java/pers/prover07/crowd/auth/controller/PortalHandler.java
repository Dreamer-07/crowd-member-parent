package pers.prover07.crowd.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author by Prover07
 * @classname PortalHandler
 * @description TODO
 * @date 2022/2/25 20:54
 */
@Controller
public class PortalHandler {

    @GetMapping("/")
    public String accessPortal() {
        return "portal";
    }

}
