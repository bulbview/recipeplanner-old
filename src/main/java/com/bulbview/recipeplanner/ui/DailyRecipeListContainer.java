package com.bulbview.recipeplanner.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.ui.VerticalLayout;

public class DailyRecipeListContainer extends VerticalLayout implements DailyRecipeListsContainerView {

    private final List<DailyRecipeList>     dailyRecipeLists;
    private final Provider<DailyRecipeList> dailyListProvider;
    private final UiHelper                  uiHelper;
    private final Logger                    logger;

    @Inject
    public DailyRecipeListContainer(final Provider<DailyRecipeList> dailyListProvider,
                                    final Collection<DailyRecipeList> dailyRecipeLists,
                                    final UiHelper uiHelper) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.dailyListProvider = dailyListProvider;
        this.dailyRecipeLists = new ArrayList<DailyRecipeList>(dailyRecipeLists);
        ;
        this.uiHelper = uiHelper;
    }

    public void createDailyList() {
        logger.debug("Creating daily lists...");
        final DailyRecipeList dailyRecipeList = dailyListProvider.get();
        dailyRecipeLists.add(dailyRecipeList);
        addComponent(dailyRecipeList);
    }

    public void updateDateHeaders(final Date date) {
        for ( int i = 0; i < dailyRecipeLists.size(); i++ ) {
            final DailyRecipeList dailyRecipeList = dailyRecipeLists.get(i);
            dailyRecipeList.setDateHeader(uiHelper.incrementDay(date, i));
        }

    }

}
