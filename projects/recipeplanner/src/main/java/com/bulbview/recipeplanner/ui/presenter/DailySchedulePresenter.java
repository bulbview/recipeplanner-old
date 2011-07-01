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

    @Autowired
    private DailyScheduleView     dailyScheduleView;
    private Section               section;
    private ShoppingListPresenter shoppingListPresenter;

    public void addItem(final Item item) {
        dailyScheduleView.addListItem(item);
        section.addItem(item);
        addToShoppingList(item);
    }

    public void clear() {
        dailyScheduleView.clear();
        section.clear();
    }

    public void dragAndDrop(final Item item) {
        addItem(item);
    }

    public Section getSection() {
        return section;
    }

    public DailyScheduleView getView() {
        return dailyScheduleView;
    }

    @Override
    public void init() {
        dailyScheduleView.init();
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
