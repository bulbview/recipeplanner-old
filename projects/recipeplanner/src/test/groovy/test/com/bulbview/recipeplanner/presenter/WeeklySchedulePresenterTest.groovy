package test.com.bulbview.recipeplanner.presenter


import org.springframework.beans.factory.ObjectFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext

import test.com.bulbview.recipeplanner.dao.SpringContextTestFixture

import com.bulbview.recipeplanner.datamodel.schedule.DateSection
import com.bulbview.recipeplanner.datamodel.schedule.NameSection
import com.bulbview.recipeplanner.datamodel.schedule.Schedule
import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao
import com.bulbview.recipeplanner.ui.DailyScheduleView
import com.bulbview.recipeplanner.ui.manager.WeeklyScheduleView
import com.bulbview.recipeplanner.ui.presenter.WeeklyScheduleModel
import com.bulbview.recipeplanner.ui.presenter.WeeklySchedulePresenter


@DirtiesContext
class WeeklySchedulePresenterTest extends SpringContextTestFixture {

    static final String MISC_ITEMS = "Miscellaneous Items"

    def WeeklyScheduleView mockWeeklyScheduleView
    def WeeklyScheduleModel mockScheduleModel
    def ObjectFactory<DailyScheduleView> mockDailyScheduleFactory
    def ObjectFactory<NameSection> mockNameSectionFactory
    def ObjectFactory<DateSection> mockDateSectionFactory
    @Autowired
    def WeeklySchedulePresenter presenter
    def Schedule schedule
    def DailyScheduleView mockDailyScheduleView
    @Autowired
    def ScheduleObjectifyDao scheduleDao
    def NameSection mockNamedSection
    def DateSection mockDateSection

    def Date startDate

    def setup() {
        schedule = new Schedule()
        mockWeeklyScheduleView = Mock()
        mockDailyScheduleView = Mock()
        mockScheduleModel = Mock()
        mockDailyScheduleFactory = Mock()
        mockNameSectionFactory = Mock()
        mockNamedSection = Mock()
        mockDateSectionFactory = Mock()
        mockDateSection = Mock()
        presenter.setView(mockWeeklyScheduleView)
        presenter.setModel(mockScheduleModel)
        presenter.setDailyScheduleListFactory(mockDailyScheduleFactory)
        presenter.setNameSectionFactory(mockNameSectionFactory)
        presenter.setDayFactory(mockDateSectionFactory)
        mockScheduleModel.getSchedule() >> schedule
        mockNameSectionFactory.getObject() >> mockNamedSection
        mockDateSectionFactory.getObject() >> mockDateSection
        mockDailyScheduleFactory.getObject() >> mockDailyScheduleView
    }


    def "should create a misc items tab on date selection" () {
        given:
        presenter.init()

        when:"the start date is set"
        presenter.setStartDate(new Date())

        then:"miscellaneous tab is added to view"
        1 * mockNamedSection.setName(MISC_ITEMS)
        1 * mockDailyScheduleView.setSection(mockNamedSection)
    }

    def "should create new schedule on initialisation" () {
        when:"the presenter is initialised"
        presenter.init()
        then:""
        1 * mockScheduleModel.createSchedule()
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
        7 * mockScheduleModel.addSection(_ as DateSection)
    }

    def "should add  misc section to schedule entity when the start date is selected" () {
        given:
        presenter.init()
        when:""
        presenter.setStartDate(new Date())
        then:""
        1 * mockScheduleModel.addSection(_ as NameSection)
    }



    def "should set date header for all tabs, for the week, when a new start date is selected" () {
        given:"the presenter is initialised"
        presenter.init()
        startDate = new Date(109,05,13)

        when:"a start date is set"
        presenter.setStartDate(startDate)

        then:"7 daily tabs are created"
        7 * mockDailyScheduleView.setSection(mockDateSection)
        1 * mockDateSection.setDate(startDate)
        1 * mockDateSection.setDate(startDate+1)
        1 * mockDateSection.setDate(startDate+2)
        1 * mockDateSection.setDate(startDate+3)
        1 * mockDateSection.setDate(startDate+4)
        1 * mockDateSection.setDate(startDate+5)
        1 * mockDateSection.setDate(startDate+6)
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
        1 * mockDailyScheduleView.clear()
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

    def "should create a new the schedule when a start date selected" () {
        given:
        presenter.init()

        when:"start date is selected"
        presenter.setStartDate(new Date())

        then:""
        1 * mockScheduleModel.createSchedule()
    }
}
