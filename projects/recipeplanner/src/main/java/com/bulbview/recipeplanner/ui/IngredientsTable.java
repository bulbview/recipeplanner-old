package com.bulbview.recipeplanner.ui;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.google.inject.Inject;
import com.vaadin.data.Container;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.And;
import com.vaadin.event.dd.acceptcriteria.ClientSideCriterion;
import com.vaadin.event.dd.acceptcriteria.SourceIs;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class IngredientsTable extends Table {

    public final RecipeFieldFactory recipeFieldFactory;
    private final Logger            logger;

    @Inject
    public IngredientsTable(final RecipeFieldFactory recipeFieldFactory,
                            final IngredientFieldFactory ingredientFieldFactory,
                            final IngredientsAccordion ingredientsEditor) {
        this.recipeFieldFactory = recipeFieldFactory;
        recipeFieldFactory.set(getContainerDataSource());
        this.logger = LoggerFactory.getLogger(getClass());
        setEditable(true);
        setImmediate(true);
        setNullSelectionAllowed(true);
        setWriteThrough(false);
        createContainerProperties();
        // setTableFieldFactory(ingredientFieldFactory);
        this.setDragMode(TableDragMode.ROW);
        final Collection<Table> ingredientCategoryTables = ingredientsEditor.getIngredientCategoryTables();
        setDropSource(ingredientCategoryTables.toArray(new Table[ingredientCategoryTables.size()]));
    }

    public void addNewIngredient(final Ingredient bean) {
        final HashSet<Ingredient> value2 = (HashSet<Ingredient>) getValue();
        value2.add(bean);
    }

    public void setRecipe(final Recipe recipe) {
        for ( final Ingredient ingredient : recipe.getIngredients().keySet() ) {
            addItem(new Object[] { ingredient, "0" }, ingredient.getName());
        }
        // ingredientsContainer.addAll(recipe.getIngredients());
    }

    private void createContainerProperties() {
        addContainerProperty("Ingredient", String.class, null);
        addContainerProperty("Amount", String.class, null);
    }

    private void setDropSource(final Table... ingredientCategoryLists) {
        logger.debug("Initialising drop source");
        setDropHandler(tableDropHandler(new SourceIs(ingredientCategoryLists)));
    }

    @SuppressWarnings("serial")
    private DropHandler tableDropHandler(final ClientSideCriterion acceptCriterion) {
        return new DropHandler() {

            public void drop(final DragAndDropEvent dropEvent) {
                // criteria verify that this is safe
                final DataBoundTransferable t = (DataBoundTransferable) dropEvent.getTransferable();
                final Container sourceContainer = t.getSourceContainer();
                final Ingredient ingredient = (Ingredient) t.getItemId();
                logger.info("Adding ingredient: {}", ingredient);
                final AbstractSelectTargetDetails dropData = ( (AbstractSelectTargetDetails) dropEvent.getTargetDetails() );
                final Object targetItemId = dropData.getItemIdOver();
                if( targetItemId != null ) {
                    addItemAfter(targetItemId, new Object[] { ingredient, "0" });
                } else {
                    addItem(ingredient);
                }
            }

            @Override
            public AcceptCriterion getAcceptCriterion() {
                return new And(acceptCriterion, AcceptItem.ALL);
            }
        };
    }

}
