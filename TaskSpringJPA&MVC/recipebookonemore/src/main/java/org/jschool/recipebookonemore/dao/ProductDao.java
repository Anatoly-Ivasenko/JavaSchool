package org.jschool.recipebookonemore.dao;

import org.jschool.recipebookonemore.model.Product;

import java.util.Collection;

public interface ProductDao {

    void save(Product product);

    Product findByName(String productName);

    Collection<Product> findAll();

    Product findById(int productId);
}
