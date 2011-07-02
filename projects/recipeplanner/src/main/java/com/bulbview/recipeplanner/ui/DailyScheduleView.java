package com.bulbview.recipeplanner.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.ui.manager.GenericListView;
import com.bulbview.recipeplanner.ui.presenter.DailySchedulePresenter;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DailyScheduleView extends GenericListView<Item, DailySchedulePresenter> {
    
    private String header;
    
    public DailyScheduleView() {
        super(Item.class);
    }
    
    public void clear() {
        newDataSource.removeAllItems();
    }
    
    public String getHeader() {
        return header;
    }
    
    @Override
    public void init() {
        super.init();
        setVisibleColumns("name");
        genericListTable.setDragMode(TableDragMode.ROW);
        genericListTable.setDropHandler(tableDropHandler());
    }
    
    @Override
    @Autowired
    public void setGenericListTable(final Table genericListTable) {
        super.setGenericListTable(genericListTable);
    }
    
    public void setHeader(String string) {
        this.header = string;
    }
    
    @Autowired
    @Override
    public void setTopLevelPanel(final Panel panel) {
        super.setTopLevelPanel(panel);
    }
    
    @SuppressWarnings("serial")
    private DropHandler tableDropHandler() {
        return new DropHandler() {
            
            @Override
            public void drop(final DragAndDropEvent dropEvent) {
                // criteria verify that this is safe
                final DataBoundTransferable t = (DataBoundTransferable) dropEvent.getTransferable();
                // final Container sourceContainer = t.getSourceContainer();
                // logger.debug("drag and drop source container: " +
                // sourceContainer);
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
