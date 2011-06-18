package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.schedule.Section;
import com.bulbview.recipeplanner.ui.DailyScheduleView;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DailySchedulePresenter extends Presenter {

    private DailyScheduleView     dailyScheduleView;
    private Section               section;
    private ShoppingListPresenter shoppingListPresenter;

    public void dragAndDrop(final Item item) {
        dailyScheduleView.addListItem(item);
        section.addItem(item);
        addToShoppingList(item);
    }

    public Section getSection() {
        return section;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    public void setDailySchedule(final DailyScheduleView dailyScheduleView) {
        this.dailyScheduleView = dailyScheduleView;
    }

    public void setSection(final Section section) {
        this.section = section;

    }

    @Autowired
    public void setShoppingListPresenter(final ShoppingListPresenter shoppingListPresenter) {
        this.shoppingListPresenter = shoppingListPresenter;
    }

    private void addToShoppingList(final Item savedItem) {
        shoppingListPresenter.addItem(savedItem);
    }

}
