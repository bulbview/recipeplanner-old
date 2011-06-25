package test.com.bulbview.recipeplanner.service

import org.springframework.beans.factory.annotation.Autowired

import test.com.bulbview.recipeplanner.dao.SpringContextTestFixture

import com.bulbview.recipeplanner.datamodel.schedule.Schedule
import com.bulbview.recipeplanner.service.ScheduleService
import com.bulbview.recipeplanner.ui.presenter.ScheduleHistoryPresenter


class ScheduleServiceTest extends SpringContextTestFixture {

    def ScheduleHistoryPresenter mockScheduleHistoryPresenter
    @Autowired
    def ScheduleService scheduleService
    @Autowired
    def Schedule schedule

    def setup() {
        mockScheduleHistoryPresenter = Mock()
        scheduleService.setScheduleHistoryPresenter(mockScheduleHistoryPresenter)
        schedule = new Schedule()
    }

    def "should update the schedule history view when a new schedule is saved" () {
        when:""
        scheduleService.save(schedule)
        then:""
        1 * mockScheduleHistoryPresenter.addScheduleToView(schedule)
    }
}
