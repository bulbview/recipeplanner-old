package com.bulbview.recipeplanner.ui.presenter;

public enum Category {
    Bakery("Bakery"), Fruit_Vegetables("Fruit & Vegetables"), Tins("Tins, Jars & Cooking"), Dairy_Eggs(
                    "Dairy & Eggs"), Meat_Fish_Poultry("Meat, Fish & Poultry");

    public String string;

    private Category(final String s) {
        this.string = s;
    }

    @Override
    public String toString() {
        return string;
    }
}