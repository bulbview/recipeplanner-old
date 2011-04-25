import static org.junit.Assert.*
import spock.lang.Specification

import com.bulbview.recipeplanner.ui.MainWindowUiHelper
import com.bulbview.recipeplanner.ui.RecipePlannerPresenter


class RecipePresenterTest extends Specification {

    def RecipePlannerPresenter presenter = new RecipePlannerPresenter()
    def MainWindowUiHelper mainWindowUiHelper = Mock(MainWindowUiHelper)

    def "should close recipe editor on save" (){

        given: "the presenter has a mainWindow"
        presenter.setMainWindow(mainWindowUiHelper)

        when: "the presenter saves a recipe"
        presenter.save()

        then:"the recipe editor is closed"
        1 * mainWindowUiHelper.closeRecipeEditor()
    }
}

