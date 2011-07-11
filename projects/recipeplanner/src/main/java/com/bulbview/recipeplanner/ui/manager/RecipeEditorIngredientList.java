package com.bulbview.recipeplanner.ui.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.ui.RecipeEditor;
import com.bulbview.recipeplanner.ui.presenter.RecipePresenter;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;

@Component
public class RecipeEditorIngredientList extends GenericListView<Ingredient, RecipePresenter> {
    
    public RecipeEditorIngredientList() {
        super(Ingredient.class);
    }
    
    public void addIngredient(final Ingredient ingredient) {
        addListItem(ingredient);
    }
    
    public Collection<Ingredient> getIngredientsField() {
        return newDataSource.getItemIds();
        
    }
    
    @Override
    public void init() {
        super.init();
        topLevelPanel.setCaption("Ingredients");
        genericListTable.setDragMode(TableDragMode.ROW);
        genericListTable.setDropHandler(tableDropHandler());
        setVisibleColumns("name");
    }
    
    @Autowired
    @Override
    public void setGenericListTable(final Table genericListTable) {
        super.setGenericListTable(genericListTable);
    }
    
    public void setIngredients(final Collection<Ingredient> ingredients) {
        for (final Ingredient ingredient : ingredients) {
            addIngredient(ingredient);
        }
    }
    
    @Autowired
    public void setTopLevelPanel(final RecipeEditor recipeEditor) {
        super.setTopLevelPanel(recipeEditor.getIngredientsPanel());
    }
    
    @SuppressWarnings("serial")
    private DropHandler tableDropHandler() {
        return new DropHandler() {
            
            @Override
            public void drop(final DragAndDropEvent dropEvent) {
                // criteria verify that this is safe
                final DataBoundTransferable t = (DataBoundTransferable) dropEvent.getTransferable();
                final Item item = (Item) t.getItemId();
                presenter.dragAndDrop(item);
            }
            
            @Override
            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }
        };
    }
    
}
