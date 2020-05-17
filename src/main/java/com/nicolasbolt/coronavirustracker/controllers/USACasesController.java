package com.nicolasbolt.coronavirustracker.controllers;

import com.nicolasbolt.coronavirustracker.models.USALocationStats;
import com.nicolasbolt.coronavirustracker.services.USACasesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class USACasesController {

    private final USACasesService usaCasesService;

    public USACasesController(USACasesService usaCasesService) {
        this.usaCasesService = usaCasesService;
    }

    @RequestMapping({"usa-cases/", "usa-cases"})
    public String usaCases(Model model) {

        List<USALocationStats> allStats = usaCasesService.getAllStats();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", usaCasesService.countReportedCases());
        model.addAttribute("totalNewCases", usaCasesService.countNewCases());

        return "usa_cases";
    }
}
