package com.bulbview.recipeplanner.ui.manager;

import com.bulbview.recipeplanner.ui.presenter.Presenter;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

public abstract class GenericListView<T, P extends Presenter> extends ViewManager<P> {

    protected Table                genericListTable;
    protected BeanItemContainer<T> newDataSource;
    protected Panel                topLevelPanel;
    private final Class<T>         entityClass;

    public GenericListView(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void addListItem(final T entity) {
        logger.debug("Adding to list: {}", entity);
        newDataSource.addBean(entity);
    }

    public Table getGenericListTable() {
        return genericListTable;
    }

    public Panel getTopLevelPanel() {
        return topLevelPanel;
    }

    @Override
    public void init() {
        topLevelPanel.setStyleName("bubble");
        topLevelPanel.addComponent(genericListTable);
        newDataSource = new BeanItemContainer<T>(entityClass);
        genericListTable.setContainerDataSource(newDataSource);
        genericListTable.addStyleName("borderless");
        genericListTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
        genericListTable.setWidth("100%");
    }

    public void setGenericListTable(final Table genericListTable) {
        this.genericListTable = genericListTable;
    }

    public void setTopLevelPanel(final Panel panel) {
        this.topLevelPanel = panel;
    }

    protected void setVisibleColumns(final String... visibleColumns) {
        genericListTable.setVisibleColumns(visibleColumns);
    }

}