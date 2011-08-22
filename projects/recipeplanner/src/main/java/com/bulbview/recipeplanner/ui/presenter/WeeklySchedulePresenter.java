package com.bulbview.recipeplanner.ui.presenter;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.schedule.InnerSection;
import com.bulbview.recipeplanner.datamodel.schedule.Schedule;
import com.bulbview.recipeplanner.service.ScheduleService;
import com.bulbview.recipeplanner.ui.manager.WeeklyScheduleView;
import com.google.appengine.repackaged.com.google.common.collect.Lists;

@Component
public class WeeklySchedulePresenter extends Presenter<WeeklyScheduleView> implements SessionPresenter {
    
    @Autowired
    private DailySchedulePresenterFactory      dailySchedulePresenterFactory;
    private final List<DailySchedulePresenter> dailySchedulePresenters;
    private Schedule                           displayedSchedule;
    @Autowired
    private ScheduleHistoryPresenter           scheduleHistoryPresenter;
    @Autowired
    private ScheduleService                    scheduleService;
    
    @Autowired
    private ShoppingListPresenter              shoppingListPresenter;
    
    public WeeklySchedulePresenter() {
        this.dailySchedulePresenters = Lists.newArrayList();
    }
    
    public void clearSchedule() {
        for (final DailySchedulePresenter presenter : dailySchedulePresenters) {
            presenter.clear();
        }
        shoppingListPresenter.clear();
    }
    
    public Collection<DailySchedulePresenter> getDailySchedulePresenters() {
        return dailySchedulePresenters;
    }
    
    public Schedule getDisplayedSchedule() {
        return displayedSchedule;
    }
    
    @Override
    public void init() {
        getView().init();
        createAllTabs();
    }
    
    public void saveSchedule() {
        scheduleService.save(displayedSchedule);
        // TODO service should do this
        updateScheduleHistoryView(displayedSchedule);
    }
    
    public void setDailySchedulePresenterFactory(final DailySchedulePresenterFactory dailySchedulePresenterFactory) {
        this.dailySchedulePresenterFactory = dailySchedulePresenterFactory;
    }
    
    public void setScheduleHistoryPresenter(final ScheduleHistoryPresenter scheduleHistoryPresenter) {
        this.scheduleHistoryPresenter = scheduleHistoryPresenter;
    }
    
    public void setShoppingList(final ShoppingListPresenter shoppingListPresenter) {
        this.shoppingListPresenter = shoppingListPresenter;
    }
    
    public void setStartDate(final Date startDate) {
        logger.info("setting start date {}...", startDate);
        // TODO if there's already a schedule show warning to user and prompt if
        // they wish to load
        // if(scheduleDao.getByName() == startDate) {
        // show warning
        // }
        
        // getView().clearSectionsFromSchedule();
        // retrieve schedule for start date(if no schedule for start date,
        // return new schedule)
        this.displayedSchedule = resolveSchedule(startDate);
        addSectionsToDailySchedulePresenters(displayedSchedule.getDateSections());
    }
    
    @Override
    @Autowired
    public void setView(final WeeklyScheduleView weeklyScheduleView) {
        super.setView(weeklyScheduleView);
    }
    
    private void addSectionsToDailySchedulePresenters(final Collection<InnerSection> sections) {
        final Iterator<DailySchedulePresenter> dailySchedulePresenterIt = dailySchedulePresenters.iterator();
        for (final InnerSection section : sections) {
            logger.debug("adding section to presenter{}...", section);
            dailySchedulePresenterIt.next().setSection(section);
        }
    }
    
    private void createAllTabs() {
        for (int i = 0; i < 8; i++) {
            createTab();
        }
        
    }
    
    private DailySchedulePresenter createDaySchedulePresenter() {
        final DailySchedulePresenter daySchedulePresenter = dailySchedulePresenterFactory.create();
        dailySchedulePresenters.add(daySchedulePresenter);
        return daySchedulePresenter;
    }
    
    private void createTab() {
        logger.debug("Adding tab to weekly planner...");
        final DailySchedulePresenter daySchedulePresenter = createDaySchedulePresenter();
        getView().addTab(daySchedulePresenter.getView());
    }
    
    private boolean no(final Schedule persistedScheduleForSelectedDate) {
        return persistedScheduleForSelectedDate == null;
    }
    
    private boolean noDisplayedSchedule() {
        return displayedSchedule == null;
    }
    
    private Schedule resolveSchedule(Date startDate) {
        final Schedule persistedScheduleForSelectedDate = scheduleService.getSchedule(startDate);
        Schedule schedule = null;
        if (no(persistedScheduleForSelectedDate)) {
            if (noDisplayedSchedule()) {
                logger.debug("creating new schedule for: {}...", startDate);
                schedule = scheduleService.createSchedule(startDate);
            }
            else {
                logger.debug("updating existing schedule with start date: {}...", startDate);
                displayedSchedule.setStartDate(startDate);
            }
        }
        else {
            schedule = persistedScheduleForSelectedDate;
            logger.debug("...persisted schedule found: {}", schedule);
        }
        return schedule;
    }
    
    private void updateScheduleHistoryView(final Schedule schedule) {
        scheduleHistoryPresenter.addScheduleToView(schedule);
    }
    
}
