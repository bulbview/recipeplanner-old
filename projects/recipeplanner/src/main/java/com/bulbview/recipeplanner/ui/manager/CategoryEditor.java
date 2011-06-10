package com.bulbview.recipeplanner.ui.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.ui.presenter.CategoryTabsPresenter;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

@Component
public class CategoryEditor extends ViewManager<CategoryTabsPresenter> {

    @Autowired
    private Form                   categoryForm;

    @Autowired
    private Window                 categoryWindow;
    private BeanItem<ItemCategory> dataSource;

    @Autowired
    private Button                 saveCategoryButton;

    @SuppressWarnings("serial")
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
        categoryForm.getFooter().addComponent(createSaveCategoryButton());
        categoryWindow.addComponent(categoryForm);
    }

    public void setItemCategory(final ItemCategory itemCategory) {
        dataSource = new BeanItem<ItemCategory>(itemCategory);
        categoryForm.setItemDataSource(dataSource);
    }

    public void showErrorMessage(final String string) {
        // TODO Auto-generated method stub

    }

    @SuppressWarnings("serial")
    private Button createSaveCategoryButton() {
        saveCategoryButton.addListener(new ClickListener() {

            @Override
            public void buttonClick(final ClickEvent event) {
                categoryForm.commit();
                presenter.saveCategory(dataSource.getBean());

            }
        });
        saveCategoryButton.setClickShortcut(KeyCode.ENTER);
        return saveCategoryButton;
    }

}
