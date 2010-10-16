package com.bulbview.recipeplanner.ui;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;

import com.google.inject.Inject;
import com.vaadin.Application;
import com.vaadin.ui.Form;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.Window;

public class RecipePlannerApplication extends Application {

    private final Form                              recipeEditor = new Form();

    private final RightSplitPanelConstituent        rightSplitPanelConstituent;

    private final LeftSplitPanelConstituent         leftSplitPanelConstituent;

    private final SplitPanel                        splitPanel;

    private final Window                            mainWindow;

    private final String                            applicationName;
    /** Required to prevent garbage collection of presenter **/
    private final IPresenter<?, ? extends EventBus> presenter;

    @Inject
    public RecipePlannerApplication(final Window mainWindow,
                                    final SplitPanel splitPanel,
                                    final RightSplitPanelConstituent rightSplitPanelConstituent,
                                    final LeftSplitPanelConstituent leftSplitPanelConstituent,
                                    final String applicationName,
                                    final RecipePlannerEventBus recipePlannerEventBus,
                                    final RecipePlannerPresenterFactory recipePlannerPresenterFactory) {

        this.rightSplitPanelConstituent = rightSplitPanelConstituent;
        this.leftSplitPanelConstituent = leftSplitPanelConstituent;
        this.splitPanel = splitPanel;
        this.mainWindow = mainWindow;
        this.applicationName = applicationName;
        presenter = recipePlannerPresenterFactory.createPresenter(RecipePlannerPresenter.class);
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
