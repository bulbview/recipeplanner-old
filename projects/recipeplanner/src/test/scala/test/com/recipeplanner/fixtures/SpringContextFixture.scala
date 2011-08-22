package test.com.recipeplanner.fixtures
import org.springframework.test.context.ContextConfiguration
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.springframework.test.context.TestContextManager
import org.scalatest.FeatureSpec
import org.scalatest.BeforeAndAfterEach
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode

@ContextConfiguration(locations =
    Array("classpath:applicationContext.xml", "classpath:itest-persistenceContext.xml"))
@RunWith(classOf[JUnitRunner])
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class SpringContextFixture extends FeatureSpec with BeforeAndAfterEach {

    val dirtyTestContextManager = new DirtyTestContextManager(this.getClass())

    override def beforeEach() {
        dirtyTestContextManager.prepareTestInstance(this)
        dirtyTestContextManager.dirty()
    }

}
