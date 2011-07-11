package test.com.bulbview.recipeplanner.dao

import org.springframework.beans.factory.annotation.Autowired

import com.bulbview.recipeplanner.datamodel.Item
import com.bulbview.recipeplanner.datamodel.schedule.DateSection
import com.bulbview.recipeplanner.datamodel.schedule.Schedule
import com.bulbview.recipeplanner.persistence.DaoException
import com.bulbview.recipeplanner.persistence.ItemObjectifyDao
import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao


class ScheduleDaoTest extends SpringContextTestFixture {
    
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
        def dateString = "May 10, 2010"
        def savedSchedule = new Schedule()
        savedSchedule.setStartDate(new Date(110,04,10))
        scheduleDao.save(savedSchedule)
        
        then:"the schedule should be persisted"
        def returnedSchedule = scheduleDao.getByName(dateString)
        returnedSchedule != null
        savedSchedule.getId() == returnedSchedule.getId()
        savedSchedule.getName().equals(returnedSchedule.getName())
    }
    
    def "should save a schedule's constituent number of days" () {
        given:"a schedule with 3 days"
        def dateString = "May 10, 2010"
        def savedSchedule = new Schedule()
        savedSchedule.setStartDate(new Date(110,04,10))
        savedSchedule.addSection(new DateSection())
        savedSchedule.addSection(new DateSection())
        savedSchedule.addSection(new DateSection())
        
        when:"the schedule is saved"
        scheduleDao.save(savedSchedule)
        
        then:"the 3 constituent days are persisted"
        def returnedSchedule = scheduleDao.getByName(dateString)
        returnedSchedule != null
        savedSchedule.getId() == returnedSchedule.getId()
        savedSchedule.getName().equals(returnedSchedule.getName())
        savedSchedule.getSections().size() == 3
    }
    
    def "should save a schedule's constituent day attributes" () {
        given:"a schedule "
        def dateString = "May 12, 2011"
        def savedSchedule = new Schedule()
        savedSchedule.setStartDate(new Date(111,04,12))
        and:"the schedule has a day "
        def day = new DateSection()
        savedSchedule.addSection(day)
        
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
        def savedDays = savedSchedule.getSections()
        savedDays.size() == 1
        def DateSection savedDay = savedDays.iterator().next()
        savedDay.getDate().equals(date)
        
        then:"the day has its items persisted"
        savedDay.getItems().size() == 2
        def iterator = savedDay.getItems().iterator()
        iterator.next().getId() == eggs.getId()
        iterator.next().getId() == milk.getId()
    }
    
    
    def "should retrieve all schedules" () {
        given:"a number of schedules are saved"
        
        def schedule1 = saveSchedule(new Date(110,04,15))
        def schedule2 = saveSchedule(new Date(110,04,16))
        def schedule3 = saveSchedule(new Date(110,04,17))
        
        when:"all schedules are retrieved"
        def allSchedules = scheduleDao.getAll()
        
        then:"all saved schedules are returned"
        allSchedules == [
            schedule1,
            schedule2,
            schedule3
        ]
    }
    
    def Schedule saveSchedule(Date date) {
        def savedSchedule = new Schedule()
        savedSchedule.setStartDate(date)
        scheduleDao.save(savedSchedule)
        return savedSchedule
    }
    
    
    
    def "should not allow duplicate schedule names to be persisted" () {
        given:"an existing schedule is persisted"
        saveSchedule(new Date(110, 04, 27))
        
        when:"a schedule with a duplicate name is saved"
        saveSchedule(new Date(110, 04, 27))
        
        then:"an error is raised"
        thrown(DaoException)
    }
}
