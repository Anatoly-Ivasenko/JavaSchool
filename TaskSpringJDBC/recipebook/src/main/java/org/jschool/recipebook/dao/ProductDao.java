package org.jschool.recipebook.dao;

import org.jschool.recipebook.model.Product;

import java.util.List;

public interface ProductDao {

    void createProduct(Product product);

    Product findProductByName(String productName);

    List<Product> getAllProducts();

    Product getProductById(int productId);
}
