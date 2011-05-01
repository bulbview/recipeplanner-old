package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;

@Component
public class RecipeJdoDao implements RecipeDao {

    private final Logger     logger;

    @Autowired
    private ObjectifyFactory objectifyFactory;

    public RecipeJdoDao() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public Collection<Recipe> getAll() {
        final Objectify objectify = beginObjectify();
        final Query<Recipe> query = objectify.query(Recipe.class);
        return query.list();
    }

    @PostConstruct
    public void registerEntityType() {
        objectifyFactory.register(Recipe.class);

    }

    @Override
    public void saveRecipe(final Recipe recipe) {
        logger.debug("Saving recipe: {}...", recipe);
        final Objectify objectify = beginObjectify();
        objectify.put(recipe);

    }

    private Objectify beginObjectify() {
        return objectifyFactory.begin();
    }

}
