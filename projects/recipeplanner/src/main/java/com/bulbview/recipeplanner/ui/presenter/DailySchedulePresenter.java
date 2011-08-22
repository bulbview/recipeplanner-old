package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.ScheduledItem;
import com.bulbview.recipeplanner.datamodel.schedule.InnerSection;
import com.bulbview.recipeplanner.persistence.EntityDao;
import com.bulbview.recipeplanner.ui.DailyScheduleView;
import com.googlecode.objectify.Key;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DailySchedulePresenter extends Presenter<DailyScheduleView> {
    
    @Autowired
    private EntityDao<Item>       itemDao;
    private InnerSection          section;
    private ShoppingListPresenter shoppingListPresenter;
    
    public void addItem(final ScheduledItem item) {
        getView().addListItem(item);
        section.addItem(item);
        addToShoppingList(item);
    }
    
    public void clear() {
        getView().clear();
        section.clear();
    }
    
    public void dragAndDrop(final ScheduledItem item) {
        addItem(item);
    }
    
    public InnerSection getSection() {
        return section;
    }
    
    @Override
    public void init() {
        getView().setPresenter(this);
        getView().init();
    }
    
    public void setSection(final InnerSection section2) {
        this.section = section2;
        updateHeader();
        loadItemsFromSection(section2);
    }
    
    @Autowired
    public void setShoppingListPresenter(final ShoppingListPresenter shoppingListPresenter) {
        this.shoppingListPresenter = shoppingListPresenter;
    }
    
    @Override
    @Autowired
    public void setView(final DailyScheduleView dailyScheduleView) {
        super.setView(dailyScheduleView);
    }
    
    public void updateHeader() {
        getView().setHeader(section.getName());
    }
    
    private void addToShoppingList(final ScheduledItem item) {
        shoppingListPresenter.addItem(item);
    }
    
    private void loadItemsFromSection(InnerSection sections) {
        logger.debug("retrieving {} items for section {}", sections.getItems().size(), sections);
        for (final Key<Item> itemKey : sections.getItems()) {
            getView().addListItem(itemDao.get(itemKey));
        }
    }
    
}
