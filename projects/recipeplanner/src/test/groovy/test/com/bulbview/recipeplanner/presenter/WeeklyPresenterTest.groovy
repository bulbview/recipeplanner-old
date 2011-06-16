package test.com.bulbview.recipeplanner.presenter


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext

import test.com.bulbview.recipeplanner.dao.SpringContextTestFixture

import com.bulbview.recipeplanner.datamodel.Schedule
import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao
import com.bulbview.recipeplanner.ui.manager.WeeklySchedule
import com.bulbview.recipeplanner.ui.presenter.WeeklySchedulePresenter


@DirtiesContext
class WeeklyPresenterTest extends SpringContextTestFixture {

    static final String MISC_ITEMS = "Miscellaneous Items"

    def WeeklySchedule mockWeeklySchedule
    def org.springframework.beans.factory.ObjectFactory<Schedule> mockScheduleFactory
    @Autowired
    def WeeklySchedulePresenter presenter
    def Schedule schedule
    @Autowired
    def ScheduleObjectifyDao scheduleDao

    def setup() {
        schedule = new Schedule()
        mockWeeklySchedule = Mock()
        mockScheduleFactory = Mock()
        presenter.setWeeklySchedule(mockWeeklySchedule)
        presenter.setScheduleFactory(mockScheduleFactory)
        presenter.setStartDate(new Date())
        mockScheduleFactory.getObject() >> schedule
    }


    def "should create 7 daily tabs on startup" () {
        when:"the presenter is initialised"
        presenter.init()

        then:"7 daily tabs are created (any tab which is not 'Additional Items')"
        7 * mockWeeklySchedule.createTab(!MISC_ITEMS)
    }

    def "should create an additional items tab on startup" () {
        when:"the presenter is initialised"
        presenter.init()
        then:""
        1 * mockWeeklySchedule.createTab(MISC_ITEMS)
    }

    def "should create new schedule on initialisation" () {
        when:"the presenter is initialised"
        presenter.init()
        then:""
        1 * mockScheduleFactory.getObject() >> schedule
    }

    def "should set date header for all tabs, for the week, on initialisation" () {
        given:
        presenter.setStartDate(new Date(111,05,13))
        when:"the presenter is initialised"
        presenter.init()
        then:""
        1 * mockWeeklySchedule.createTab("Jun 13, 2011")
        1 * mockWeeklySchedule.createTab("Jun 14, 2011")
        1 * mockWeeklySchedule.createTab("Jun 15, 2011")
        1 * mockWeeklySchedule.createTab("Jun 16, 2011")
        1 * mockWeeklySchedule.createTab("Jun 17, 2011")
        1 * mockWeeklySchedule.createTab("Jun 18, 2011")
        1 * mockWeeklySchedule.createTab("Jun 19, 2011")
    }

    def "should save schedule" () {
        given:"A schedule is created"
        presenter.init()
        schedule.setName("September 22, 2012")

        when:"the schedule is saved"
        presenter.saveSchedule()

        then:"the schedule is persisted"
        scheduleDao.getByName("September 22, 2012") != null
    }
}
