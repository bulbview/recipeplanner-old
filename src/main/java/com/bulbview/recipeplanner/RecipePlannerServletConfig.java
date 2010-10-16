package com.bulbview.recipeplanner;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

public class RecipePlannerServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {

        final ServletModule module = new RecipePlannerServletModule();

        final Injector injector = Guice.createInjector(module);

        return injector;
    }
}