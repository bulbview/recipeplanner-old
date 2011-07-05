package com.bulbview.recipeplanner.ui.presenter;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.persistence.DaoException;
import com.bulbview.recipeplanner.persistence.EntityDao;
import com.bulbview.recipeplanner.ui.manager.CategoryAccordion;
import com.bulbview.recipeplanner.ui.manager.CategoryEditorView;
import com.vaadin.ui.Window;

@Component
public class CategoryTabsPresenter extends Presenter implements SessionPresenter {
    
    private EntityDao<ItemCategory>       categoryDao;
    private CategoryEditorView            categoryEditorView;
    private Collection<CategoryAccordion> categoryAccordions;
    @Autowired
    private Window                        categoryWindow;
    
    private final Logger                  logger;
    private Window                        rootWindow;
    
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
    
    @Autowired
    public void setCategoryAccordions(final Collection<CategoryAccordion> categoryAccordions) {
        this.categoryAccordions = categoryAccordions;
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
        for (CategoryAccordion categoryAccordion : categoryAccordions) {
            categoryAccordion.addCategoryTab(savedCategory.getName());
        }
    }
}
