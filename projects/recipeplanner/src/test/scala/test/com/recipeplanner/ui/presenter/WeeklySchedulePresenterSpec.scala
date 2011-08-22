package test.com.recipeplanner.ui.presenter

import org.scalatest.FeatureSpec
import org.scalatest.GivenWhenThen
import com.bulbview.recipeplanner.datamodel.schedule.Schedule
import com.bulbview.recipeplanner.ui.presenter.WeeklySchedulePresenter
import org.scalatest.BeforeAndAfterEach
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.util.Date
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestContextManager
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.scalatest.matchers.ShouldMatchers
import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao
import org.mockito.Mockito
import java.text.DateFormat
import java.text.SimpleDateFormat
import com.bulbview.recipeplanner.datamodel.schedule.Section
import com.bulbview.recipeplanner.ui.presenter.DailySchedulePresenter
import org.mockito.ArgumentCaptor
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.context.ApplicationContext
import org.springframework.beans.factory.ObjectFactory
import com.bulbview.recipeplanner.ui.presenter.DailySchedulePresenterFactory
import org.mockito.BDDMockito
import scala.collection.JavaConversions._
import com.bulbview.recipeplanner.ui.manager.WeeklyScheduleView
import org.mockito.Matchers._
import com.bulbview.recipeplanner.datamodel.Item
import com.bulbview.recipeplanner.persistence.EntityDao
import com.bulbview.recipeplanner.datamodel.schedule.Schedule
import com.bulbview.recipeplanner.ui.DailyScheduleView
import com.bulbview.recipeplanner.datamodel.schedule.InnerSection
import com.bulbview.recipeplanner.persistence.EntityNameDao
import test.com.recipeplanner.fixtures.SpringContextFixture

class WeeklySchedulePresenterSpec extends SpringContextFixture with GivenWhenThen with MockitoSugar with ShouldMatchers {

    @Autowired var presenter: WeeklySchedulePresenter = null
    @Autowired var localServiceTestHelper: LocalServiceTestHelper = null
    @Autowired var scheduleDao: ScheduleObjectifyDao = null
    @Autowired var itemDao: EntityNameDao[Item] = null
    @Autowired var applicationContext: ApplicationContext = null
    var startDate: Date = _
    var sections: java.util.Collection[InnerSection] = _
    @Autowired
    var dateFormat: SimpleDateFormat = null

    override def beforeEach() {
        super.beforeEach()
        localServiceTestHelper.setUp()
    }

    feature("User should be able to select the schedule start date") {

        scenario("should update the schedule's start date if schedule is transient ") {

            when("the start date is selected")
            presenter.init()
            startDate = dateFormat.parse("Thu, May 28, 2011")
            presenter.setStartDate(startDate)

            then("the schedule is updated with a start date")
            presenter.getDisplayedSchedule().getStartDate() should be === (startDate)
        }

        scenario("should display the schedule's items in the weekly schedule view") {

            presenter.init()
            val mockDailyView = getMockDailyView()

            given("a persisted schedule with a section")
            val startDate = dateFormat.parse("Mon, March 28, 2011")
            val item = new Item()
            item.setName("ginger")
            itemDao.save(item)
            val schedule = new Schedule()
            val dateSection = new InnerSection()
            dateSection.setDateFormatter(dateFormat)
            schedule.addDateSection(dateSection)
            dateSection.addItem(item)
            schedule.setStartDate(startDate)
            val ret = scheduleDao.save(schedule)

            when("the schedule's start date is selected in the view")
            presenter.setStartDate(startDate)

            then("the section's items are displayed in the view")
            Mockito.verify(mockDailyView).addListItem(item)
        }

        scenario("should display a given schedule in the shopping list") {

        }

        scenario("should display persisted schedule if start date correlates to schedule date") {

            given("a persisted schedule with start date")
            startDate = new Date()
            val persistedSchedule = new Schedule()
            scheduleDao.save(persistedSchedule)

            when("the start date is selected")
            presenter.init()
            presenter.setStartDate(startDate)
            then("the schedule is updated")
            presenter.getDisplayedSchedule() should equal(persistedSchedule)
        }

        scenario("should display date header in week's daily tabs ") {

            given("the presenter is initialised and there is no existing schedule")
            noConcreteViewInteraction()
            presenter.init()
            val mockDailyScheduleView = getMockDailyView()

            when("a start date is set")
            val startDate = dateFormat.parse("Tue, Jun 3, 2003")
            presenter.setStartDate(startDate)

            then("8 schedule tabs are created")
            presenter.getDailySchedulePresenters() should have size (8)

            and("incremental Date is set for each section")
            verify(mockDailyScheduleView).setHeader("Tue, Jun 3, 2003")
            verify(mockDailyScheduleView).setHeader("Wed, Jun 4, 2003")
            verify(mockDailyScheduleView).setHeader("Thu, Jun 5, 2003")
            verify(mockDailyScheduleView).setHeader("Fri, Jun 6, 2003")
            verify(mockDailyScheduleView).setHeader("Sat, Jun 7, 2003")
            verify(mockDailyScheduleView).setHeader("Sun, Jun 8, 2003")
            verify(mockDailyScheduleView).setHeader("Mon, Jun 9, 2003")
        }

        def getMockDailyView(): DailyScheduleView = {
            val mockDailyScheduleView = mock[DailyScheduleView]
            presenter.getDailySchedulePresenters().foreach(dailySchedulePresenter => dailySchedulePresenter.setView(mockDailyScheduleView))
            mockDailyScheduleView
        }

    }
    def noConcreteViewInteraction() {
        presenter.setView(mock[WeeklyScheduleView])
    }
}