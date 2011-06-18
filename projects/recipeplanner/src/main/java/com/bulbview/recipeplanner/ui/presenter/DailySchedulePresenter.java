package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.schedule.Schedule;
import com.bulbview.recipeplanner.ui.DailyScheduleView;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DailySchedulePresenter extends Presenter {

    private DailyScheduleView     dailyScheduleView;
    private Schedule              schedule;
    private ShoppingListPresenter shoppingListPresenter;

    public void dragAndDrop(final Item item) {
        dailyScheduleView.addListItem(item);
        addToShoppingList(item);
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    public void setDailySchedule(final DailyScheduleView dailyScheduleView) {
        this.dailyScheduleView = dailyScheduleView;
    }

    public void setSchedule(final Schedule schedule) {
        this.schedule = schedule;

    }

    @Autowired
    public void setShoppingListPresenter(final ShoppingListPresenter shoppingListPresenter) {
        this.shoppingListPresenter = shoppingListPresenter;
    }

    private void addToShoppingList(final Item savedItem) {
        shoppingListPresenter.addItem(savedItem);
    }

}
