package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.ui.DailySchedule;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DailySchedulePresenter extends Presenter {

    private DailySchedule         dailySchedule;
    private ShoppingListPresenter shoppingListPresenter;

    public void dragAndDrop(final Item item) {
        dailySchedule.addListItem(item);
        addToShoppingList(item);
    }

    @Override
    public void init() {

    }

    public void setDailySchedule(final DailySchedule dailySchedule) {
        this.dailySchedule = dailySchedule;
    }

    @Autowired
    public void setShoppingListPresenter(final ShoppingListPresenter shoppingListPresenter) {
        this.shoppingListPresenter = shoppingListPresenter;
    }

    private void addToShoppingList(final Item savedItem) {
        shoppingListPresenter.addItem(savedItem);
    }

}
