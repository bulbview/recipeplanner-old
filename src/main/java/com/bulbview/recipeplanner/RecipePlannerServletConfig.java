package com.bulbview.recipeplanner;

import java.util.Collection;
import java.util.HashSet;

import com.bulbview.recipeplanner.dao.RecipeDao;
import com.bulbview.recipeplanner.dao.TestRecipeDao;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.ApplicationNameLabel;
import com.bulbview.recipeplanner.ui.DailyRecipeList;
import com.bulbview.recipeplanner.ui.DailyRecipeListContainer;
import com.bulbview.recipeplanner.ui.DailyRecipeListsContainerView;
import com.bulbview.recipeplanner.ui.MasterRecipeList;
import com.bulbview.recipeplanner.ui.MasterRecipeListView;
import com.bulbview.recipeplanner.ui.Presenter;
import com.bulbview.recipeplanner.ui.RecipeEditorForm;
import com.bulbview.recipeplanner.ui.RecipeEditorFormView;
import com.bulbview.recipeplanner.ui.RecipeEditorModalWindow;
import com.bulbview.recipeplanner.ui.RecipePlannerApplication;
import com.bulbview.recipeplanner.ui.RecipePlannerPresenter;
import com.bulbview.recipeplanner.ui.RecipePlannerPresenter.NumberOfDailyRecipeLists;
import com.bulbview.recipeplanner.ui.RecipePlannerWindow;
import com.bulbview.recipeplanner.ui.WindowView;
import com.bulbview.recipeplanner.ui.menu.MasterRecipeListContextMenu;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.ServletScopes;
import com.vaadin.Application;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class RecipePlannerServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {

        final ServletModule module = new ServletModule() {

            @Override
            protected void configureServlets() {
                serve("/*").with(GuiceApplicationServlet.class);

                bind(Application.class).to(RecipePlannerApplication.class).in(ServletScopes.SESSION);
                bind(String.class).toInstance("Recipe Planner");
                bind(Presenter.class).to(RecipePlannerPresenter.class);
                bind(RecipePlannerPresenter.class).in(ServletScopes.SESSION);
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
        };

        final Injector injector = Guice.createInjector(module);

        return injector;
    }
}