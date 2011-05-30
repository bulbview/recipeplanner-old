package com.bulbview.recipeplanner.ui.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.presenter.ShoppingListPresenter;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Window;

@Component
public class ShoppingList extends ViewManager<ShoppingListPresenter> {

    @Autowired
    private GridLayout shoppingListGrid;
    @Autowired
    private Window     shoppingListWindow;

    public void addCategory(final String name) {
        final ShoppingListCategory shoppingListCategory = createCategory(name);
        shoppingListGrid.addComponent(shoppingListCategory.getTopLevelPanel());
    }

    private ShoppingListCategory createCategory(final String name) {
        final ShoppingListCategory shoppingListCategory = new ShoppingListCategory();
        shoppingListCategory.setCategoryName(name);
        return shoppingListCategory;
    }

    @Override
    public void init() {
        shoppingListWindow.setContent(shoppingListGrid);
    }
}
