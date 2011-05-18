package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.bulbview.recipeplanner.datamodel.ItemCategory
import com.bulbview.recipeplanner.persistence.ObjectifyDao
import com.bulbview.recipeplanner.ui.helper.CategoryEditor
import com.bulbview.recipeplanner.ui.helper.CategoryTabs
import com.bulbview.recipeplanner.ui.helper.MainWindowUiManager
import com.bulbview.recipeplanner.ui.presenter.CategoryTabsPresenter
import com.google.appengine.tools.development.testing.LocalServiceTestHelper

@ContextConfiguration(locations=[
    "classpath:applicationContext.xml", "classpath:itest-persistenceContext.xml"
])
class CategoryTabsPresenterTest extends Specification {
    //presenter integration test mocking the view only
    //
    @Autowired
    private  LocalServiceTestHelper localServiceTestHelper

    @Autowired
    def CategoryTabsPresenter presenter

    def CategoryTabs mockCategoryTabs
    def CategoryEditor mockCategoryWindow
    def MainWindowUiManager mockGeneratedWindow
    @Autowired
    def ObjectifyDao<ItemCategory> categoryDao

    def setup() {
        localServiceTestHelper.setUp()
        mockCategoryTabs = Mock(CategoryTabs)
        mockCategoryWindow = Mock(CategoryEditor)
        mockGeneratedWindow = Mock(MainWindowUiManager)

        presenter.setCategoryEditorWindow(mockCategoryWindow)
        presenter.setCategoryTabs(mockCategoryTabs)
        presenter.setMainWindow(mockGeneratedWindow)
    }

    def "on save of a category should create a new category tab" () {
        given:"item category with a name"
        def name = "new cat"
        def itemCategory = itemCategoryWithName(name)


        when:"the category is saved"
        presenter.saveCategory(itemCategory)

        then:"a new category tab is created with the same name"
        1 * mockCategoryTabs.addCategoryTab(name)
    }

    def ItemCategory itemCategoryWithName(String name) {
        def itemCategory = new ItemCategory()
        itemCategory.setName(name)
        return itemCategory
    }

    def "should close category window following a category save" () {

        given:"a new item category"
        def itemCategory = new ItemCategory()
        itemCategory.setName("new category")

        when:"a new category is saved"
        presenter.saveCategory(itemCategory)

        then:"the generated window is closed"
        1* mockGeneratedWindow.closeCategoryWindow()
    }

    def "should open category editor for a new category" () {
        when:""
        presenter.addCategoryMenuSelected()
        then:""
        1 * mockCategoryWindow.setItemCategory(_)
    }

    def "should display all categories in categories list" () {
        given:
        def name1 = "new cat1"
        def name2 = "new cat2"
        def itemCategory = itemCategoryWithName(name1)
        def itemCategory2 = itemCategoryWithName(name2)
        categoryDao.save(itemCategory)
        categoryDao.save(itemCategory2)
        when:"the application initiates"
        presenter.init()

        then:"the categories list is populated"
        1 * mockCategoryTabs.addCategoryTab(name1)
        1 * mockCategoryTabs.addCategoryTab(name2)
    }
}
