package test.com.bulbview.recipeplanner.presenter


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext

import test.com.bulbview.recipeplanner.dao.SpringContextTestFixture

import com.bulbview.recipeplanner.datamodel.schedule.InnerSection
import com.bulbview.recipeplanner.datamodel.schedule.Schedule
import com.bulbview.recipeplanner.datamodel.schedule.SectionFactory
import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao
import com.bulbview.recipeplanner.service.ScheduleService
import com.bulbview.recipeplanner.ui.manager.WeeklyScheduleView
import com.bulbview.recipeplanner.ui.presenter.DailySchedulePresenter
import com.bulbview.recipeplanner.ui.presenter.DailySchedulePresenterFactory
import com.bulbview.recipeplanner.ui.presenter.ScheduleHistoryPresenter
import com.bulbview.recipeplanner.ui.presenter.ShoppingListPresenter
import com.bulbview.recipeplanner.ui.presenter.WeeklySchedulePresenter


@DirtiesContext
class WeeklySchedulePresenterTest extends SpringContextTestFixture {
    
    static final String MISC_ITEMS = "Miscellaneous Items"
    
    def WeeklyScheduleView mockWeeklyScheduleView
    def DailySchedulePresenterFactory mockDailySchedulePresenterFactory
    def SectionFactory mockSectionFactory
    def ScheduleHistoryPresenter mockScheduleHistoryPresenter
    @Autowired
    def WeeklySchedulePresenter presenter
    def Schedule schedule
    def DailySchedulePresenter mockDailySchedulePresenter
    @Autowired
    def ScheduleObjectifyDao scheduleDao
    def InnerSection mockNamedSection
    def InnerSection mockDateSection
    def ShoppingListPresenter mockShoppingListPresenter
    @Autowired
    def ScheduleService scheduleService
    
    def Date startDate
    
    def setup() {
        schedule = new Schedule()
        createMocks()
        initPresenter()
        mockDailySchedulePresenterFactory.create() >> mockDailySchedulePresenter
    }
    
    private createMocks() {
        mockWeeklyScheduleView = Mock()
        mockDailySchedulePresenter = Mock()
        //        mockScheduleModel = Mock()
        mockDailySchedulePresenterFactory = Mock()
        mockSectionFactory = Mock()
        mockNamedSection = Mock()
        mockDateSection = Mock()
        mockScheduleHistoryPresenter = Mock()
        mockShoppingListPresenter = Mock()
    }
    
    private initPresenter() {
        presenter.setScheduleHistoryPresenter(mockScheduleHistoryPresenter)
        presenter.setView(mockWeeklyScheduleView)
        presenter.setDailySchedulePresenterFactory(mockDailySchedulePresenterFactory)
        presenter.setShoppingList(mockShoppingListPresenter)
    }
    
    def "should update the schedule history view when a new schedule is saved" () {
        given:""
        presenter.displayedSchedule = schedule
        when:""
        presenter.saveSchedule()
        then:""
        1 * mockScheduleHistoryPresenter.addScheduleToView(schedule)
    }
    
    
    
    
    
    def "should save schedule when save is called" () {
        given:"presenter is initialised"
        presenter.init()
        
        and:"a start date is set"
        schedule.setStartDate(new Date(112,8,22))
        
        when:"the schedule is saved"
        presenter.saveSchedule()
        
        then:"the schedule is persisted"
        scheduleDao.getByName("Sep 22, 2012") != null
    }
    
    
    def "should clear the shopping list when the schedule is cleared" () {
        given:""
        presenter.init()
        presenter.setStartDate(new Date())
        when:"the schedule is cleared"
        presenter.clearSchedule()
        
        then:"the shopping list is cleared"
        1*  mockShoppingListPresenter.clear()
    }
}