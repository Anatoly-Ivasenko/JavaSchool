package org.jschool.recipebook.model;

import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private String measure;

    public Product(){
    }

    public Product(String name, String measure) {
        this.name = name;
        this.measure = measure;
    }

    public Product(int id, String name, String measure) {
        this.id = id;
        this.name = name;
        this.measure = measure;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                name.equals(product.name) &&
                measure.equals(product.measure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, measure);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", measure='" + measure + "'" +
                '}';
    }

}
