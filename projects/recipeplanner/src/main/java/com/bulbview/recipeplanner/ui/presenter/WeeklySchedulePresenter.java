package com.bulbview.recipeplanner.ui.presenter;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.schedule.DateSection;
import com.bulbview.recipeplanner.datamodel.schedule.NameSection;
import com.bulbview.recipeplanner.datamodel.schedule.Section;
import com.bulbview.recipeplanner.persistence.DaoException;
import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao;
import com.bulbview.recipeplanner.ui.DailyScheduleView;
import com.bulbview.recipeplanner.ui.manager.MainWindowUiManager;
import com.bulbview.recipeplanner.ui.manager.ScheduleHistoryList;
import com.bulbview.recipeplanner.ui.manager.WeeklySchedule;
import com.google.appengine.repackaged.com.google.common.collect.Sets;

@Component
public class WeeklySchedulePresenter extends Presenter implements SessionPresenter {

    private static final int                    DAY_IN_MILLIS = 1 * 24 * 60 * 60 * 1000;
    private ObjectFactory<DailyScheduleView>    dailyScheduleListFactory;

    private final Collection<DailyScheduleView> dailyScheduleViews;
    private final DateFormat                    dateFormatter;
    @Autowired
    private ObjectFactory<DateSection>          dayFactory;
    @Autowired
    private MainWindowUiManager                 mainWindow;
    @Autowired
    private ObjectFactory<NameSection>          nameSectionFactory;
    @Autowired
    private ScheduleObjectifyDao                scheduleDao;
    @Autowired
    private ScheduleHistoryList                 scheduleHistoryList;
    @Autowired
    private WeeklySchedule                      weeklySchedule;
    private WeeklyScheduleModel                 weeklyScheduleModel;

    public WeeklySchedulePresenter() {
        this.dateFormatter = DateFormat.getDateInstance();
        this.dailyScheduleViews = Sets.newHashSet();
    }

    public void clearSchedule() {
        for ( final DailyScheduleView dailyScheduleView : dailyScheduleViews ) {
            dailyScheduleView.clear();
        }
    }

    public void createTab(final String header) {
        logger.debug("Creating tab with header {}", header);
        final DailyScheduleView dayScheduleList = createDaySchedule();
        weeklySchedule.addTab(header, dayScheduleList);

    }

    @Override
    public void init() {
        initViews();
        createNewSchedule();
        createAllTabs();
    }

    public void saveSchedule() {
        try {
            scheduleDao.save(weeklyScheduleModel.getSchedule());
        } catch (final DaoException e) {
            throw new WeeklySchedulePresenterException("Error saving schedule", e);
        }
    }

    @Autowired
    public void setDailyScheduleListFactory(final ObjectFactory<DailyScheduleView> dayScheduleListFactory) {
        this.dailyScheduleListFactory = dayScheduleListFactory;
    }

    @Autowired
    public void setModel(final WeeklyScheduleModel model) {
        this.weeklyScheduleModel = model;
    }

    public void setStartDate(final Date startDate) {
        weeklyScheduleModel.setStartDate(startDate);
    }

    @Autowired
    public void setWeeklySchedule(final WeeklySchedule weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
    }

    public void showHistory() {
        mainWindow.showScheduleHistoryWindow();
    }

    private void addDailyTab(final Date incrementedDate) {
        final String formattedDate = formatDate(incrementedDate);
        final DateSection section = createSection(incrementedDate);
        addTabAndAddDayToSchedule(formattedDate, section);
    }

    private void addHeaderToTabView(final String header) {
        createTab(header);
    }

    // TODO refactor all schedule methods to new scheduleModel class
    private void addScheduleToDailySchedulePresenter() {
        for ( final DailyScheduleView dailyScheduleView : dailyScheduleViews ) {
            dailyScheduleView.setSchedule(weeklyScheduleModel.getSchedule());
        }
    }

    private void addTabAndAddDayToSchedule(final String tabHeader,
                                           final Section scheduleSection) {
        addHeaderToTabView(tabHeader);
        weeklyScheduleModel.addSection(scheduleSection);
    }

    private void createAllTabs() {
        createDailyTabs();
        addTabAndAddDayToSchedule("Miscellaneous Items", createNameSection("Miscellaneous Items"));
    }

    private void createDailyTabs() {
        final Date startDate = weeklyScheduleModel.getStartDate();
        logger.info("Creating tabs with start date: {}", startDate);
        addDailyTab(startDate);
        Date incrementedDate = startDate;
        for ( int i = 0; i < 6; i++ ) {
            incrementedDate = incrementDate(incrementedDate);
            addDailyTab(incrementedDate);
        }
    }

    private DailyScheduleView createDaySchedule() {
        final DailyScheduleView dayScheduleList = dailyScheduleListFactory.getObject();
        dayScheduleList.init();
        dailyScheduleViews.add(dayScheduleList);
        return dayScheduleList;
    }

    private NameSection createNameSection(final String name) {
        final NameSection nameSection = nameSectionFactory.getObject();
        nameSection.setName(name);
        return nameSection;
    }

    private void createNewSchedule() {
        weeklyScheduleModel.createSchedule();
        addScheduleToDailySchedulePresenter();
    }

    private DateSection createSection(final Date date) {
        final DateSection dateSection = dayFactory.getObject();
        dateSection.setDate(date);
        return dateSection;
    }

    private String formatDate(final Date incrementedDate) {
        return dateFormatter.format(incrementedDate);
    }

    private Date incrementDate(final Date date) {
        return new Date(date.getTime() + DAY_IN_MILLIS);
    }

    private void initViews() {
        weeklySchedule.init();
        scheduleHistoryList.init();
    }

}
