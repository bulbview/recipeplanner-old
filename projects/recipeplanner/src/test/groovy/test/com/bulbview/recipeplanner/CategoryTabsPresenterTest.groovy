package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired

import test.com.bulbview.recipeplanner.dao.SpringContextTestFixture

import com.bulbview.recipeplanner.datamodel.ItemCategory
import com.bulbview.recipeplanner.persistence.EntityDao
import com.bulbview.recipeplanner.ui.manager.CategoryAccordion
import com.bulbview.recipeplanner.ui.manager.CategoryEditorView
import com.bulbview.recipeplanner.ui.presenter.CategoryTabsPresenter
import com.bulbview.recipeplanner.ui.presenter.EntityValidationException
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import com.vaadin.ui.Window


class CategoryTabsPresenterTest extends SpringContextTestFixture {
    //presenter integration test mocking the view only
    //
    @Autowired
    private  LocalServiceTestHelper localServiceTestHelper
    
    @Autowired
    def CategoryTabsPresenter presenter
    
    def Collection<CategoryAccordion> mockCategoryAccordions
    def CategoryAccordion mockCategoryAccordion
    def CategoryEditorView mockCategoryWindow
    def Window mockRootWindow
    @Autowired
    def EntityDao<ItemCategory> categoryDao
    
    def setup() {
        localServiceTestHelper.setUp()
        mockCategoryWindow = Mock()
        mockRootWindow = Mock()
        mockCategoryAccordion = Mock()
        mockCategoryAccordions = [mockCategoryAccordion]
        
        presenter.setCategoryAccordions(mockCategoryAccordions)
        presenter.setCategoryEditorWindow(mockCategoryWindow)
        presenter.setRootWindow(mockRootWindow)
    }
    
    def "on save of a category should create a new category tab" () {
        given:"item category with a name"
        def name = "new cat"
        def itemCategory = itemCategoryWithName(name)
        
        
        when:"the category is saved"
        presenter.saveCategory(itemCategory)
        
        then:"a new category tab is created with the same name"
        1 * mockCategoryAccordion.addCategoryTab(name)
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
        1* mockRootWindow.removeWindow(_)
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
        1 * mockCategoryAccordion.addCategoryTab(name1)
        1 * mockCategoryAccordion.addCategoryTab(name2)
    }
    
    def "should throw exception if saving category with null name" () {
        given:
        def nullNameItemCategory = itemCategoryWithName(null)
        
        when:""
        presenter.saveCategory(nullNameItemCategory)
        
        then:""
        thrown(EntityValidationException)
    }
}
