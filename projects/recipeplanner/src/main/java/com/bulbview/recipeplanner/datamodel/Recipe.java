package com.bulbview.recipeplanner.datamodel;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Recipe extends Entity {
    
    @Override
    public String toString() {
        return String.format("Recipe [name=%s]", name);
    }
}