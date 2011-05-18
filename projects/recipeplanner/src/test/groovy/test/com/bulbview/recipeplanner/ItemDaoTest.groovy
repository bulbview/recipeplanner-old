package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired

import com.bulbview.recipeplanner.datamodel.Item
import com.bulbview.recipeplanner.persistence.ObjectifyDao



class ItemDaoTest extends DaoTestFixture {

    @Autowired
    private ObjectifyDao<Item> itemDao

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
}
