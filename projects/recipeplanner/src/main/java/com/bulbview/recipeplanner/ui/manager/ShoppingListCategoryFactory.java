package com.bulbview.recipeplanner.ui.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShoppingListCategoryFactory {

    private final Logger                        logger;

    @Autowired
    private ObjectFactory<ShoppingListCategory> underlyingFactory;

    public ShoppingListCategoryFactory() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public ShoppingListCategory create(final String name) {
        final ShoppingListCategory shoppingListCategory = underlyingFactory.getObject();
        shoppingListCategory.setCategoryName(name);
        logger.debug("...{} created", shoppingListCategory);
        return shoppingListCategory;
    }
}
