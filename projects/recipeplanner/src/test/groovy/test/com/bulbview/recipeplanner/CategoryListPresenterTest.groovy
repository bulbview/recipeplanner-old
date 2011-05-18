package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.bulbview.recipeplanner.datamodel.Item
import com.bulbview.recipeplanner.persistence.JdoDao
import com.bulbview.recipeplanner.ui.helper.CategorisedItemList
import com.bulbview.recipeplanner.ui.presenter.CategoryListPresenter
import com.google.appengine.tools.development.testing.LocalServiceTestHelper

@ContextConfiguration(locations=[
    "classpath:applicationContext.xml", "classpath:itest-persistenceContext.xml"
])
class CategoryListPresenterTest extends Specification {

    @Autowired
    def JdoDao<Item> itemDao
    @Autowired
    def CategoryListPresenter categoryListPresenter
    def CategorisedItemList mockCategorisedItemList
    @Autowired
    private  LocalServiceTestHelper localServiceTestHelper

    def setup() {
        localServiceTestHelper.setUp()
        mockCategorisedItemList =  Mock(CategorisedItemList)
        categoryListPresenter.setView(mockCategorisedItemList)
    }

    def "should retrieve all items for a category on startup" () {
        when:""

        then:""
    }

    def "should save new item and add to view" () {
        when:"an item is saved to an existing category"
        categoryListPresenter.addItem("cheese", "Dairy")
        def savedItem = itemDao.get("cheese")

        then:"a new item is saved"

        savedItem != null
        1* mockCategorisedItemList.addListItem(_);
    }
}
