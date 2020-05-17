package com.nicolasbolt.coronavirustracker.controllers;

import com.nicolasbolt.coronavirustracker.services.GlobalCasesService;
import com.nicolasbolt.coronavirustracker.services.USACasesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final GlobalCasesService globalCasesService;
    private final USACasesService usaCasesService;

    public IndexController(GlobalCasesService globalCasesService, USACasesService usaCasesService) {
        this.globalCasesService = globalCasesService;
        this.usaCasesService = usaCasesService;
    }

    @RequestMapping({"", "/"})
    public String index(Model model) {

        int totalReportedCases =
                globalCasesService.countReportedCases() +
                        usaCasesService.countReportedCases();
        int totalNewCases =
                globalCasesService.countNewCases() +
                        usaCasesService.countNewCases();

        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return "index";
    }
}
