package com.bulbview.recipeplanner.ui.helper;

import com.bulbview.recipeplanner.ui.Presenter;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

public abstract class GenericListUiManager<T, P extends Presenter> extends ViewManager<P> {

    protected Panel              topLevelPanel;
    private final Class<T>       entityClass;
    private Table                genericListTable;
    private BeanItemContainer<T> newDataSource;

    public GenericListUiManager(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void addListItem(final T entity) {
        newDataSource.addBean(entity);
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
    }

    public void setGenericListTable(final Table genericListTable) {
        this.genericListTable = genericListTable;
    }

    public void setTopLevelPanel(final Panel panel) {
        this.topLevelPanel = panel;
    }

}