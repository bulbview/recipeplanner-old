package com.bulbview.recipeplanner.datamodel;

import java.util.Collection;

import javax.persistence.Embedded;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.appengine.repackaged.com.google.common.collect.Sets;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Recipe extends Entity {
    
    @Embedded
    private Collection<Ingredient> ingredients;
    
    public Recipe() {
        this.ingredients = Sets.newHashSet();
    }
    
    public Collection<Ingredient> getIngredients() {
        return ingredients;
    }
    
    public void setIngredients(final Collection<Ingredient> ingredients) {
        this.ingredients = ingredients;
        
    }
    
    @Override
    public String toString() {
        return String.format("Recipe [name=%s]", name);
    }
}