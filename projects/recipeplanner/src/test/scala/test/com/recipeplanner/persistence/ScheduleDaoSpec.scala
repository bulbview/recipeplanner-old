package test.com.recipeplanner.persistence
import org.scalatest.GivenWhenThen
import org.scalatest.matchers.ShouldMatchers
import com.bulbview.recipeplanner.datamodel.schedule.Schedule
import java.util.Date
import com.bulbview.recipeplanner.datamodel.schedule.InnerSection
import java.util.Calendar
import com.bulbview.recipeplanner.persistence.EntityDao
import org.springframework.beans.factory.annotation.Autowired
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import com.bulbview.recipeplanner.datamodel.Item
import org.springframework.beans.factory.ObjectFactory
import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao
import java.text.SimpleDateFormat
import com.googlecode.objectify.Key
import test.com.recipeplanner.fixtures.SpringContextFixture

class ScheduleDaoRetrieveSpec extends ScheduleDaoSpec {

    feature("retrieve persisted schedules") {

        scenario("should retrieve schedules by their start date") {
            
            given("a schedule with a start date is saved ")
            val transientSchedule = createSchedule()
            val startDate = dateFormatter.parse("Sat, May 15, 2010")
            transientSchedule.setStartDate(startDate)
            val persistedSchedule = scheduleDao.save(transientSchedule)
        
            when("the schedule is retrieved by its start date") 
                val retrievedSchedule = scheduleDao.getByDate(startDate)
                
                then("the schedule should be retrieved")
                retrievedSchedule should not equal (null)
                retrievedSchedule.getId() should be (persistedSchedule.getId())
        }
        
         scenario("should retrieve all schedules") {
            given("a number of schedules are saved")

            val schedule1 = saveSchedule(new Date(110, 04, 15))
            val schedule2 = saveSchedule(new Date(110, 04, 16))
            val schedule3 = saveSchedule(new Date(110, 04, 17))

            when("all schedules are retrieved")
            val allSchedules = scheduleDao.getAll()

            then("all saved schedules are returned")
            allSchedules should contain(schedule1)
            allSchedules should contain(schedule2)
            allSchedules should contain(schedule3)
        }
    }
}

class ScheduleDaoSaveSpec extends ScheduleDaoSpec {

    def addDate(date: Date, days: Integer): Date = {
        val calendar = Calendar.getInstance
        calendar.add(Calendar.DAY_OF_YEAR, days)
        calendar.getTime()
    }

    feature("save a schedule so the schedule can be retrieved between sessions") {
        
        scenario("should save a schedule") {
            given("a schedule")
             val savedSchedule = createSchedule()
             when("the schedule is saved")
             val ret = scheduleDao.save(savedSchedule)
             then("")
             val retrieveSchedule = scheduleDao.get(new Key[Schedule](classOf[Schedule], ret.getId()))
             retrieveSchedule should not be (null)
            retrieveSchedule.getId() should equal (ret.getId())
        }

        scenario("should save a schedule's constituent number of days") {
            given("a schedule with 3 days")
            val savedSchedule = createSchedule()
            val date = new Date(110, 04, 10)
            savedSchedule.setStartDate(date)
            savedSchedule.addDateSection(createDateSection(date))
            savedSchedule.addDateSection(createDateSection(addDate(date, 1)))
            savedSchedule.addDateSection(createDateSection(addDate(date, 2)))

            when("the schedule is saved")
            val ret = scheduleDao.save(savedSchedule)

            then("the 3 constituent days are persisted")
            val returnedSchedule = scheduleDao.getByDate(date)
            returnedSchedule should not be (null)
            savedSchedule.getId() should be(returnedSchedule.getId())
            savedSchedule.getStartDate() should be(returnedSchedule.getStartDate())
            returnedSchedule.getDateSections().size() should be(3)
        }

        scenario("should save a schedule's constituent day attributes") {

            given("a schedule ")
            val savedSchedule = createSchedule()
            val startDate = dateFormatter.parse("Fri, May 15, 2009")
            savedSchedule.setStartDate(startDate)

            and("the schedule has a date section ")
            val section = createInnerSection()
            val date = new Date()
            section setDate date
            savedSchedule.addDateSection(section)

            and("the day has 2 items")
            val eggs = createAndSave("Eggs")
            val milk = createAndSave("Milk")
            section.addItem(eggs)
            section.addItem(milk)

            when("the schedule is saved")
            scheduleDao.save(savedSchedule)

            then("the day has its date persisted ")
            val returnedSchedule = scheduleDao.getByDate(startDate)
            returnedSchedule should not be (null)
            val savedDays = returnedSchedule.getDateSections()
            savedDays.size() should be(1)
            val savedDay = savedDays.iterator().next()
            savedDay.getDate().equals(date)

            then("the day has its items persisted")
            savedDay.getItems().size() should be(2)
            val iterator = savedDay.getItems().iterator()
            iterator.next().getId() should equal(eggs.getId())
            iterator.next().getId() should equal(milk.getId())
        }
    }

    def createDateSection(date: Date): InnerSection = {
        var section = createInnerSection()
        section.setDate(date)
        section
    }

    def createAndSave(name: String): Item = {
        val item = new Item
        item setName name
        itemDao.save(item)
    }

    def createInnerSection(): InnerSection = {
        innerSectionFactory getObject
    }

}

class ScheduleDaoSpec extends SpringContextFixture with GivenWhenThen with ShouldMatchers {

    @Autowired val scheduleDao: ScheduleObjectifyDao = null
    @Autowired val itemDao: EntityDao[Item] = null
    @Autowired val localServiceTestHelper: LocalServiceTestHelper = null
    @Autowired val innerSectionFactory: ObjectFactory[InnerSection] = null
    @Autowired val scheduleFactory: ObjectFactory[Schedule] = null
    @Autowired val dateFormatter: SimpleDateFormat = null

    override def beforeEach() {
        super.beforeEach()
        localServiceTestHelper.setUp()
    }

    def createSchedule(): Schedule = {
        scheduleFactory getObject
    }
    
    def saveSchedule(date: Date): Schedule = {
        val savedSchedule = createSchedule()
        savedSchedule setStartDate date
        scheduleDao save savedSchedule
    }
}