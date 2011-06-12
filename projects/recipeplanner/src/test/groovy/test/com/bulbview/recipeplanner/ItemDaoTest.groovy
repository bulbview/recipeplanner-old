package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired

import com.bulbview.recipeplanner.datamodel.Item
import com.bulbview.recipeplanner.datamodel.ItemCategory
import com.bulbview.recipeplanner.persistence.EntityDao
import com.bulbview.recipeplanner.persistence.ItemObjectifyDao



class ItemDaoTest extends DaoTestFixture {

    @Autowired
    private ItemObjectifyDao itemDao

    @Autowired
    private EntityDao<ItemCategory> categoryDao

    def item(name) {
        def Item item = new Item()
        item.setName(name)
        return item
    }

    def "should retrieve all persisted items" () {
        given:"categories are persisted"
        def expectedItems = [
            item("item1"),
            item("item2")
        ]

        for(item in expectedItems) {
            itemDao.save(item)
        }

        when:"all the recipes are retrieved from persistence"
        savedEntities = itemDao.getAll()

        then:"all items are retrieved"
        entitesArePersisted(expectedItems)
    }

    def "should return the saved item on save" () {
        when:""
        def itemToSave = item("item123")
        def saveditem = itemDao.save(itemToSave)

        then:""
        saveditem != null
        saveditem.equals(itemToSave)
    }

    def "should persist items under a category" () {
        given:"An existing category"
        def category = new ItemCategory()
        category.setName("Meat")
        categoryDao.save(category)

        and: "an associated number of items are persisted under the category"
        def Item item1 = item("pork")
        def Item item2= item("chicken")
        def Item item3 = item("beef")
        item1.setCategory(category)
        item2.setCategory(category)
        item3.setCategory(category)
        itemDao.save(item1)
        itemDao.save(item2)
        itemDao.save(item3)

        when:"the items for the category are retrieved"
        def  items = itemDao.getAllFor(category)

        then:"the saved items are returned"
        items.size() == 3
        items.containsAll([item1, item2, item3])
    }
}
