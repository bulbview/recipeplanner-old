package com.bulbview.recipeplanner.ui.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.schedule.Schedule;
import com.bulbview.recipeplanner.ui.presenter.ScheduleHistoryPresenter;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

@Component
public class ScheduleHistoryListView extends GenericListView<Schedule, ScheduleHistoryPresenter> {

    @Autowired
    private Window scheduleHistoryWindow;

    public ScheduleHistoryListView() {
        super(Schedule.class);

    }

    @Override
    public void init() {
        super.init();
        initWindow();
        addThisToWindow();
        setVisibleColumns("name");

    }

    @Autowired
    public void initialise(final Panel panel) {
        setTopLevelPanel(panel);
    }

    @Override
    @Autowired
    public void setGenericListTable(final Table genericListTable) {
        super.setGenericListTable(genericListTable);
    }

    private void addThisToWindow() {
        scheduleHistoryWindow.addComponent(getTopLevelPanel());
    }

    private void initWindow() {
        scheduleHistoryWindow.setWidth("10%");
        scheduleHistoryWindow.setHeight("-1px");
        addThisToWindow();
    }

}
