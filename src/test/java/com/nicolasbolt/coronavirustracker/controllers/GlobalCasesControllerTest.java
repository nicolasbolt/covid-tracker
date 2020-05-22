package com.nicolasbolt.coronavirustracker.controllers;

import com.nicolasbolt.coronavirustracker.services.GlobalCasesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class GlobalCasesControllerTest {

    GlobalCasesController globalCasesController;

    @Mock
    Model model;

    @Mock
    GlobalCasesService globalCasesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        globalCasesController = new GlobalCasesController(globalCasesService);
    }

    @Test
    void globalCases() {
        String viewName = globalCasesController.globalCases(model);
        assertEquals(viewName, "global_cases");
        verify(globalCasesService, times(1)).getAllStats();
        verify(globalCasesService, times(1)).countReportedCases();
        verify(globalCasesService, times(1)).countNewCases();
    }
}