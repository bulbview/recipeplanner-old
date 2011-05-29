package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Window;

@Component
public class ShoppingListPresenter extends Presenter {

    @Autowired
    private Window rootWindow;
    @Autowired
    private Window shoppingListWindow;

    public void displayShoppingList() {
        rootWindow.addWindow(shoppingListWindow);
    }

    @Override
    public void init() {

    }

}
