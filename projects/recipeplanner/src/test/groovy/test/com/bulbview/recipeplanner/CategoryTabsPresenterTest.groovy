package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired

import test.com.bulbview.recipeplanner.dao.SpringContextTestFixture

import com.bulbview.recipeplanner.datamodel.ItemCategory
import com.bulbview.recipeplanner.persistence.EntityDao;
import com.bulbview.recipeplanner.ui.manager.CategoriesAccordionDecorator
import com.bulbview.recipeplanner.ui.manager.CategoryEditorView
import com.bulbview.recipeplanner.ui.presenter.CategoriesViewFactory
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
    def CategoriesViewFactory mockCategoriesViewFactory
    def CategoriesAccordionDecorator mockMainWindowCategoryAccordion
    def CategoriesAccordionDecorator mockRecipeEditorCategoryAccordion
    def CategoryEditorView mockCategoryWindow
    def Window mockRootWindow
    @Autowired
    def EntityDao<ItemCategory> categoryDao
    
    def setup() {
        localServiceTestHelper.setUp()
        mockCategoryWindow = Mock()
        mockRootWindow = Mock()
        mockCategoriesViewFactory = Mock()
        mockMainWindowCategoryAccordion = Mock()
        mockRecipeEditorCategoryAccordion = Mock()
        
        presenter.setCategoriesViewFactory(mockCategoriesViewFactory)
        presenter.setCategoryEditorWindow(mockCategoryWindow)
        presenter.setRootWindow(mockRootWindow)
        
        mockCategoriesViewFactory.createCategoriesView(_) >>> [
            mockMainWindowCategoryAccordion,
            mockRecipeEditorCategoryAccordion
        ]
    }
    
    def "should create a new category tab on save of a category" () {
        given:"item category with a name"
        def name = "new cat"
        def itemCategory = itemCategoryWithName(name)
        presenter.init()
        
        
        when:"the category is saved"
        presenter.saveCategory(itemCategory)
        
        then:"a new category tab is created with the same name"
        1 * mockMainWindowCategoryAccordion.addCategoryTab(name, _)
        1 * mockRecipeEditorCategoryAccordion.addCategoryTab(name, _)
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
        presenter.init()
        
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
    
    def "should retrieve and display all categories in categories list on startup" () {
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
        1 * mockMainWindowCategoryAccordion.addCategoryTab(name1,_)
        1 * mockMainWindowCategoryAccordion.addCategoryTab(name2,_)
        1 * mockRecipeEditorCategoryAccordion.addCategoryTab(name1,_)
        1 * mockRecipeEditorCategoryAccordion.addCategoryTab(name2,_)
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
