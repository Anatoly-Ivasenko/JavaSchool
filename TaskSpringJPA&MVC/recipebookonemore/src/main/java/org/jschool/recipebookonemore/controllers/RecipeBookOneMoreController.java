package org.jschool.recipebookonemore.controllers;

import org.jschool.recipebookonemore.model.Product;
import org.jschool.recipebookonemore.service.RecipeBookService;
import org.jschool.recipebookonemore.service.RecipeBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping(produces = {"application/xml", "application/json"})
public class RecipeBookOneMoreController {

    private final RecipeBookService recipeBookService;

    @Autowired
    public RecipeBookOneMoreController(RecipeBookService recipeBookService) {
        this.recipeBookService = recipeBookService;
    }

    @GetMapping("/")
    public String welcome() {
        return "<h1>This is Recipe book one more!</h1><h1>Welcome!</h1><a href=\"/products\">All products </a>";
    }

    @GetMapping("/initdata")
    public String initdata() {
        Product product = new Product();
        product.setName("Морковь");
        product.setMeasure("кг");
        recipeBookService.saveProduct(product);
        Product product1 = new Product();
        product1.setName("Капуста");
        product1.setMeasure("кг");
        recipeBookService.saveProduct(product1);
        Product product2 = new Product();
        product2.setName("Картофель");
        product2.setMeasure("кг");
        recipeBookService.saveProduct(product2);
        Product product3 = new Product();
        product3.setName("Лук");
        product3.setMeasure("кг");
        recipeBookService.saveProduct(product3);
        Product product4 = new Product();
        product4.setName("Вода");
        product4.setMeasure("л");
        recipeBookService.saveProduct(product4);
        return "Data loaded successfully";
    }

    @GetMapping("/products/{productId}")
    public String getProduct(@PathVariable int productId) {
        Product product = recipeBookService.findProductById(productId);
        if (product == null) {
            return "No such product";
        } else {
            return product.toString();
        }
    }

    @GetMapping("/products")
    public String getAllProducts() {
        List<Product> allProducts = recipeBookService.findAllProducts();
        StringBuilder response = new StringBuilder();
        response.append("<p>Всего ").append(allProducts.size()).append(" продуктов. </p>");
        allProducts.forEach(product -> {
            response.append("<p>");
            response.append(product.toString());
            response.append("</p>");
        });
        return response.toString();
    }

    @PostMapping("/createProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public String createProduct(@RequestBody Product product, Model model) {
        model.addAttribute(product);
        return product.toString();
    }


}
