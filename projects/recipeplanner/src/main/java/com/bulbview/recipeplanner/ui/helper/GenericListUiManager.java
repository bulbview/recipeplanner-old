package com.bulbview.recipeplanner.ui.helper;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

public abstract class GenericListUiManager<T> extends UiManager {

    protected Panel              panel;
    private final Class<T>       entityClass;
    private BeanItemContainer<T> newDataSource;
    private Table                table;

    public GenericListUiManager(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void init() {
        panel.setCaption("Recipes");
        panel.setStyleName("bubble");
        panel.addComponent(table);
        newDataSource = new BeanItemContainer<T>(entityClass);
        table.setContainerDataSource(newDataSource);
        table.addStyleName("borderless");
    }

    public void setRecipePanel(final Panel panel) {
        this.panel = panel;
    }

    public void setTable(final Table table) {
        this.table = table;
    }

    protected void addListItem(final T entity) {
        newDataSource.addBean(entity);
    }

}