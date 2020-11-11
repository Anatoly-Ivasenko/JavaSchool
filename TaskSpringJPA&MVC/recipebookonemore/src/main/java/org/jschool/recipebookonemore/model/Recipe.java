package org.jschool.recipebookonemore.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name="recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;

    @ElementCollection
    @CollectionTable(name = "ingredients")
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product,Double> ingredients = new HashMap<>();

//    private Set<byte[]> photos;  //TODO Реализовать

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return title.equals(recipe.title) &&
                description.equals(recipe.description) &&
                ingredients.equals(recipe.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, ingredients);
    }
}

