package test.com.recipeplanner.datamodel.factory
import org.scalatest.FeatureSpec
import org.scalatest.GivenWhenThen
import com.bulbview.recipeplanner.datamodel.factory.ScheduleFactory
import java.util.Date
import org.scalatest.BeforeAndAfterEach
import com.bulbview.recipeplanner.datamodel.schedule.Section
import scala.collection.JavaConversions._
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import test.com.recipeplanner.fixtures.SpringContextFixture
import org.springframework.test.context.TestContextManager
import org.springframework.beans.factory.annotation.Autowired
import com.bulbview.recipeplanner.datamodel.schedule.Schedule
import com.bulbview.recipeplanner.datamodel.schedule.InnerSection

class ScheduleFactorySpec extends SpringContextFixture with GivenWhenThen with ShouldMatchers {

    @Autowired val scheduleFactory: ScheduleFactory = null
    var sections: java.util.Collection[InnerSection] = null

    feature("A schedule should contain date sections for a week and a miscellaneous section") {

        scenario("should add  misc section to schedule") {

            when("")
            val schedule = scheduleFactory.create(new Date())
            then("")
            sections = schedule.getStringSections()
            sections should have size (1)
            val stringSection = sections.iterator().next()
            stringSection.getName() should be("Miscellaneous Items")
        }

        scenario("should add  date sections for a week to schedule") {

            when("")
            val schedule = scheduleFactory.create(new Date())

            then("")
            val dateSections = schedule.getDateSections()
            println(dateSections)
            dateSections should have size (7)
        }
    }
}