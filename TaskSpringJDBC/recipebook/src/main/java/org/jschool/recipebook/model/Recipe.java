package org.jschool.recipebook.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Recipe {

    private int id;
    private String title;
    private String description;
    private Map<Product,Double> ingredients = new HashMap<>();
    private Set<byte[]> photos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Product, Double> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<Product, Double> ingredients) {
        this.ingredients = ingredients;
    }

    private String ingredientsToString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        ingredients.forEach(((product, aDouble) ->
                stringBuilder.append(product.getName()).append(":").append(aDouble).append(product.getMeasure()).append(",")));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ingredients=" + ingredientsToString() +
                '}';
    }



}

