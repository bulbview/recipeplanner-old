package com.bulbview.recipeplanner.ui.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.ui.presenter.Presenter;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
// TODO determine if presenter required?
public class ShoppingListCategory extends GenericListView<Item, Presenter> {

    private String categoryName;

    public ShoppingListCategory() {
        super(Item.class);
    }

    @Override
    public void init() {
        super.init();
        setVisibleColumns("name");
        topLevelPanel.setCaption(categoryName);
    }

    public void setCategoryName(final String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    @Autowired
    public void setGenericListTable(final Table genericListTable) {
        super.setGenericListTable(genericListTable);
    }

    //
    // @Override
    // @Autowired
    // public void setPresenter(final CategoryListPresenter presenter) {
    // super.setPresenter(presenter);
    // // presenter.setView(this);
    // }

    @Autowired
    @Override
    public void setTopLevelPanel(final Panel panel) {
        super.setTopLevelPanel(panel);
    }

    @Override
    public String toString() {
        return String.format("ShoppingListCategory [categoryName=%s]", categoryName);
    }
}
