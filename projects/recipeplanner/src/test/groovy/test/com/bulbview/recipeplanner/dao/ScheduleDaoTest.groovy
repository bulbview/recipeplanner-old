package test.com.bulbview.recipeplanner.dao

import org.springframework.beans.factory.annotation.Autowired

import com.bulbview.recipeplanner.datamodel.Day
import com.bulbview.recipeplanner.datamodel.Item
import com.bulbview.recipeplanner.datamodel.Schedule
import com.bulbview.recipeplanner.persistence.DaoException
import com.bulbview.recipeplanner.persistence.ItemObjectifyDao
import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao


class ScheduleDaoTest extends DaoTestFixture {

    def TestUtilities<Schedule> scheduleUtils
    def TestUtilities<Item> itemUtils
    @Autowired
    def ScheduleObjectifyDao scheduleDao
    @Autowired
    def ItemObjectifyDao itemDao

    def setup() {
        scheduleUtils = TestUtilities.create(scheduleDao, Schedule)
        itemUtils = TestUtilities.create(itemDao, Item)
    }


    def "should save a schedule" () {
        when:"a schedule is saved"
        def dateString = "10th May 2010"
        def savedSchedule = new Schedule()
        savedSchedule.setName(dateString)
        scheduleDao.save(savedSchedule)

        then:"the schedule should be persisted"
        def returnedSchedule = scheduleDao.getByName(dateString)
        returnedSchedule != null
        savedSchedule.getId() == returnedSchedule.getId()
        savedSchedule.getName().equals(returnedSchedule.getName())
    }

    def "should save a schedule's constituent number of days" () {
        given:"a schedule with 3 days"
        def dateString = "10th May 2010"
        def savedSchedule = new Schedule()
        savedSchedule.setName(dateString)
        savedSchedule.addDay(new Day())
        savedSchedule.addDay(new Day())
        savedSchedule.addDay(new Day())

        when:"the schedule is saved"
        scheduleDao.save(savedSchedule)

        then:"the 3 constituent days are persisted"
        def returnedSchedule = scheduleDao.getByName(dateString)
        returnedSchedule != null
        savedSchedule.getId() == returnedSchedule.getId()
        savedSchedule.getName().equals(returnedSchedule.getName())
        savedSchedule.getDays().size() == 3
    }

    def "should save a schedule's constituent day" () {
        given:"a schedule "
        def dateString = "12th May 2011"
        def savedSchedule = new Schedule()
        savedSchedule.setName(dateString)
        and:"the schedule has a day "
        def day = new Day()
        savedSchedule.addDay(day)

        and:"the day has a date"
        def date = new Date()
        day.setDate(date)

        and:"the day has 2 items"
        def eggs = itemUtils.createAndSaveEntityWithName("Eggs")
        def milk = itemUtils.createAndSaveEntityWithName("Milk")
        day.addItem(eggs)
        day.addItem(milk)

        when:"the schedule is saved"
        scheduleDao.save(savedSchedule)

        then:"the day has its date persisted "
        def returnedSchedule = scheduleDao.getByName(dateString)
        returnedSchedule != null
        def savedDays = savedSchedule.getDays()
        savedDays.size() == 1
        def Day savedDay = savedDays.iterator().next()
        savedDay.getDate().equals(date)

        then:"the day has its items persisted"
        savedDay.getItems().size() == 2
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
