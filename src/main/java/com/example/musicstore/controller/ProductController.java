package com.example.musicstore.controller;

import com.example.musicstore.entity.MusicGenre;
import com.example.musicstore.entity.Product;
import com.example.musicstore.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id){return productService.getProduct(id);}

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.showAllProducts();
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> addProduct(@RequestBody Product product){return productService.create(product);}

    @PostMapping(path = "/update")
    public ResponseEntity updateProduct(@RequestBody Product product){
        return productService.update(product);
    }

    @PostMapping(path = "/delete")
    public ResponseEntity deletedProduct(@RequestBody List<Long> product){
        return productService.delete(product);
    }

   @PostMapping(value = "/search/title/{title}", produces = "application/json")
   public ResponseEntity<?> getByTitle(@PathVariable String title){
       List<Product> result = productService.showProductsByTitle(title);
       return ResponseEntity.ok(result);
   }
}
