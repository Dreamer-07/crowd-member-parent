package pers.prover07.crowd.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pers.prover07.crowd.auth.service.PortalService;
import pers.prover07.crowd.vo.PortalTypeVo;

import java.util.List;

/**
 * @author by Prover07
 * @classname PortalHandler
 * @description TODO
 * @date 2022/2/25 20:54
 */
@Controller
public class PortalHandler {

    @Autowired
    private PortalService portalService;

    @GetMapping("/")
    public String accessPortal(Model model) {
        List<PortalTypeVo> portalTypeVoList = portalService.getPortalTypeVoList();
        model.addAttribute("typeList", portalTypeVoList);
        return "portal";
    }

}
