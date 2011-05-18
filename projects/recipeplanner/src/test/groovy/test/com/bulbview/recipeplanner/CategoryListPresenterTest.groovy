package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.bulbview.recipeplanner.datamodel.Item
import com.bulbview.recipeplanner.persistence.ObjectifyDao
import com.bulbview.recipeplanner.ui.helper.CategorisedItemList
import com.bulbview.recipeplanner.ui.presenter.CategoryListPresenter
import com.google.appengine.tools.development.testing.LocalServiceTestHelper

@ContextConfiguration(locations=[
    "classpath:applicationContext.xml", "classpath:itest-persistenceContext.xml"
])
class CategoryListPresenterTest extends Specification {


    @Autowired
    def ObjectifyDao<Item> itemDao
    @Autowired
    def CategoryListPresenter categoryListPresenter
    def CategorisedItemList mockCategorisedItemList
    @Autowired
    private  LocalServiceTestHelper localServiceTestHelper


    //    def "should display all items for a category on startup" () {
    //        given:"there are a number of persisted items"
    //        saveItem("milk")
    //        saveItem("bread")
    //        saveItem("chocolate")
    //        when:"the presenter is initialised"
    //        categoryListPresenter.init()
    //        then:""
    //        3 * mockCategorisedItemList.addListItem(_)
    //    }

    private saveItem(name) {
        def item = new Item()
        item.setName(name)
        itemDao.save(item)
    }

    def "should save item under associated category" () {
        when:""

        then:""
    }

    def "should save new item and add to view" () {
        when:"an item is saved to an existing category"
        categoryListPresenter.addItem("cheese", "Dairy")
        def savedItem = itemDao.get("cheese")

        then:"a new item is saved"

        savedItem != null
        1 * mockCategorisedItemList.addListItem(_);
    }

    def setup() {
        localServiceTestHelper.setUp()
        mockCategorisedItemList =  Mock(CategorisedItemList)
        categoryListPresenter.setView(mockCategorisedItemList)
    }
}
