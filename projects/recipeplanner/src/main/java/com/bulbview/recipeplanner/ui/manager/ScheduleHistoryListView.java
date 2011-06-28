package com.bulbview.recipeplanner.ui.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.schedule.Schedule;
import com.bulbview.recipeplanner.ui.presenter.ScheduleHistoryPresenter;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

@Component
public class ScheduleHistoryListView extends GenericListView<Schedule, ScheduleHistoryPresenter> {

    @Autowired
    private Action[] actions;
    @Autowired
    private Window   scheduleHistoryWindow;

    public ScheduleHistoryListView() {
        super(Schedule.class);

    }

    @Override
    public void init() {
        super.init();
        initWindow();
        addThisToWindow();
        setVisibleColumns("name");
        genericListTable.setSelectable(true);
        genericListTable.addActionHandler(getContextMenu());

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

    @SuppressWarnings("serial")
    private Handler getContextMenu() {
        return new Handler() {

            @Override
            public Action[] getActions(final Object target,
                                       final Object sender) {
                if( target != null ) {
                    final String name = ( (Schedule) target ).getName();
                    actions[0].setCaption(actions[0].getCaption() + name);
                }
                return actions;
            }

            @Override
            public void handleAction(final Action action,
                                     final Object sender,
                                     final Object target) {
                presenter.load((Schedule) target);
            }
        };
    }

    private void initWindow() {
        scheduleHistoryWindow.setWidth("10%");
        scheduleHistoryWindow.setHeight("-1px");
        addThisToWindow();
    }

}
