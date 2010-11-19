package com.bulbview.recipeplanner.ui;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.menu.MasterRecipeListContextMenu;
import com.google.inject.Inject;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class MasterRecipeList extends Table implements MasterRecipeListView {

    private BeanItemContainer<Recipe> recipeListDataSource;
    private final Logger              logger;

    @Inject
    public MasterRecipeList(final MasterRecipeListContextMenu masterListContextMenu) {
        this.logger = LoggerFactory.getLogger(getClass());
        setSelectable(true);
        setImmediate(true);
        setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
        final Property.ValueChangeListener recipeValueChangeListener = new Property.ValueChangeListener() {

            public void valueChange(final com.vaadin.data.Property.ValueChangeEvent event) {
                final Object id = getValue();
                logger.debug("*************Value change");
                // contactEditor.setItemDataSource(id == null ? null :
                // getItem(id));

            }
        };
        addListener(recipeValueChangeListener);
        addActionHandler(masterListContextMenu);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bulbview.ui.MasterRecipeListView#setRecipes(java.util.Collection)
     */
    @Override
    public void setRecipes(final Collection<Recipe> recipes) {
        recipeListDataSource = new BeanItemContainer<Recipe>(recipes);
        setContainerDataSource(recipeListDataSource);
        setVisibleColumns(new Object[] { "name" });
        requestRepaint();
    }

}
