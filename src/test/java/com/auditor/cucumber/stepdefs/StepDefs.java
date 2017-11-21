package com.auditor.cucumber.stepdefs;

import com.auditor.AuditorApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = AuditorApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
