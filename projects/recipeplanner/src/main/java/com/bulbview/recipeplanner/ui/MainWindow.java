package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@Component
public class MainWindow extends CustomComponent {

    private static final long      serialVersionUID = 1L;
    @AutoGenerated
    private VerticalLayout         category1VerticalLayout;
    @AutoGenerated
    private Label                  categorylabel1;
    @AutoGenerated
    private Panel                  categoryPanel1;
    @AutoGenerated
    private VerticalLayout         categoryVerticalLayout1;
    private final Logger           logger;
    @AutoGenerated
    private HorizontalLayout       logoHorizontalLayout;
    @AutoGenerated
    private VerticalLayout         mainCentredVerticalLayout;
    @AutoGenerated
    private VerticalLayout         mainLayout;
    private RecipePlannerPresenter presenter;
    @AutoGenerated
    private HorizontalLayout       RecipeAndSchedulerHorizontalLayout;
    @AutoGenerated
    private Panel                  recipePanel;
    @AutoGenerated
    private VerticalLayout         recipePanelVerticalLayout;
    @AutoGenerated
    private MenuBar                recipeplannerMenuBar;
    @AutoGenerated
    private Accordion              SchedulerAccordion;
    @AutoGenerated
    private GridLayout             shoppingListGridLayout;
    @AutoGenerated
    private Label                  ShoppingListLabel;
    @AutoGenerated
    private VerticalLayout         titleAndMenuVerticalLayout;

    /**
     * The constructor should first build the main layout, set the composition
     * root and then do any custom initialization.
     * 
     * The constructor will not be automatically regenerated by the visual
     * editor.
     */
    public MainWindow() {
        buildMainLayout();
        setCompositionRoot(mainLayout);
        this.logger = LoggerFactory.getLogger(getClass());
        buildMenuBarItems();
    }

    public Panel getRecipePanel() {
        return recipePanel;
    }

    public void setApplicationLogo(final Embedded embedded) {
        embedded.setHeight("100px");
        logoHorizontalLayout.addComponent(embedded);
    }

    public void setPresenter(final RecipePlannerPresenter presenter) {
        this.presenter = presenter;

    }

    @AutoGenerated
    private VerticalLayout buildCategory1VerticalLayout() {
        // common part: create layout
        category1VerticalLayout = new VerticalLayout();
        category1VerticalLayout.setWidth("-1px");
        category1VerticalLayout.setHeight("-1px");
        category1VerticalLayout.setImmediate(false);
        category1VerticalLayout.setMargin(false);

        // categorylabel1
        categorylabel1 = new Label();
        categorylabel1.setWidth("31px");
        categorylabel1.setHeight("-1px");
        categorylabel1.setValue("Category 1");
        categorylabel1.setImmediate(false);
        category1VerticalLayout.addComponent(categorylabel1);

        // categoryPanel1
        categoryPanel1 = buildCategoryPanel1();
        category1VerticalLayout.addComponent(categoryPanel1);

        return category1VerticalLayout;
    }

    @AutoGenerated
    private Panel buildCategoryPanel1() {
        // common part: create layout
        categoryPanel1 = new Panel();
        categoryPanel1.setWidth("100px");
        categoryPanel1.setHeight("30px");
        categoryPanel1.setImmediate(false);

        // categoryVerticalLayout1
        categoryVerticalLayout1 = new VerticalLayout();
        categoryVerticalLayout1.setWidth("100.0%");
        categoryVerticalLayout1.setHeight("100.0%");
        categoryVerticalLayout1.setImmediate(false);
        categoryVerticalLayout1.setMargin(false);
        categoryPanel1.setContent(categoryVerticalLayout1);

        return categoryPanel1;
    }

    @AutoGenerated
    private VerticalLayout buildMainCentredVerticalLayout() {
        // common part: create layout
        mainCentredVerticalLayout = new VerticalLayout();
        mainCentredVerticalLayout.setWidth("699px");
        mainCentredVerticalLayout.setHeight("600px");
        mainCentredVerticalLayout.setImmediate(false);
        mainCentredVerticalLayout.setMargin(false);

        // titleAndMenuVerticalLayout
        titleAndMenuVerticalLayout = buildTitleAndMenuVerticalLayout();
        mainCentredVerticalLayout.addComponent(titleAndMenuVerticalLayout);

        // RecipeAndSchedulerHorizontalLayout
        RecipeAndSchedulerHorizontalLayout = buildRecipeAndSchedulerHorizontalLayout();
        mainCentredVerticalLayout.addComponent(RecipeAndSchedulerHorizontalLayout);
        mainCentredVerticalLayout.setComponentAlignment(RecipeAndSchedulerHorizontalLayout, new Alignment(20));

        // ShoppingListLabel
        ShoppingListLabel = new Label();
        ShoppingListLabel.setWidth("-1px");
        ShoppingListLabel.setHeight("-1px");
        ShoppingListLabel.setValue("Shopping List");
        ShoppingListLabel.setImmediate(false);
        mainCentredVerticalLayout.addComponent(ShoppingListLabel);

        // shoppingListGridLayout
        shoppingListGridLayout = buildShoppingListGridLayout();
        mainCentredVerticalLayout.addComponent(shoppingListGridLayout);
        mainCentredVerticalLayout.setComponentAlignment(shoppingListGridLayout, new Alignment(48));

        return mainCentredVerticalLayout;
    }

    @AutoGenerated
    private VerticalLayout buildMainLayout() {
        // common part: create layout
        mainLayout = new VerticalLayout();

        // top-level component properties
        setWidth("100.0%");
        setHeight("100.0%");

        // mainCentredVerticalLayout
        mainCentredVerticalLayout = buildMainCentredVerticalLayout();
        mainLayout.addComponent(mainCentredVerticalLayout);
        mainLayout.setComponentAlignment(mainCentredVerticalLayout, new Alignment(20));

        return mainLayout;
    }

    private void buildMenuBarItems() {
        final Command addRecipeMenuItemCommand = new Command() {

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                logger.debug("menu item selected '{}'", selectedItem.getText());
                presenter.createNewRecipe();
            }
        };
        final MenuItem addRecipeMenuItem = recipeplannerMenuBar.addItem("add recipe", addRecipeMenuItemCommand);
        final MenuItem clearMenuItem = recipeplannerMenuBar.addItem("clear", addRecipeMenuItemCommand);
        addRecipeMenuItem.setStyleName("menuitem");
    }

    @AutoGenerated
    private HorizontalLayout buildRecipeAndSchedulerHorizontalLayout() {
        // common part: create layout
        RecipeAndSchedulerHorizontalLayout = new HorizontalLayout();
        RecipeAndSchedulerHorizontalLayout.setWidth("100.0%");
        RecipeAndSchedulerHorizontalLayout.setHeight("-1px");
        RecipeAndSchedulerHorizontalLayout.setImmediate(false);
        RecipeAndSchedulerHorizontalLayout.setMargin(true);
        RecipeAndSchedulerHorizontalLayout.setSpacing(true);

        // recipePanel
        recipePanel = buildRecipePanel();
        RecipeAndSchedulerHorizontalLayout.addComponent(recipePanel);

        // SchedulerAccordion
        SchedulerAccordion = new Accordion();
        SchedulerAccordion.setWidth("300px");
        SchedulerAccordion.setHeight("200px");
        SchedulerAccordion.setImmediate(false);
        RecipeAndSchedulerHorizontalLayout.addComponent(SchedulerAccordion);
        RecipeAndSchedulerHorizontalLayout.setComponentAlignment(SchedulerAccordion, new Alignment(6));

        return RecipeAndSchedulerHorizontalLayout;
    }

    @AutoGenerated
    private Panel buildRecipePanel() {
        // common part: create layout
        recipePanel = new Panel();
        recipePanel.setWidth("300px");
        recipePanel.setHeight("200px");
        recipePanel.setImmediate(false);

        // recipePanelVerticalLayout
        recipePanelVerticalLayout = new VerticalLayout();
        recipePanelVerticalLayout.setWidth("100px");
        recipePanelVerticalLayout.setHeight("-1px");
        recipePanelVerticalLayout.setImmediate(false);
        recipePanelVerticalLayout.setMargin(true);
        recipePanel.setContent(recipePanelVerticalLayout);

        return recipePanel;
    }

    @AutoGenerated
    private GridLayout buildShoppingListGridLayout() {
        // common part: create layout
        shoppingListGridLayout = new GridLayout();
        shoppingListGridLayout.setWidth("100.0%");
        shoppingListGridLayout.setHeight("100px");
        shoppingListGridLayout.setImmediate(false);
        shoppingListGridLayout.setMargin(false);
        shoppingListGridLayout.setColumns(3);
        shoppingListGridLayout.setRows(3);

        // category1VerticalLayout
        category1VerticalLayout = buildCategory1VerticalLayout();
        shoppingListGridLayout.addComponent(category1VerticalLayout, 0, 0);
        shoppingListGridLayout.setComponentAlignment(category1VerticalLayout, new Alignment(48));

        return shoppingListGridLayout;
    }

    @AutoGenerated
    private VerticalLayout buildTitleAndMenuVerticalLayout() {
        // common part: create layout
        titleAndMenuVerticalLayout = new VerticalLayout();
        titleAndMenuVerticalLayout.setWidth("-1px");
        titleAndMenuVerticalLayout.setHeight("-1px");
        titleAndMenuVerticalLayout.setImmediate(false);
        titleAndMenuVerticalLayout.setMargin(false);

        // logoHorizontalLayout
        logoHorizontalLayout = new HorizontalLayout();
        logoHorizontalLayout.setWidth("-1px");
        logoHorizontalLayout.setHeight("-1px");
        logoHorizontalLayout.setImmediate(false);
        logoHorizontalLayout.setMargin(false);
        titleAndMenuVerticalLayout.addComponent(logoHorizontalLayout);
        titleAndMenuVerticalLayout.setComponentAlignment(logoHorizontalLayout, new Alignment(48));

        // recipeplannerMenuBar
        recipeplannerMenuBar = new MenuBar();
        recipeplannerMenuBar.setWidth("750px");
        recipeplannerMenuBar.setHeight("50px");
        recipeplannerMenuBar.setImmediate(false);
        titleAndMenuVerticalLayout.addComponent(recipeplannerMenuBar);
        titleAndMenuVerticalLayout.setComponentAlignment(recipeplannerMenuBar, new Alignment(48));

        return titleAndMenuVerticalLayout;
    }

}
