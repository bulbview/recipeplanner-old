package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.ScheduledItem;
import com.bulbview.recipeplanner.datamodel.schedule.Section;
import com.bulbview.recipeplanner.ui.DailyScheduleView;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DailySchedulePresenter extends Presenter<DailyScheduleView> {
    
    private Section               section;
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
    
    public Section getSection() {
        return section;
    }
    
    @Override
    public void init() {
        getView().setPresenter(this);
        getView().init();
    }
    
    public void setSection(final Section section) {
        getView().setHeader(section.toString());
        this.section = section;
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
    
    private void addToShoppingList(final ScheduledItem item) {
        shoppingListPresenter.addItem(item);
    }
    
}
