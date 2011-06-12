package test.com.bulbview.recipeplanner.dao

import org.springframework.beans.factory.annotation.Autowired

import com.bulbview.recipeplanner.datamodel.Schedule
import com.bulbview.recipeplanner.persistence.DaoException
import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao


class ScheduleDaoTest extends DaoTestFixture {

    def TestUtilities<Schedule> scheduleUtils

    def setup() {
        scheduleUtils = TestUtilities.create(scheduleDao, Schedule)
    }

    @Autowired
    def ScheduleObjectifyDao scheduleDao

    def "should save a schedule" () {
        when:"a schedule is saved"
        def dateString = "10th May 2010"
        def savedSchedule = scheduleUtils.createAndSaveEntityWithName(dateString)

        then:"the schedule should be persisted"
        def returndSchedule = scheduleDao.getByName(dateString)
        savedSchedule.getId() == returndSchedule.getId()
        savedSchedule.getName().equals(returndSchedule.getName())
    }

    def "should retrieve all schedules" () {
        given:"a number of schedules are saved"
        def schedule1 = scheduleUtils.createAndSaveEntityWithName("15th May 2010")
        def schedule2 = scheduleUtils.createAndSaveEntityWithName("16th May 2010")
        def schedule3 = scheduleUtils.createAndSaveEntityWithName("17th May 2010")

        when:"all schedules are retrieved"
        def allSchedules = scheduleDao.getAll()

        then:"all saved schedules are returned"
        allSchedules.size() == 3
        allSchedules.contains(schedule1)
        allSchedules.contains(schedule2)
        allSchedules.contains(schedule3)
    }



    def "should not allow duplicate schedule names to be persisted" () {
        given:"an existing schedule is persisted"
        scheduleUtils.createAndSaveEntityWithName("27th May 2010")

        when:"a schedule with a duplicate name is saved"
        scheduleUtils.createAndSaveEntityWithName("27th May 2010")

        then:"an error is raised"
        thrown(DaoException)
    }
}
