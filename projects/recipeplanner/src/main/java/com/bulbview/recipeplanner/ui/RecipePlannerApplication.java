package com.bulbview.recipeplanner.ui;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;

import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.bulbview.recipeplanner.ui.presenter.RecipeEditorPresenter;
import com.google.inject.Inject;
import com.vaadin.Application;
import com.vaadin.ui.Form;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class RecipePlannerApplication extends Application {

    private final Form                              recipeEditor = new Form();

    private final RightSplitPanelConstituent        rightSplitPanelConstituent;

    private final LeftSplitPanelConstituent         leftSplitPanelConstituent;

    private final SplitPanel                        splitPanel;

    private final Window                            mainWindow;

    private final String                            applicationName;

    private final IPresenter<?, ? extends EventBus> recipePlannerPresenter;

    private final IPresenter<?, ? extends EventBus> recipeEditorPlanner;

    @Inject
    public RecipePlannerApplication(final Window mainWindow,
                                    final SplitPanel splitPanel,
                                    final RightSplitPanelConstituent rightSplitPanelConstituent,
                                    final LeftSplitPanelConstituent leftSplitPanelConstituent,
                                    final String applicationName,
                                    final RecipePlannerEventBus recipePlannerEventBus,
                                    final RecipePlannerPresenterFactory<RecipePlannerPresenter> recipePlannerPresenterFactory,
                                    final RecipePlannerPresenterFactory<RecipeEditorPresenter> recipeEditorPresenterFactory) {

        this.rightSplitPanelConstituent = rightSplitPanelConstituent;
        this.leftSplitPanelConstituent = leftSplitPanelConstituent;
        this.splitPanel = splitPanel;
        this.mainWindow = mainWindow;
        this.applicationName = applicationName;
        // assign to fields to avoid garbage collection
        this.recipePlannerPresenter = recipePlannerPresenterFactory.createPresenter(RecipePlannerPresenter.class);
        this.recipeEditorPlanner = recipeEditorPresenterFactory.createPresenter(RecipeEditorPresenter.class);
        recipePlannerEventBus.initialise();
    }

    @Override
    public void init() {
        initLayout();
    }

    private void initLayout() {
        splitPanel.setOrientation(SplitPanel.ORIENTATION_HORIZONTAL);
        mainWindow.setContent(splitPanel);
        mainWindow.setCaption(applicationName);
        setMainWindow(mainWindow);
        splitPanel.addComponent(leftSplitPanelConstituent);
        splitPanel.addComponent(rightSplitPanelConstituent);

        recipeEditor.setSizeFull();
        recipeEditor.getLayout().setMargin(true);
        recipeEditor.setImmediate(true);
    }
}
