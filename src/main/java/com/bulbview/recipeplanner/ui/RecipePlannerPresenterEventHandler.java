package com.bulbview.recipeplanner.ui;

import org.bushe.swing.event.EventServiceExistsException;
import org.bushe.swing.event.EventServiceLocator;
import org.bushe.swing.event.ThreadSafeEventService;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.ui.events.CloseRecipeEditorEvent;
import com.bulbview.ui.events.CreateRecipeEvent;
import com.bulbview.ui.events.InitStartDateEvent;
import com.bulbview.ui.events.SaveRecipeEvent;
import com.google.inject.Inject;

public class RecipePlannerPresenterEventHandler {

    static {
        initialiseEventBusService();
    }

    public static void initialiseEventBusService() {
        try {
            System.out.println("*************Event bus started...");
            EventServiceLocator.setEventService(EventServiceLocator.SERVICE_NAME_SWING_EVENT_SERVICE,
                                                new ThreadSafeEventService());
        } catch (final EventServiceExistsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private final Presenter presenter;
    private final Logger    logger;

    @Inject
    RecipePlannerPresenterEventHandler(final Presenter presenter) {
        this.presenter = presenter;
        this.logger = LoggerFactory.getLogger(getClass());
        AnnotationProcessor.process(this);
    }

    @EventSubscriber(eventClass = CloseRecipeEditorEvent.class)
    public void onEvent(final CloseRecipeEditorEvent event) {
        presenter.closeRecipeEditor();
    }

    @EventSubscriber(eventClass = CreateRecipeEvent.class)
    public void onEvent(final CreateRecipeEvent event) {
        presenter.createNewRecipe();
    }

    @EventSubscriber(eventClass = InitStartDateEvent.class)
    public void onEvent(final InitStartDateEvent event) {
        logger.debug("Start date event received");
        presenter.setStartDate(event.getStartDate());
    }

    @EventSubscriber(eventClass = SaveRecipeEvent.class)
    public void onEvent(final SaveRecipeEvent event) {
        presenter.saveRecipe(event.getRecipe());
    }

}
