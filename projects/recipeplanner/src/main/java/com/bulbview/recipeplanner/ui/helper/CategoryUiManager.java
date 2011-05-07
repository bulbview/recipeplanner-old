package com.bulbview.recipeplanner.ui.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

@Component
public class CategoryUiManager extends UiManager {

    @Autowired
    private Form   categoryForm;

    @Autowired
    private Window categoryWindow;
    @Autowired
    private Button saveCategoryButton;

    @Override
    public void init() {
        categoryWindow.setWidth("250px");
        categoryForm.setFormFieldFactory(new FormFieldFactory() {

            @Override
            public Field createField(final Item item,
                                     final Object propertyId,
                                     final com.vaadin.ui.Component uiContext) {
                final TextField textField = new TextField(propertyId.toString());
                textField.setNullRepresentation("");
                textField.setInputPrompt("<Enter category name>");
                return textField;
            }
        });
        categoryForm.getFooter().addComponent(saveCategoryButton);
        categoryWindow.addComponent(categoryForm);
    }

    public void setItemCategory(final ItemCategory itemCategory) {
        categoryForm.setItemDataSource(new BeanItem<ItemCategory>(itemCategory));
    }

}
