package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.google.inject.Inject;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.And;
import com.vaadin.event.dd.acceptcriteria.ClientSideCriterion;
import com.vaadin.event.dd.acceptcriteria.SourceIs;
import com.vaadin.ui.AbstractSelect.AbstractSelectTargetDetails;
import com.vaadin.ui.AbstractSelect.AcceptItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.VerticalLayout;

public class DailyRecipeList extends VerticalLayout {

    private final Logger                    logger;
    private final BeanItemContainer<Recipe> dataSource;
    private final Table                     recipeList;
    private final Label                     dateLabel;

    @Inject
    public DailyRecipeList(final Table recipeList, final Label dateLabel) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.dateLabel = dateLabel;
        this.recipeList = recipeList;
        addComponent(dateLabel);
        addComponent(recipeList);
        dataSource = new BeanItemContainer<Recipe>(Recipe.class);
        configureRecipeList();
        setSpacing(true);

        logger.info("{} created", getClass().getName());
    }

    public void setDateHeader(final String date) {
        dateLabel.setCaption(date);
        logger.debug("date header set to {}", dateLabel.getCaption());
    }

    public void setDropSource(final MasterRecipeListView masterRecipeListView) {
        logger.debug("Initialising drop source");
        recipeList.setDropHandler(tableDropHandler(new SourceIs((Component) masterRecipeListView)));
    }

    private void configureRecipeList() {
        recipeList.setSelectable(true);
        recipeList.setDragMode(TableDragMode.ROW);
        // recipeList.setSizeFull();
        recipeList.setWidth("300px");
        recipeList.setPageLength(5);
        recipeList.setContainerDataSource(dataSource);
        recipeList.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
    }

    @SuppressWarnings("serial")
    private DropHandler tableDropHandler(final ClientSideCriterion acceptCriterion) {
        return new DropHandler() {

            public void drop(final DragAndDropEvent dropEvent) {
                // criteria verify that this is safe
                final DataBoundTransferable t = (DataBoundTransferable) dropEvent.getTransferable();
                // if( ! ( t.getSourceContainer() instanceof
                // Container.Hierarchical ) ) {
                // return;
                // }
                final Container sourceContainer = t.getSourceContainer();
                final Recipe recipe = (Recipe) t.getItemId();

                // find and convert the item(s) to move

                // move item(s) to the correct location in the table

                final AbstractSelectTargetDetails dropData = ( (AbstractSelectTargetDetails) dropEvent.getTargetDetails() );
                final Object targetItemId = dropData.getItemIdOver();
                if( targetItemId != null ) {
                    dataSource.addItemAfter(targetItemId, recipe);
                } else {
                    dataSource.addItem(recipe);
                }
            }

            public AcceptCriterion getAcceptCriterion() {
                return new And(acceptCriterion, AcceptItem.ALL);
            }
        };
    }

}
