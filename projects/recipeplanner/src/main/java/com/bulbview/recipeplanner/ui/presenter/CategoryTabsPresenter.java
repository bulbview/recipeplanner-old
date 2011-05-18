package com.bulbview.recipeplanner.ui.presenter;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.persistence.ObjectifyDao;
import com.bulbview.recipeplanner.ui.Presenter;
import com.bulbview.recipeplanner.ui.helper.CategoryEditor;
import com.bulbview.recipeplanner.ui.helper.CategoryTabs;
import com.bulbview.recipeplanner.ui.helper.MainWindowUiManager;

@Component
public class CategoryTabsPresenter extends Presenter {

    private ObjectifyDao<ItemCategory> categoryDao;
    private CategoryEditor       categoryEditor;
    private CategoryTabs         categoryTabs;
    private final Logger         logger;
    private MainWindowUiManager  mainWindow;

    public CategoryTabsPresenter() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void addCategoryMenuSelected() {
        categoryEditor.setItemCategory(createItemCategory());
        mainWindow.showCategoryWindow();
    }

    public void init() {
        categoryEditor.init();
        categoryTabs.init();
        setCategoriesInView(categoryDao.getAll());
    }

    public void saveCategory(final ItemCategory itemCategory) {
        logger.debug("saving category: {}...", itemCategory);
        final ItemCategory savedCategory = categoryDao.save(itemCategory);
        categoryTabs.addCategoryTab(savedCategory.getName());
        mainWindow.closeCategoryWindow();
    }

    @Autowired
    public void setCategoryDao(final ObjectifyDao<ItemCategory> categoryDao) {
        this.categoryDao = categoryDao;
    }

    public void setCategoryEditorWindow(final CategoryEditor categoryUiManager) {
        this.categoryEditor = categoryUiManager;
        setUiManager(categoryUiManager);
    }

    @Autowired
    public void setCategoryTabs(final CategoryTabs categoryTabs) {
        this.categoryTabs = categoryTabs;
        setUiManager(categoryTabs);
    }

    public void setMainWindow(final MainWindowUiManager mainWindow) {
        this.mainWindow = mainWindow;
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
