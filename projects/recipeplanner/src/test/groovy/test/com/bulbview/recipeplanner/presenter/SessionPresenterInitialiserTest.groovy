package test.com.bulbview.recipeplanner.presenter

import org.springframework.beans.factory.annotation.Autowired

import test.com.bulbview.recipeplanner.dao.SpringContextTestFixture

import com.bulbview.recipeplanner.ui.SessionPresenterInitialiser


class SessionPresenterInitialiserTest extends SpringContextTestFixture {


    @Autowired
    def SessionPresenterInitialiser sessionPresenterInitialiser;

    def "should initialise session scope presenters" () {

        when:"the recipe planner application is initialised"
        sessionPresenterInitialiser.initialise()

        then:"all session scoped views are successfully initialised"
    }
}
