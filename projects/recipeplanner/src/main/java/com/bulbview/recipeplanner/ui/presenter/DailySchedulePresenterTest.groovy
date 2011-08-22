package com.bulbview.recipeplanner.ui.presenter

import org.springframework.beans.factory.annotation.Autowired

import test.com.bulbview.recipeplanner.dao.SpringContextTestFixture

import com.bulbview.recipeplanner.datamodel.Item
import com.bulbview.recipeplanner.datamodel.ItemCategory
import com.bulbview.recipeplanner.datamodel.schedule.Section
import com.bulbview.recipeplanner.persistence.EntityNameDao
import com.bulbview.recipeplanner.ui.DailyScheduleView
import com.googlecode.objectify.Key


class DailySchedulePresenterTest extends SpringContextTestFixture {
    @Autowired
    def DailySchedulePresenter presenter
    def Section mockSection
    private DailyScheduleView mockDailyScheduleView
    def ShoppingListPresenter mockShoppingListPresenter
    def Item item
    @Autowired
    def EntityNameDao<Item> itemDao
    @Autowired
    def EntityNameDao<ItemCategory> categoryDao

    def setup() {
        mockSection = Mock()
        mockDailyScheduleView = Mock()
        mockShoppingListPresenter = Mock()
        presenter.setSection(mockSection)
        presenter.setView(mockDailyScheduleView)
        presenter.setShoppingListPresenter(mockShoppingListPresenter)
        item = new Item()
    }

    def "should add item to the presenter's associated section model when item is dragged to schedule" () {
        when:""
        presenter.dragAndDrop(item)
        then:""
        1 * mockSection.addItem(item)
    }

    def "should add item to shopping list when item is dragged into schedule" () {
        when:""
        presenter.dragAndDrop(item)
        then:""
        1 * mockShoppingListPresenter.addItem(item)
    }

    def "should add item to view when item is dragged into schedule" () {
        when:""
        presenter.dragAndDrop(item)
        then:""
        1 * mockDailyScheduleView.addListItem(item)
    }

    def"should load items from the existing section"() {
        given:
        def cat = new ItemCategory()
        cat.setName("general cat")
        categoryDao.save(cat)
        def createItem={def i=new Item(); i.setName(it); i.setCategory(cat); itemDao.save(i); return i }
        def beef = createItem("beef")
        def cheese = createItem("cheese")
        def milk = createItem("milk")
        and:
        presenter.setSection(mockSection)
        when:
        presenter.loadItemsFromSection()
        then:

        1 * mockSection.getItems() >> [
            new Key<Item>(Item, beef.id),
            new Key<Item>(Item, cheese.id),
            new Key<Item>(Item, milk.id)
        ]
        1 * mockDailyScheduleView.addListItem(beef)
        1 * mockDailyScheduleView.addListItem(cheese)
        1 * mockDailyScheduleView.addListItem(milk)
    }
}
