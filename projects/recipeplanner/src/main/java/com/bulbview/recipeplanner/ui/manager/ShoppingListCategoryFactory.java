package com.bulbview.recipeplanner.ui.manager;

import org.springframework.stereotype.Component;

@Component
public class ShoppingListCategoryFactory {

    public ShoppingListCategory create(final String name) {
        final ShoppingListCategory shoppingListCategory = new ShoppingListCategory();
        shoppingListCategory.setCategoryName(name);
        return shoppingListCategory;
    }

}
