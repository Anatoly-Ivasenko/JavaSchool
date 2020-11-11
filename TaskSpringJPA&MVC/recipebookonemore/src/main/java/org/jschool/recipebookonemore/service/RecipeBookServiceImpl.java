package org.jschool.recipebookonemore.service;

import org.jschool.recipebookonemore.model.Product;
import org.jschool.recipebookonemore.model.Recipe;
import org.jschool.recipebookonemore.repository.ProductRepository;
import org.jschool.recipebookonemore.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeBookServiceImpl implements  RecipeBookService{

    private RecipeRepository recipeRepository;
    private ProductRepository productRepository;

    @Autowired
    public RecipeBookServiceImpl(RecipeRepository recipeRepository, ProductRepository productRepository) {
        this.recipeRepository = recipeRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recipe> findAllRecipes() {
        return (List<Recipe>) recipeRepository.findAll();
    }

    @Override
    @Transactional
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findProductById(int id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Recipe findRecipeByTitle(String title) {
        return recipeRepository.findByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recipe> findRecipesByProductName(String productName) {
        Product product = productRepository.findByName(productName);
        return recipeRepository.findRecipesByIngredient(product);
    }
}
