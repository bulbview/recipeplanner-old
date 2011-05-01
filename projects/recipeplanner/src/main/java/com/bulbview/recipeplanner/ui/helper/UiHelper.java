package com.bulbview.recipeplanner.ui.helper;

import com.bulbview.recipeplanner.ui.RecipePlannerPresenter;

public abstract class UiHelper {

    protected RecipePlannerPresenter presenter;

    public void setPresenter(final RecipePlannerPresenter presenter) {
        this.presenter = presenter;
    }

}
