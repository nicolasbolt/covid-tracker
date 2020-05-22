package com.nicolasbolt.coronavirustracker.controllers;

import com.nicolasbolt.coronavirustracker.services.USACasesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class USACasesControllerTest {

    USACasesController usaCasesController;

    @Mock
    Model model;

    @Mock
    USACasesService usaCasesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        usaCasesController = new USACasesController(usaCasesService);
    }

    @Test
    void usaCases() {
        String viewName = usaCasesController.usaCases(model);
        assertEquals(viewName, "usa_cases");
        verify(usaCasesService, times(1)).getAllStats();
        verify(usaCasesService, times(1)).countNewCases();
        verify(usaCasesService, times(1)).countReportedCases();
    }
}