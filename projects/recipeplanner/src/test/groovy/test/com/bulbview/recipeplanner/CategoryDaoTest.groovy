package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired

import com.bulbview.recipeplanner.datamodel.ItemCategory
import com.bulbview.recipeplanner.persistence.JdoDao

class CategoryDaoTest extends DaoTestFixture {

    @Autowired
    private JdoDao<ItemCategory> categoryDao


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
        def ItemCategory retrievedCategory = categoryDao.get(categoryName)

        then:"the category is retreived"
        retrievedCategory != null
        retrievedCategory.equals(savedCategory)
    }

    def category(name) {
        def ItemCategory category = new ItemCategory()
        category.setName(name)
        return category
    }
}