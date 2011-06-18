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
    @Autowired
    def WeeklySchedulePresenter presenter
    def Schedule schedule
    def DailyScheduleView mockDailyScheduleView
    @Autowired
    def ScheduleObjectifyDao scheduleDao

    def Date returnedDate

    def setup() {
        schedule = new Schedule()
        mockWeeklySchedule = Mock()
        mockDailyScheduleView = Mock()
        mockScheduleModel = Mock()
        mockDailyScheduleFactory = Mock()
        returnedDate =  new Date()
        presenter.setWeeklySchedule(mockWeeklySchedule)
        presenter.setModel(mockScheduleModel)
        presenter.setDailyScheduleListFactory(mockDailyScheduleFactory)
        mockScheduleModel.getStartDate() >>returnedDate
        mockScheduleModel.getSchedule() >> schedule

        mockDailyScheduleFactory.getObject() >> mockDailyScheduleView
    }


    def "should create 7 daily tabs on startup" () {
        when:"the presenter is initialised"
        presenter.init()

        then:"7 daily tabs are created (any tab which is not 'Additional Items')"
        7 * mockWeeklySchedule.addTab(!MISC_ITEMS, _)
    }

    def "should create an additional items tab on startup" () {
        when:"the presenter is initialised"
        presenter.init()
        then:""
        1 * mockWeeklySchedule.addTab(MISC_ITEMS, _)
    }

    def "should create new schedule on initialisation" () {
        when:"the presenter is initialised"
        presenter.init()
        then:""
        1 * mockScheduleModel.createSchedule()
    }

    def "should add schedule days to then schedule entity" () {
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
        then:""
        mockScheduleModel.getStartDate() >>returnedDate

        1 * mockWeeklySchedule.addTab("Jun 13, 2009",_)
        1 * mockWeeklySchedule.addTab("Jun 14, 2009",_)
        1 * mockWeeklySchedule.addTab("Jun 15, 2009",_)
        1 * mockWeeklySchedule.addTab("Jun 16, 2009",_)
        1 * mockWeeklySchedule.addTab("Jun 17, 2009",_)
        1 * mockWeeklySchedule.addTab("Jun 18, 2009",_)
        1 * mockWeeklySchedule.addTab("Jun 19, 2009",_)
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
