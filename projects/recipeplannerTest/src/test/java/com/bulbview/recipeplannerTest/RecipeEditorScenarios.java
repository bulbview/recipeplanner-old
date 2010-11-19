package com.bulbview.recipeplannerTest;

import static org.mockito.Mockito.verify;
import static tumbler.Tumbler.Given;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import tumbler.Scenario;
import tumbler.Tumbler;
import tumbler.TumblerRunner;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.persistence.RecipeplannerPersistenceService;
import com.bulbview.recipeplanner.ui.RecipeEditorFormView;
import com.bulbview.recipeplanner.ui.presenter.Category;
import com.bulbview.recipeplanner.ui.presenter.RecipeEditorPresenter;

@RunWith(TumblerRunner.class)
public class RecipeEditorScenarios {

    @Mock
    private RecipeplannerPersistenceService mockPersistenceService;

    @Mock
    private Recipe                          mockRecipe;
    @Mock
    private RecipeEditorFormView            mockRecipeEditorFormView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Scenario
    public void shouldProvideIngredientOptionsIfEditingAnExistingRecipe() {
        Given("The recipe editor");
        final RecipeEditorPresenter recipeEditorPresenter = new RecipeEditorPresenter(mockPersistenceService,
                                                                                      mockRecipeEditorFormView, null,
                                                                                      null);
        Tumbler.When("An existing recipe is edited");
        recipeEditorPresenter.onEditRecipe(mockRecipe);

        Tumbler.Then("All ingredients are retrieved from persistence");
        verify(mockPersistenceService).getIngredients();
        Tumbler.Then("The ingredients are added to the editor");
        verify(mockRecipeEditorFormView).activate(Mockito.eq(mockRecipe),
                                                  Mockito.anyCollectionOf(Ingredient.class),
                                                  Mockito.anyCollectionOf(Category.class));

    }
}
