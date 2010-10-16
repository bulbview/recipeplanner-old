package com.bulbview.recipeplanner;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBusManager;

import com.bulbview.recipeplanner.dao.RecipeDao;
import com.bulbview.recipeplanner.dao.TestRecipeDao;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.ApplicationNameLabel;
import com.bulbview.recipeplanner.ui.DailyRecipeList;
import com.bulbview.recipeplanner.ui.DailyRecipeListContainer;
import com.bulbview.recipeplanner.ui.DailyRecipeListsContainerView;
import com.bulbview.recipeplanner.ui.MasterRecipeList;
import com.bulbview.recipeplanner.ui.MasterRecipeListView;
import com.bulbview.recipeplanner.ui.RecipeEditorForm;
import com.bulbview.recipeplanner.ui.RecipeEditorFormView;
import com.bulbview.recipeplanner.ui.RecipeEditorModalWindow;
import com.bulbview.recipeplanner.ui.RecipePlannerApplication;
import com.bulbview.recipeplanner.ui.RecipePlannerEventBus;
import com.bulbview.recipeplanner.ui.RecipePlannerPresenter.NumberOfDailyRecipeLists;
import com.bulbview.recipeplanner.ui.RecipePlannerWindow;
import com.bulbview.recipeplanner.ui.WindowView;
import com.bulbview.recipeplanner.ui.menu.MasterRecipeListContextMenu;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.ServletScopes;
import com.google.inject.servlet.SessionScoped;
import com.vaadin.Application;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

final class RecipePlannerServletModule extends ServletModule {

    private final Logger logger;

    /**
     * @param recipePlannerServletConfig
     */
    RecipePlannerServletModule() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    protected void configureServlets() {
        serve("/*").with(GuiceApplicationServlet.class);

        bind(EventBusManager.class).in(ServletScopes.SESSION);

        bind(Application.class).to(RecipePlannerApplication.class).in(ServletScopes.SESSION);
        bind(String.class).toInstance("Recipe Planner");
        bind(RecipePlannerWindow.class).in(ServletScopes.SESSION);
        bind(Window.class).to(RecipePlannerWindow.class);
        bind(WindowView.class).to(RecipePlannerWindow.class);
        bind(DailyRecipeListContainer.class).in(ServletScopes.SESSION);
        bind(DailyRecipeListsContainerView.class).to(DailyRecipeListContainer.class);
        bind(RecipeEditorFormView.class).to(RecipeEditorForm.class);
        bind(MasterRecipeListView.class).to(MasterRecipeList.class);
        bind(RecipeEditorForm.class).in(ServletScopes.SESSION);
        bind(MasterRecipeList.class).in(ServletScopes.SESSION);
        bind(new TypeLiteral<Collection<DailyRecipeList>>() {}).toInstance(new HashSet<DailyRecipeList>());
        bind(Label.class).to(ApplicationNameLabel.class);
        bind(Integer.class).annotatedWith(NumberOfDailyRecipeLists.class).toInstance(7);
        bind(MasterRecipeListContextMenu.class).in(ServletScopes.SESSION);
        bind(RecipeEditorModalWindow.class).in(ServletScopes.SESSION);
        bind(new TypeLiteral<Collection<Recipe>>() {}).toInstance(new HashSet<Recipe>());
        bind(RecipeDao.class).to(TestRecipeDao.class);
    }

    @Provides
    @SessionScoped
    RecipePlannerEventBus providesRecipePlannerEventBus(final EventBusManager eventBusManager) {
        logger.debug("Retrieving EventBus...");
        // final IPresenter<?, ? extends EventBus> presenter =
        // recipePlannerPresenterFactory.createPresenter(RecipePlannerPresenter.class);
        // return (RecipePlannerEventBus) presenter.getEventBus();
        return eventBusManager.getEventBus(RecipePlannerEventBus.class);
    }
}