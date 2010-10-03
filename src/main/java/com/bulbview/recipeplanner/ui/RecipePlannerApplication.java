package com.bulbview.recipeplanner.ui;

import com.google.inject.Inject;
import com.vaadin.Application;
import com.vaadin.ui.Form;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.Window;

public class RecipePlannerApplication extends Application {

    private final Form                       recipeEditor = new Form();

    private final RightSplitPanelConstituent rightSplitPanelConstituent;

    private final LeftSplitPanelConstituent  leftSplitPanelConstituent;

    private final SplitPanel                 splitPanel;

    private final Window                     mainWindow;

    private final String                     applicationName;

    @Inject
    public RecipePlannerApplication(final Presenter presenter,
                                    final Window mainWindow,
                                    final SplitPanel splitPanel,
                                    final RightSplitPanelConstituent rightSplitPanelConstituent,
                                    final LeftSplitPanelConstituent leftSplitPanelConstituent,
                                    final String applicationName) {

        this.rightSplitPanelConstituent = rightSplitPanelConstituent;
        this.leftSplitPanelConstituent = leftSplitPanelConstituent;
        this.splitPanel = splitPanel;
        this.mainWindow = mainWindow;
        this.applicationName = applicationName;
        presenter.initialise();
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
