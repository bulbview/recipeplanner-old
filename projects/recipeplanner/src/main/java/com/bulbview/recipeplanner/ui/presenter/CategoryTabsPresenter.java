package com.bulbview.recipeplanner.ui.presenter;

import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.persistence.DaoException;
import com.bulbview.recipeplanner.persistence.EntityDao;
import com.bulbview.recipeplanner.ui.RecipeEditor;
import com.bulbview.recipeplanner.ui.manager.CategoriesAccordionDecorator;
import com.bulbview.recipeplanner.ui.manager.CategoryEditorView;
import com.bulbview.recipeplanner.ui.manager.MainWindowView;
import com.bulbview.recipeplanner.ui.manager.factory.CategorisedItemListFactory;
import com.vaadin.ui.Window;

@Component
public class CategoryTabsPresenter extends Presenter implements SessionPresenter {
    
    @Autowired
    private CategoriesViewFactory                    categoriesViewFactory;
    private Collection<CategoriesAccordionDecorator> categoryAccordions;
    private EntityDao<ItemCategory>                  categoryDao;
    private CategoryEditorView                       categoryEditorView;
    @Autowired
    private Window                                   categoryWindow;
    private final Logger                             logger;
    
    @Autowired
    private MainWindowView                           mainWindow;
    @Autowired
    private RecipeEditor                             recipeEditor;
    private Window                                   rootWindow;
    @Autowired
    CategorisedItemListFactory                       categorisedItemListFactory;
    
    public CategoryTabsPresenter() {
        this.logger = LoggerFactory.getLogger(getClass());
    }
    
    public void addCategoryMenuSelected() {
        categoryEditorView.setItemCategory(createItemCategory());
        rootWindow.addWindow(categoryWindow);
    }
    
    public void closeCategoryWindow() {
        rootWindow.removeWindow(categoryWindow);
    }
    
    @Override
    public void init() {
        categoryAccordions = createCategoryAccordions();
        categoryEditorView.init();
        setCategoriesInView(categoryDao.getAll());
    }
    
    public void saveCategory(final ItemCategory itemCategory) {
        logger.debug("saving category: {}...", itemCategory);
        try {
            final ItemCategory savedCategory = categoryDao.save(itemCategory);
            updateCategoryAccordions(savedCategory);
            closeCategoryWindow();
        }
        catch (final DaoException e) {
            categoryEditorView.showErrorMessage(e.getMessage());
        }
    }
    
    public void setCategoriesViewFactory(final CategoriesViewFactory categoriesViewFactory) {
        this.categoriesViewFactory = categoriesViewFactory;
    }
    
    @Autowired
    public void setCategoryDao(final EntityDao<ItemCategory> categoryDao) {
        this.categoryDao = categoryDao;
    }
    
    @Autowired
    public void setCategoryEditorWindow(final CategoryEditorView categoryEditorView) {
        this.categoryEditorView = categoryEditorView;
        setView(categoryEditorView);
    }
    
    @Autowired
    public void setRootWindow(final Window rootWindow) {
        this.rootWindow = rootWindow;
    }
    
    private Collection<CategoriesAccordionDecorator> createCategoryAccordions() {
        return Arrays.asList(new CategoriesAccordionDecorator[] {
                categoriesViewFactory.createCategoriesView(recipeEditor.getCategoryAccordion()),
                categoriesViewFactory.createCategoriesView(mainWindow.getCategoriesAccordion()) });
    }
    
    private ItemCategory createItemCategory() {
        return new ItemCategory();
    }
    
    private void setCategoriesInView(final Collection<ItemCategory> categories) {
        logger.info("Initialising categories: {}...", categories);
        for (final ItemCategory category : categories) {
            updateCategoryAccordions(category);
        }
    }
    
    private void updateCategoryAccordions(final ItemCategory savedCategory) {
        for (final CategoriesAccordionDecorator categoryAccordion : categoryAccordions) {
            categoryAccordion.addCategoryTab(savedCategory.getName(),
                                             categorisedItemListFactory.createList(savedCategory.getName()));
        }
    }
}
