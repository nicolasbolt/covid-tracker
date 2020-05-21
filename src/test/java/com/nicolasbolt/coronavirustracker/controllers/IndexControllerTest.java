package com.nicolasbolt.coronavirustracker.controllers;

import com.nicolasbolt.coronavirustracker.services.GlobalCasesService;
import com.nicolasbolt.coronavirustracker.services.USACasesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class IndexControllerTest {

    @Mock
    GlobalCasesService globalCasesService;

    @Mock
    USACasesService usaCasesService;

    @Mock
    Model model;

    IndexController indexController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(globalCasesService, usaCasesService);
    }

    @Test
    void index() {
        String viewName = indexController.index(model);
        assertEquals(viewName, "index");
        verify(globalCasesService, times(1)).countNewCases();
        verify(usaCasesService, times(1)).countNewCases();
        verify(globalCasesService, times(1)).countReportedCases();
        verify(usaCasesService, times(1)).countReportedCases();
    }
}