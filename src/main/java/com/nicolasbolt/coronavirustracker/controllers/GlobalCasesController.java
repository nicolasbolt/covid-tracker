package com.nicolasbolt.coronavirustracker.controllers;

import com.nicolasbolt.coronavirustracker.models.GlobalLocationStats;
import com.nicolasbolt.coronavirustracker.services.GlobalCasesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class GlobalCasesController {

    private GlobalCasesService globalCasesService;

    public GlobalCasesController(GlobalCasesService globalCasesService) {
        this.globalCasesService = globalCasesService;
    }

    @RequestMapping({"global-cases/", "global-cases"})
    public String globalCases(Model model) {
        List<GlobalLocationStats> allStats = globalCasesService.getAllStats();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", globalCasesService.countReportedCases());
        model.addAttribute("totalNewCases", globalCasesService.countNewCases());
        return "global_cases";
    }
}
