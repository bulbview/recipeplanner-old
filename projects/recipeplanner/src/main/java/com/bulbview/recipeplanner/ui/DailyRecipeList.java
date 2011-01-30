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
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

public class DailyRecipeList extends Table {

    private Object                          headerName;
    private final Logger                    logger;
    private final BeanItemContainer<Recipe> dataSource;

    @Inject
    public DailyRecipeList() {
        logger = LoggerFactory.getLogger(getClass());
        configureAsDynamicallyUpdated();
        setSizeFull();
        createInitialHeader();
        setDragMode(TableDragMode.ROW);
        dataSource = new BeanItemContainer<Recipe>(Recipe.class);
        setContainerDataSource(dataSource);
        setVisibleColumns(MasterRecipeList.visibleColumns);
        logger.info("{} created", getClass().getName());
    }

    public void setDateHeader(final String date) {
        setColumnHeader(headerName, date);
        logger.debug("date header set to {}", getColumnHeader("date"));
    }

    public void setDropSource(final MasterRecipeListView masterRecipeListView) {
        logger.debug("Initialising drop source");
        setDropHandler(tableDropHandler(new SourceIs((Component) masterRecipeListView)));
    }

    public DropHandler tableDropHandler(final ClientSideCriterion acceptCriterion) {
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

    private void configureAsDynamicallyUpdated() {
        setImmediate(true);
        setSelectable(true);
        enableContentRefreshing(true);
    }

    private boolean createInitialHeader() {
        headerName = "Date";
        return addContainerProperty(headerName, String.class, null);
    }

}
