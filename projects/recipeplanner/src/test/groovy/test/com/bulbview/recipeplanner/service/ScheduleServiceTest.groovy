package test.com.bulbview.recipeplanner.service

import org.springframework.beans.factory.annotation.Autowired

import test.com.bulbview.recipeplanner.dao.SpringContextTestFixture

import com.bulbview.recipeplanner.datamodel.schedule.Schedule
import com.bulbview.recipeplanner.service.ScheduleService


class ScheduleServiceTest extends SpringContextTestFixture {

    @Autowired
    def ScheduleService scheduleService
    @Autowired
    def Schedule schedule

    def setup() {
        schedule = new Schedule()
    }
}
