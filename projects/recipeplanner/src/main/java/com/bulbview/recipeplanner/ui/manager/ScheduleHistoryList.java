package com.bulbview.recipeplanner.ui.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Schedule;
import com.bulbview.recipeplanner.ui.MainWindow;
import com.bulbview.recipeplanner.ui.presenter.WeeklySchedulePresenter;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

@Component
public class ScheduleHistoryList extends GenericListUiManager<Schedule, WeeklySchedulePresenter> {

    @Autowired
    private Window scheduleHistoryWindow;

    public ScheduleHistoryList() {
        super(Schedule.class);

    }

    public void addSchedule(final Schedule schedule) {
        addListItem(schedule);
    }

    @Override
    public void init() {
        super.init();
        initWindow();
        topLevelPanel.setCaption("Schedule History");
    }

    @Autowired
    public void initialise(final MainWindow generatedComponent) {
        setTopLevelPanel(generatedComponent.getRecipePanel());
    }

    @Override
    @Autowired
    public void setGenericListTable(final Table genericListTable) {
        super.setGenericListTable(genericListTable);
    }

    public void setRecipes(final Collection<Schedule> schedules) {
        for ( final Schedule schedule : schedules ) {
            addSchedule(schedule);
        }
    }

    private void initWindow() {
        scheduleHistoryWindow.setWidth("50%");
        scheduleHistoryWindow.setHeight("50%");
        scheduleHistoryWindow.addComponent(getTopLevelPanel());
    }

}
