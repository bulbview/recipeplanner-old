package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;

public class IngredientFieldFactory implements TableFieldFactory {

    private final Logger logger;

    public IngredientFieldFactory() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public Field createField(final Container container,
                             final Object itemId,
                             final Object propertyId,
                             final Component uiContext) {
        final Property itemProperty = container.getItem(itemId).getItemProperty(propertyId);
        logger.debug("property {}", itemProperty);
        return new com.vaadin.ui.TextField(itemProperty);
    }

}
