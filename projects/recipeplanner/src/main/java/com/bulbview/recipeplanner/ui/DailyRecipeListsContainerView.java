package com.bulbview.recipeplanner.ui;

import java.util.Collection;
import java.util.Date;

public interface DailyRecipeListsContainerView {

    public void createDailyList();

    public Collection<DailyRecipeList> getDailyLists();

    public void updateDateHeaders(Date value);

}
