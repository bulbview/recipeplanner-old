package test.com.bulbview.recipeplanner.dao

import java.util.List

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.google.appengine.tools.development.testing.LocalServiceTestHelper

@ContextConfiguration(locations=[
    "classpath:applicationContext.xml", "classpath:itest-persistenceContext.xml"
])
class DaoTestFixture extends Specification {

    def savedEntities

    @Autowired
    private  LocalServiceTestHelper localServiceTestHelper

    def setup() {
        localServiceTestHelper.setUp()
    }



    def entitesArePersisted(List expectedEntities) {
        assert savedEntities.size() == expectedEntities.size()
        for(entity in expectedEntities) {
            assert savedEntities.contains(entity)
        }
        return true
    }
}