package org.jschool.recipebookonemore.repository;

import org.jschool.recipebookonemore.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    Product findByName(String name);

    Product findById(int id);
}
