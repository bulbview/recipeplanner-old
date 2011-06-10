package com.bulbview.recipeplanner.ui.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Entity;
import com.bulbview.recipeplanner.ui.presenter.CategoryListPresenter;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.TextField;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CategorisedItemList extends GenericListUiManager<Entity, CategoryListPresenter> {

    private String categoryName;

    public CategorisedItemList() {
        super(Entity.class);
    }

    @Override
    public void init() {
        super.init();
        topLevelPanel.addComponent(newItemTextFieldPanel());
        presenter.setCategory(categoryName);
        genericListTable.setDragMode(TableDragMode.ROW);
    }

    public void setCategoryName(final String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    @Autowired
    public void setGenericListTable(final Table genericListTable) {
        super.setGenericListTable(genericListTable);
    }

    @Override
    @Autowired
    public void setPresenter(final CategoryListPresenter presenter) {
        super.setPresenter(presenter);
        presenter.setView(this);
    }

    @Autowired
    @Override
    public void setTopLevelPanel(final Panel daySchedulePanel) {
        super.setTopLevelPanel(daySchedulePanel);
    }

    public void showErrorMessage(final String message) {
        // TODO Auto-generated method stub

    }

    @SuppressWarnings("serial")
    private com.vaadin.ui.Component newItemTextFieldPanel() {
        final HorizontalLayout horizontalLayout = new HorizontalLayout();
        final TextField itemNameTextField = new TextField();
        itemNameTextField.setInputPrompt("<Enter new item name>");
        horizontalLayout.addComponent(itemNameTextField);
        final Button button = new Button("+");
        button.addListener(new ClickListener() {

            @Override
            public void buttonClick(final ClickEvent event) {
                presenter.addItem(itemNameTextField.getValue().toString());
            }
        });
        button.setClickShortcut(KeyCode.ENTER);
        horizontalLayout.addComponent(button);
        return horizontalLayout;
    }
}
