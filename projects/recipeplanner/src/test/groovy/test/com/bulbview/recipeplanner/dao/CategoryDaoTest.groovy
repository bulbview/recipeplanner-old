package test.com.bulbview.recipeplanner.dao

import org.springframework.beans.factory.annotation.Autowired

import com.bulbview.recipeplanner.datamodel.ItemCategory
import com.bulbview.recipeplanner.persistence.DaoException
import com.bulbview.recipeplanner.persistence.EntityDao

class CategoryDaoTest extends SpringContextTestFixture {

    def TestUtilities<ItemCategory> categoryTestUtils

    @Autowired
    private EntityDao<ItemCategory> categoryDao


    def "should raise an error when attempting to  persist of duplicate category names" () {
        given:"An existing category with a name is saved"
        def fish = "Fish"
        categoryTestUtils.createAndSaveEntityWithName(fish);
        when:"a category with the same name is saved"
        categoryTestUtils.createAndSaveEntityWithName(fish);

        then:"an error is thrown"
        thrown(DaoException)
    }


    def "should retrieve all persisted categories" () {
        given:"categories are persisted"
        def expectedCategories = [
            category("category1"),
            category("category2")
        ]

        for(category in expectedCategories) {
            categoryDao.save(category)
        }

        when:"all the recipes are retrieved from persistence"
        savedEntities = categoryDao.getAll()

        then:"all categories are retrieved"
        entitesArePersisted(expectedCategories)
    }

    def "should retrieve a category by name" () {
        given:"a category is saved"
        def categoryName = "categorySave"
        def savedCategory = category(categoryName)
        categoryDao.save(savedCategory)

        when:"the category is retrieved by name"
        def ItemCategory retrievedCategory = categoryDao.getByName(categoryName)

        then:"the category is retreived"
        retrievedCategory.equals(savedCategory)
    }

    def "should return the saved category on save" () {
        when:""
        def categoryToSave = category("category123")
        def savedCategory = categoryDao.save(categoryToSave)

        then:""
        savedCategory != null
        savedCategory.equals(categoryToSave)
    }

    def category(name) {
        def ItemCategory category = new ItemCategory()
        category.setName(name)
        return category
    }

    def setup() {
        categoryTestUtils = TestUtilities.create(categoryDao, ItemCategory)
    }
}