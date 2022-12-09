package com.example.musicstore.controller;

import com.example.musicstore.entity.MusicGenre;
import com.example.musicstore.entity.Product;
import com.example.musicstore.service.ProductService;
import com.example.musicstore.support.ResponseMessage;
import com.example.musicstore.support.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(productService.showAllProducts());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/add", consumes = {"application/json"})
    public ResponseEntity<?> addMusic(@RequestBody @Valid Product product) throws ProductNotFoundException {
        Optional<Product> optionalMusic = productService.addProduct(product);
        if(optionalMusic.isPresent())
            return ResponseEntity.ok(optionalMusic.get());
        return ResponseEntity.badRequest().body(new ResponseMessage("Product inexistent"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/edit", consumes = {"application/json"})
    public ResponseEntity<?> updateMusic(@RequestBody @Valid Product product) {
        Optional<Product> optionalMusic = productService.addProduct(product);
        if(optionalMusic.isPresent())
            return ResponseEntity.ok(optionalMusic.get());
        return ResponseEntity.badRequest().body(new ResponseMessage("Product inexistent"));
    }

    @GetMapping(value = "/all/paged", produces = "application/json")
    public ResponseEntity<List<Product>> getAllPaged(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                   @RequestParam(value = "pageSize", defaultValue = "9") int pageSize,
                                                   @RequestParam(value = "sortBy", defaultValue = "id") String sortBy){
        List<Product> result = productService.showAllProductsPaged(pageNumber, pageSize, sortBy);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{title}", produces = "application/json")
    public ResponseEntity<?> getByTitle(@RequestParam(value = "title", defaultValue = "") String title){
        List<Product> result = productService.showProductsByTitle(title);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{artist}", produces = "application/json")
    public ResponseEntity<?> getByArtist(@RequestParam(value = "artist", defaultValue = "") String artist){
        List<Product> result = productService.showProductsByArtist(artist);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{genre}/paged", produces = "application/json")
    public ResponseEntity<?> getByGenrePaged(@PathVariable("genre") String genre,
                                        @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                        @RequestParam(value = "pageSize", defaultValue = "9") int pageSize,
                                        @RequestParam(value = "sortBy", defaultValue = "idMusic") String sortBy){
        List<Product> result = productService.showProductsByGenre(MusicGenre.valueOf(genre.toUpperCase()), pageNumber,pageSize,sortBy);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/song", produces = "application/json")
    public ResponseEntity<?> getBySong(@RequestParam(value = "song", defaultValue = "") String song){
        List<Product> result = productService.showProductsBySong(song);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/relase_date", produces = "application/json")
    public ResponseEntity<?> getByRelasDate(@RequestParam(value = "relase_date", defaultValue = "") String date) {
        List<Product> result = productService.showProductsByRelaseDate(date);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<?> getByIdMusic(@PathVariable("id") int idMusic){
        Optional<Product> optionalMusic = productService.getProducts(idMusic);
        if(optionalMusic.isPresent()) {
            return ResponseEntity.ok(optionalMusic.get());
        }else {
            return ResponseEntity.badRequest().body(new ResponseMessage("Product inexistent"));
        }
    }
}
