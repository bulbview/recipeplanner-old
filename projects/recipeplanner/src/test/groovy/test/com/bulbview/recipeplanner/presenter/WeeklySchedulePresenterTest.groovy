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
import com.bulbview.recipeplanner.ui.manager.WeeklySchedule
import com.bulbview.recipeplanner.ui.presenter.WeeklyScheduleModel
import com.bulbview.recipeplanner.ui.presenter.WeeklySchedulePresenter


@DirtiesContext
class WeeklySchedulePresenterTest extends SpringContextTestFixture {

    static final String MISC_ITEMS = "Miscellaneous Items"

    def WeeklySchedule mockWeeklySchedule
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

    def Date returnedDate

    def setup() {
        schedule = new Schedule()
        mockWeeklySchedule = Mock()
        mockDailyScheduleView = Mock()
        mockScheduleModel = Mock()
        mockDailyScheduleFactory = Mock()
        mockNameSectionFactory = Mock()
        mockNamedSection = Mock()
        mockDateSectionFactory = Mock()
        mockDateSection = Mock()
        returnedDate =  new Date()
        presenter.setWeeklySchedule(mockWeeklySchedule)
        presenter.setModel(mockScheduleModel)
        presenter.setDailyScheduleListFactory(mockDailyScheduleFactory)
        presenter.setNameSectionFactory(mockNameSectionFactory)
        presenter.setDayFactory(mockDateSectionFactory)
        mockScheduleModel.getStartDate() >>returnedDate
        mockScheduleModel.getSchedule() >> schedule
        mockNameSectionFactory.getObject() >> mockNamedSection
        mockDateSectionFactory.getObject() >> mockDateSection
        mockDailyScheduleFactory.getObject() >> mockDailyScheduleView
    }


    def "should create an additional items tab on startup" () {
        when:"the presenter is initialised"
        presenter.init()
        then:""
        1 * mockNamedSection.setName(MISC_ITEMS)
        1 * mockDailyScheduleView.setSection(mockNamedSection)
    }

    def "should create new schedule on initialisation" () {
        when:"the presenter is initialised"
        presenter.init()
        then:""
        1 * mockScheduleModel.createSchedule()
    }

    def "should add schedule days to the schedule entity" () {
        when:""
        presenter.init()
        then:""
        7 * mockScheduleModel.addSection(_ as DateSection)
    }

    def "should add  misc section to schedule entity" () {
        when:""
        presenter.init()
        then:""
        1 * mockScheduleModel.addSection(_ as NameSection)
    }

    def "should set date header for all tabs, for the week, on initialisation" () {
        given:
        returnedDate = new Date(109,05,13)

        when:"the presenter is initialised"
        presenter.init()
        then:"7 daily tabs are created"
        mockScheduleModel.getStartDate() >>returnedDate

        7 * mockDailyScheduleView.setSection(mockDateSection)
        1 * mockDateSection.setDate(returnedDate)
        1 * mockDateSection.setDate(returnedDate+1)
        1 * mockDateSection.setDate(returnedDate+2)
        1 * mockDateSection.setDate(returnedDate+3)
        1 * mockDateSection.setDate(returnedDate+4)
        1 * mockDateSection.setDate(returnedDate+5)
        1 * mockDateSection.setDate(returnedDate+6)
    }

    def "should save schedule" () {
        given:
        schedule.setStartDate(new Date(112,8,22))
        when:"the schedule is saved"
        presenter.init()
        presenter.saveSchedule()

        then:"the schedule is persisted"
        mockScheduleModel.getStartDate() >> new Date(112,8,22)
        scheduleDao.getByName("Sep 22, 2012") != null
    }

    def "should clear all daily schedules if schedule is cleared" () {
        given:""
        presenter.init()
        when:""
        presenter.clearSchedule()

        then:""
        1 * mockDailyScheduleView.clear()
    }
}
