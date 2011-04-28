package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

@Component
public class JdoRecipeDao implements RecipeDao {

    private final Logger     logger;
    @Autowired
    private ObjectifyFactory objectifyFactory;

    public JdoRecipeDao() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public Collection<Recipe> getAll() {
        final Objectify objectify = objectifyFactory.begin();
        return objectify.query(Recipe.class).list();
    }

    @Override
    public void saveRecipe(final Recipe recipe) {
        logger.debug("Saving recipe: {}", recipe);
    }

}
