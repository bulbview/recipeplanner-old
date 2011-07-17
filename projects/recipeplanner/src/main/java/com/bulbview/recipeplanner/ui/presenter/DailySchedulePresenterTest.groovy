package com.bulbview.recipeplanner.ui.presenter

import org.springframework.beans.factory.annotation.Autowired

import test.com.bulbview.recipeplanner.dao.SpringContextTestFixture

import com.bulbview.recipeplanner.datamodel.Item
import com.bulbview.recipeplanner.datamodel.schedule.Section
import com.bulbview.recipeplanner.ui.DailyScheduleView


class DailySchedulePresenterTest extends SpringContextTestFixture {
    @Autowired
    def DailySchedulePresenter presenter
    def Section mockSection
    private DailyScheduleView mockDailyScheduleView
    def ShoppingListPresenter mockShoppingListPresenter
    def Item item
    
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
}
