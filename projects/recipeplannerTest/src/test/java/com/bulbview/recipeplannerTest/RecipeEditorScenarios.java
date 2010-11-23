package com.bulbview.recipeplannerTest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static tumbler.Tumbler.Given;
import static tumbler.Tumbler.Then;
import static tumbler.Tumbler.When;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tumbler.Scenario;
import tumbler.TumblerRunner;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.persistence.RecipeplannerPersistenceService;
import com.bulbview.recipeplanner.ui.RecipeEditorFormView;
import com.bulbview.recipeplanner.ui.presenter.RecipeEditorPresenter;

@RunWith(TumblerRunner.class)
public class RecipeEditorScenarios {

    @Mock
    private RecipeplannerPersistenceService mockPersistenceService;
    @Mock
    private Recipe                          mockRecipe;
    @Mock
    private RecipeEditorFormView            mockRecipeEditorFormView;

    private RecipeEditorPresenter           recipeEditorPresenter;
    @Mock
    private Ingredient                      mockIngredient1;
    @Mock
    private Ingredient                      mockIngredient;

    public void createRecipeEditorPresenter() {
        recipeEditorPresenter = new RecipeEditorPresenter(mockPersistenceService, mockRecipeEditorFormView, null, null);
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Scenario
    public void shouldAddExistingRecipesIngredientsToEditor() {

        Given("The recipe editor and, an existing recipe with ingredients");
        createRecipeEditorPresenter();
        final List<Ingredient> ingredients = Arrays.asList(mock(Ingredient.class));
        given(mockRecipe.getIngredients()).willReturn(ingredients);

        When("the recipe is edited in the editor");
        recipeEditorPresenter.onEditRecipe(mockRecipe);

        Then("the recipe's existing ingredients are displayed in the editor");
        verify(mockRecipeEditorFormView).setRecipe(mockRecipe);
    }

    @Scenario
    public void shouldProvideIngredientOptionsIfEditingAnExistingRecipe() {
        Given("The recipe editor");
        createRecipeEditorPresenter();
        final List<Ingredient> mockIngredientCollection = Arrays.asList(mockIngredient, mockIngredient1);
        given(mockPersistenceService.getIngredients()).willReturn(mockIngredientCollection);

        When("An existing recipe is edited");
        recipeEditorPresenter.onEditRecipe(mockRecipe);

        Then("All ingredients are retrieved from persistence and the ingredients are added to the editor");
        verify(mockPersistenceService).getIngredients();
        final InOrder inOrder = inOrder(mockRecipeEditorFormView);
        inOrder.verify(mockRecipeEditorFormView).setIngredientOptions(mockIngredientCollection);
        inOrder.verify(mockRecipeEditorFormView).setRecipe(mockRecipe);
    }
}
