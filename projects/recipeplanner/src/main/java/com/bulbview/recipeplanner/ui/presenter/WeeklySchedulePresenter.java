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
import com.bulbview.recipeplanner.service.ScheduleService;
import com.bulbview.recipeplanner.ui.DailyScheduleView;
import com.bulbview.recipeplanner.ui.manager.ScheduleHistoryListView;
import com.bulbview.recipeplanner.ui.manager.WeeklyScheduleView;
import com.google.appengine.repackaged.com.google.common.collect.Sets;

@Component
public class WeeklySchedulePresenter extends Presenter implements SessionPresenter {

    private static final int                    DAY_IN_MILLIS = 1 * 24 * 60 * 60 * 1000;
    private ObjectFactory<DailyScheduleView>    dailyScheduleViewFactory;

    private final Collection<DailyScheduleView> dailyScheduleViews;
    @Autowired
    private DateFormat                          dateFormatter;
    @Autowired
    private ObjectFactory<DateSection>          dateSectionFactory;
    private ObjectFactory<NameSection>          nameSectionFactory;
    @Autowired
    private ScheduleHistoryListView             scheduleHistoryListView;
    @Autowired
    private ScheduleService                     scheduleService;
    private WeeklyScheduleModel                 weeklyScheduleModel;
    @Autowired
    private WeeklyScheduleView                  weeklyScheduleView;

    public WeeklySchedulePresenter() {
        this.dailyScheduleViews = Sets.newHashSet();
    }

    public void clearSchedule() {
        for ( final DailyScheduleView dailyScheduleView : dailyScheduleViews ) {
            dailyScheduleView.clear();
        }
    }

    @Override
    public void init() {
        createNewSchedule();
        initViews();
    }

    public void saveSchedule() {
        scheduleService.save(weeklyScheduleModel.getSchedule());
    }

    @Autowired
    public void setDailyScheduleListFactory(final ObjectFactory<DailyScheduleView> dayScheduleListFactory) {
        this.dailyScheduleViewFactory = dayScheduleListFactory;
    }

    public void setDayFactory(final ObjectFactory<DateSection> dayFactory) {
        this.dateSectionFactory = dayFactory;
    }

    @Autowired
    public void setModel(final WeeklyScheduleModel model) {
        this.weeklyScheduleModel = model;
    }

    @Autowired
    public void setNameSectionFactory(final ObjectFactory<NameSection> nameSectionFactory) {
        this.nameSectionFactory = nameSectionFactory;
    }

    public void setStartDate(final Date startDate) {
        weeklyScheduleModel.createSchedule();
        weeklyScheduleModel.setStartDate(startDate);
        weeklyScheduleView.clearSectionsFromSchedule();
        createViewDailyTabs(startDate);
        createMiscTab();
    }

    @Autowired
    public void setView(final WeeklyScheduleView weeklyScheduleView) {
        this.weeklyScheduleView = weeklyScheduleView;
    }

    private void addDailyTab(final Date incrementedDate) {
        final DateSection section = createSection(incrementedDate);
        addViewTabAndAddSectionToSchedule(section);
    }

    private void addViewTabAndAddSectionToSchedule(final Section scheduleSection) {
        final DailyScheduleView dayScheduleList = createDayScheduleView();
        dayScheduleList.setSection(scheduleSection);
        weeklyScheduleView.addTab(dayScheduleList);
        weeklyScheduleModel.addSection(scheduleSection);
    }

    private DailyScheduleView createDayScheduleView() {
        final DailyScheduleView dayScheduleList = dailyScheduleViewFactory.getObject();
        dayScheduleList.init();
        dailyScheduleViews.add(dayScheduleList);
        return dayScheduleList;
    }

    private void createMiscTab() {
        addViewTabAndAddSectionToSchedule(createNameSection("Miscellaneous Items"));
    }

    private NameSection createNameSection(final String name) {
        final NameSection nameSection = nameSectionFactory.getObject();
        nameSection.setName(name);
        return nameSection;
    }

    private void createNewSchedule() {
        weeklyScheduleModel.createSchedule();
    }

    private DateSection createSection(final Date date) {
        final DateSection dateSection = dateSectionFactory.getObject();
        dateSection.setDate(date);
        return dateSection;
    }

    private void createViewDailyTabs(final Date startDate) {
        logger.info("Creating tabs with start date: {}", startDate);
        addDailyTab(startDate);
        Date incrementedDate = startDate;
        for ( int i = 0; i < 6; i++ ) {
            incrementedDate = incrementDate(incrementedDate);
            addDailyTab(incrementedDate);
        }
    }

    private String formatDate(final Date incrementedDate) {
        return dateFormatter.format(incrementedDate);
    }

    private Date incrementDate(final Date date) {
        return new Date(date.getTime() + DAY_IN_MILLIS);
    }

    private void initViews() {
        weeklyScheduleView.init();
        scheduleHistoryListView.init();
    }

}
