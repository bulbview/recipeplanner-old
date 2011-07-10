package com.bulbview.recipeplanner.datamodel;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Ingredient {
    
    private ItemCategory category;
    
    private Item         item;
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Ingredient)) {
            return false;
        }
        final Ingredient other = (Ingredient) obj;
        if (category == null) {
            if (other.category != null) {
                return false;
            }
        }
        else if (!category.equals(other.category)) {
            return false;
        }
        if (item == null) {
            if (other.item != null) {
                return false;
            }
        }
        else if (!item.equals(other.item)) {
            return false;
        }
        return true;
    }
    
    public ItemCategory getCategory() {
        return category;
    }
    
    public String getName() {
        return item != null ? item.getName() : null;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((category == null) ? 0 : category.hashCode());
        result = (prime * result) + ((item == null) ? 0 : item.hashCode());
        return result;
    }
    
    public void setCategory(final ItemCategory category) {
        this.category = category;
    }
    
    public void setItem(final Item item) {
        this.item = item;
        
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
}
