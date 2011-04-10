package com.bulbview.recipeplanner.ui;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;

import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.bulbview.recipeplanner.ui.presenter.RecipeEditorPresenter;
import com.google.inject.Inject;
import com.vaadin.Application;
import com.vaadin.terminal.ClassResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class RecipePlannerApplication extends Application {

    // private final Form recipeEditor = new Form();

    private final RightSplitPanelConstituent        rightSplitPanelConstituent;

    private final IngredientRecipeLayout            ingredientsAndRecipesLayout;

    private final HorizontalLayout                  mainWindowLayout;

    private final Window                            mainWindow;

    private final String                            applicationName;

    private final IPresenter<?, ? extends EventBus> recipePlannerPresenter;

    private final IPresenter<?, ? extends EventBus> recipeEditorPlanner;

    private final ApplicationNameLabel              applicationNameLabel;

    private final MainWindow                        recipePlannerMainCustomComponent;

    @Inject
    public RecipePlannerApplication(final Window mainWindow,
                                    final MainWindow recipePlannerMainConfigComponent,
                                    final ApplicationNameLabel applicationNameLabel,
                                    final HorizontalLayout mainWindowLayout,
                                    final RightSplitPanelConstituent rightSplitPanelConstituent,
                                    final IngredientRecipeLayout ingredientRecipeLayout,
                                    final String applicationName,
                                    final RecipePlannerEventBus recipePlannerEventBus,
                                    final RecipePlannerPresenterFactory<RecipePlannerPresenter> recipePlannerPresenterFactory,
                                    final RecipePlannerPresenterFactory<RecipeEditorPresenter> recipeEditorPresenterFactory) {

        this.rightSplitPanelConstituent = rightSplitPanelConstituent;
        this.ingredientsAndRecipesLayout = ingredientRecipeLayout;
        this.mainWindowLayout = mainWindowLayout;
        this.mainWindow = mainWindow;
        this.recipePlannerMainCustomComponent = recipePlannerMainConfigComponent;
        this.mainWindow.addComponent(recipePlannerMainConfigComponent);
        this.applicationName = applicationName;
        this.applicationNameLabel = applicationNameLabel;
        // assign to fields to avoid garbage collection
        this.recipePlannerPresenter = recipePlannerPresenterFactory.createPresenter(RecipePlannerPresenter.class);
        this.recipeEditorPlanner = recipeEditorPresenterFactory.createPresenter(RecipeEditorPresenter.class);
        recipePlannerEventBus.initialise();
    }

    @Override
    public void init() {
        initLayout();
    }

    private TabSheet createTabsheet() {
        final TabSheet tabsheet = new TabSheet();
        tabsheet.setHeight("600px");
        tabsheet.setWidth("1000px");
        tabsheet.addTab(ingredientsAndRecipesLayout, "Edit Recipes", null);
        tabsheet.addTab(rightSplitPanelConstituent, "Daily Scheduler", null);
        tabsheet.addTab(new Label("Shopping"), "Shopping List", null);
        return tabsheet;
    }

    private void initLayout() {
        // mainWindow.setContent(mainWindowLayout);
        mainWindowLayout.setSizeFull();
        mainWindow.setCaption(applicationName);
        setMainWindow(mainWindow);
        // Application required to create classpath resource
        recipePlannerMainCustomComponent.setApplicationLogo(new Embedded(null,
                                                                         new ClassResource("recipePlannerlogo1.jpg",
                                                                                           this)));

        // mainWindowLayout.addComponent(rightSplitPanelConstituent);
    }
}
