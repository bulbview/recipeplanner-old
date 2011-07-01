package test.com.bulbview.recipeplanner.presenter


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext

import test.com.bulbview.recipeplanner.dao.SpringContextTestFixture

import com.bulbview.recipeplanner.datamodel.schedule.DateSection
import com.bulbview.recipeplanner.datamodel.schedule.NameSection
import com.bulbview.recipeplanner.datamodel.schedule.Schedule
import com.bulbview.recipeplanner.datamodel.schedule.SectionFactory
import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao
import com.bulbview.recipeplanner.ui.manager.WeeklyScheduleView
import com.bulbview.recipeplanner.ui.presenter.DailySchedulePresenter
import com.bulbview.recipeplanner.ui.presenter.DailySchedulePresenterFactory
import com.bulbview.recipeplanner.ui.presenter.ScheduleHistoryPresenter
import com.bulbview.recipeplanner.ui.presenter.WeeklyScheduleModel
import com.bulbview.recipeplanner.ui.presenter.WeeklySchedulePresenter


@DirtiesContext
class WeeklySchedulePresenterTest extends SpringContextTestFixture {

    static final String MISC_ITEMS = "Miscellaneous Items"

    def WeeklyScheduleView mockWeeklyScheduleView
    def WeeklyScheduleModel mockScheduleModel
    def DailySchedulePresenterFactory mockDailySchedulePresenterFactory
    def SectionFactory mockSectionFactory
    def ScheduleHistoryPresenter mockScheduleHistoryPresenter
    @Autowired
    def WeeklySchedulePresenter presenter
    def Schedule schedule
    def DailySchedulePresenter mockDailySchedulePresenter
    @Autowired
    def ScheduleObjectifyDao scheduleDao
    def NameSection mockNamedSection
    def DateSection mockDateSection

    def Date startDate

    def setup() {
        schedule = new Schedule()
        mockWeeklyScheduleView = Mock()
        mockDailySchedulePresenter = Mock()
        mockScheduleModel = Mock()
        mockDailySchedulePresenterFactory = Mock()
        mockSectionFactory = Mock()
        mockNamedSection = Mock()
        mockDateSection = Mock()
        mockScheduleHistoryPresenter = Mock()
        presenter.setScheduleHistoryPresenter(mockScheduleHistoryPresenter)
        presenter.setView(mockWeeklyScheduleView)
        presenter.setModel(mockScheduleModel)
        presenter.setDailySchedulePresenterFactory(mockDailySchedulePresenterFactory)
        presenter.setSectionFactory(mockSectionFactory)
        mockScheduleModel.getSchedule() >> schedule
        mockDailySchedulePresenterFactory.create() >> mockDailySchedulePresenter
    }

    def "should update the schedule history view when a new schedule is saved" () {
        when:""
        presenter.saveSchedule()
        then:""
        1 * mockScheduleHistoryPresenter.addScheduleToView(schedule)
    }


    def "should create a misc items tab on date selection" () {
        given:
        presenter.init()

        when:"the start date is set"
        presenter.setStartDate(new Date())

        then:"miscellaneous tab is added to view"
        1 * mockSectionFactory.create(MISC_ITEMS) >> mockNamedSection
        1 * mockDailySchedulePresenter.setSection(mockNamedSection)
    }

    def "should create new schedule on initialisation" () {
        when:"the presenter is initialised"
        presenter.init()
        then:""
        1 * mockScheduleModel.setSchedule(_)
    }

    def "should clear the schedule when a start date selected" () {
        given:
        presenter.init()

        when:"start date is seleected"
        presenter.setStartDate(new Date())

        then:""
        1 * mockWeeklyScheduleView.clearSectionsFromSchedule()
    }

    def "should add schedule days to the schedule entity when start date selected" () {
        given:
        presenter.init()

        when:"start date is selected"
        presenter.setStartDate(new Date())
        then:""
        7 * mockSectionFactory.create(_ as Date) >> mockDateSection
        7 * mockScheduleModel.addSection(_ as DateSection)
    }

    def "should add  misc section to schedule entity when the start date is selected" () {
        given:
        presenter.init()
        when:""
        presenter.setStartDate(new Date())
        then:""
        1 * mockSectionFactory.create(MISC_ITEMS) >> mockNamedSection
        1 * mockScheduleModel.addSection(_ as NameSection)
    }



    def "should set date header for all tabs, for the week, when a new start date is selected" () {
        given:"the presenter is initialised"
        presenter.init()
        startDate = new Date(109,05,13)

        when:"a start date is set"
        presenter.setStartDate(startDate)

        then:"7 daily tabs are created"
        7 * mockDailySchedulePresenter.setSection(mockDateSection)

        and:"incremental Date is set for each section"
        1 * mockSectionFactory.create(startDate) >> mockDateSection
        1 * mockSectionFactory.create(startDate+1) >> mockDateSection
        1 * mockSectionFactory.create(startDate+2)>> mockDateSection
        1 * mockSectionFactory.create(startDate+3)>> mockDateSection
        1 * mockSectionFactory.create(startDate+4)>> mockDateSection
        1 * mockSectionFactory.create(startDate+5)>> mockDateSection
        1 * mockSectionFactory.create(startDate+6)>> mockDateSection
    }

    def "should save schedule when save is called" () {
        given:"presenter is initialised"
        presenter.init()

        and:"a start date is set"
        schedule.setStartDate(new Date(112,8,22))

        when:"the schedule is saved"
        presenter.saveSchedule()

        then:"the schedule is persisted"
        scheduleDao.getByName("Sep 22, 2012") != null
    }

    def "should clear all daily schedules when the schedule is cleared" () {
        given:""
        presenter.init()
        presenter.setStartDate(new Date())
        when:""
        presenter.clearSchedule()

        then:"each schedule view is cleared"
        //only 1 mock is returned by mock factory so a single iteration
        1 * mockDailySchedulePresenter.clear()
    }


    def "should set start date on initialisation" () {
        given:
        def startDate = new Date()
        presenter.init()
        when:""
        presenter.setStartDate(startDate)

        then:""
        1 * mockScheduleModel.setStartDate(startDate)
    }



    def "should add schedule days when schedule requested to be displayed" () {
        given:"the section has date sections for a week"
        for(i in 0..7) {
            schedule.addSection new DateSection()
        }

        when:""
        presenter.display(schedule)
        then:""
        7 * mockScheduleModel.addSection(_ as DateSection)
    }

    def "should add misc section to schedule entity when a schedule is requested to be displayed" () {
        given:
        def nameSection = new NameSection()
        schedule.addSection nameSection

        when:
        presenter.display(schedule)

        then:""
        1 * mockScheduleModel.addSection(nameSection)
    }

    def "should create a new the schedule when a start date selected" () {
        given:
        presenter.init()

        when:"start date is selected"
        presenter.setStartDate(new Date())

        then:""
        1 * mockScheduleModel.setSchedule(_)
    }
}