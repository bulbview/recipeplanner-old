package com.bulbview.recipeplanner.ui;

import com.google.inject.Inject;
import com.vaadin.ui.VerticalLayout;

public class RightSplitPanelConstituent extends VerticalLayout {

    @Inject public RightSplitPanelConstituent(final StartDateSelector startDateSelector,
                                              final DailyRecipeListContainer dailyRecipeListContainer) {
        addComponent(startDateSelector);
        addComponent(dailyRecipeListContainer);
    }

}
