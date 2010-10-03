package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.vaadin.ui.Table;

public class DailyRecipeList extends Table {

    private Object       headerName;
    private final Logger logger;

    @Inject public DailyRecipeList() {
        logger = LoggerFactory.getLogger(getClass());
        configureAsDynamicallyUpdated();
        setSizeFull();
        createInitialHeader();
        logger.info("{} created", getClass().getName());
    }

    public void setDateHeader(final String date) {
        setColumnHeader(headerName, date);
        logger.debug("date header set to {}", getColumnHeader("date"));
    }

    private void configureAsDynamicallyUpdated() {
        setImmediate(true);
        setSelectable(true);
        enableContentRefreshing(true);
    }

    private boolean createInitialHeader() {
        headerName = "Date";
        return addContainerProperty(headerName, String.class, null);
    }

}
