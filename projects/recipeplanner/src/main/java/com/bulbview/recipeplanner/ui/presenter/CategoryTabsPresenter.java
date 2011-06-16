package com.bulbview.recipeplanner.ui.presenter;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.persistence.DaoException;
import com.bulbview.recipeplanner.persistence.EntityDao;
import com.bulbview.recipeplanner.ui.manager.CategoryEditor;
import com.bulbview.recipeplanner.ui.manager.CategoryTabs;
import com.vaadin.ui.Window;

@Component
public class CategoryTabsPresenter extends Presenter implements SessionPresenter {

    private EntityDao<ItemCategory> categoryDao;
    private CategoryEditor          categoryEditor;
    private CategoryTabs            categoryTabs;
    @Autowired
    private Window                  categoryWindow;

    private final Logger            logger;
    private Window                  rootWindow;

    public CategoryTabsPresenter() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void addCategoryMenuSelected() {
        categoryEditor.setItemCategory(createItemCategory());
        rootWindow.addWindow(categoryWindow);
    }

    public void closeCategoryWindow() {
        rootWindow.removeWindow(categoryWindow);
    }

    @Override
    public void init() {
        categoryEditor.init();
        categoryTabs.init();
        setCategoriesInView(categoryDao.getAll());
    }

    public void saveCategory(final ItemCategory itemCategory) {
        logger.debug("saving category: {}...", itemCategory);
        try {
            final ItemCategory savedCategory = categoryDao.save(itemCategory);
            categoryTabs.addCategoryTab(savedCategory.getName());
            closeCategoryWindow();
        } catch (final DaoException e) {
            categoryEditor.showErrorMessage(e.getMessage());
        }
    }

    @Autowired
    public void setCategoryDao(final EntityDao<ItemCategory> categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Autowired
    public void setCategoryEditorWindow(final CategoryEditor categoryEditor) {
        this.categoryEditor = categoryEditor;
        setView(categoryEditor);
    }

    @Autowired
    public void setCategoryTabs(final CategoryTabs categoryTabs) {
        this.categoryTabs = categoryTabs;
        setView(categoryTabs);
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
        for ( final ItemCategory category : categories ) {
            categoryTabs.addCategoryTab(category.getName());
        }
    }
}
