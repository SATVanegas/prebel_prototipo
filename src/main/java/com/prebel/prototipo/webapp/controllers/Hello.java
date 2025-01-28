package com.prebel.prototipo.webapp.controllers;
import com.prebel.prototipo.webapp.models.Product;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Hello {

    // Declaración e inicialización de la lista
    private final List<Product> products;

    // Constructor para inicializar la lista de productos
    public Hello() {
        products = new ArrayList<>();
        products.add(new Product("67523", "iron", "aluminio", "red", "20", "black", 10));
        products.add(new Product("67524", "steel", "acero", "blue", "25", "white", 15));
        products.add(new Product("67525", "copper", "cobre", "green", "18", "silver", 8));
        products.add(new Product("67526", "gold", "oro", "yellow", "30", "golden", 20));
        products.add(new Product("67527", "plastic", "plástico", "purple", "10", "gray", 12));
        products.add(new Product("67528", "glass", "vidrio", "transparent", "5", "clear", 5));
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable int id) {
        return products.get(id);
    }
    @GetMapping("/products")
    public List<Product> getProducts() {
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println("hola laura como estas");
        return products;

    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        products.add(product);
        return product;
    }
}
