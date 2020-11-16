package org.jschool.recipebookonemore.controllers;

import org.jschool.recipebookonemore.model.Product;
import org.jschool.recipebookonemore.service.RecipeBookServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping(produces = {"application/xml", "application/json"})
public class RecipeBookOneMoreController {

    @GetMapping("/")
    public String welcome() {
        return "<h1>This is Recipe book one more!</h1><h1>Welcome!</h1>";
    }

    @PostMapping("/createProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public String createProduct(@RequestBody Product product, Model model) {
        model.addAttribute(product);
        return product.toString();
    }
}
