package com.example.musicstore.service;

import com.example.musicstore.entity.Product;
import com.example.musicstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> showAllProducts(){
        return productRepository.findAll();}

    public Product getProduct(long id){return productRepository.findById(id).get();}

    public ResponseEntity create(Product product){ return ResponseEntity.ok(productRepository.save(product));}

    public ResponseEntity update(Product product) {
        Optional<Product> musicOptional = productRepository.findById(product.getId());
        if(musicOptional.isPresent()){
            if(product.getTitle()!=null)musicOptional.get().setTitle(product.getTitle());
            if(product.getArtist()!=null)musicOptional.get().setArtist(product.getArtist());
            if(product.getGenre()!=null)musicOptional.get().setGenre(product.getGenre());
            if(product.getPrice()!=0.0)musicOptional.get().setPrice(product.getPrice());
            if(product.getStock()!=0)musicOptional.get().setStock(product.getStock());
            if(product.getIsrc()!=0)musicOptional.get().setIsrc(product.getIsrc());
            if(product.getCover()!=null)musicOptional.get().setCover(product.getCover());
            if(product.getTracklist()!=null)musicOptional.get().setTracklist(product.getTracklist());
            if(product.getCd()!=0)musicOptional.get().setCd(product.getCd());
            if(product.getReleaseDate()!=null)musicOptional.get().setReleaseDate(product.getReleaseDate());

            productRepository.save(musicOptional.get());
            return ResponseEntity.ok(Collections.singletonMap("message","update correctly"));
        }
        return ResponseEntity.badRequest().body("Product not found!");
    }

    public ResponseEntity delete(List<Long> products){
        products.forEach(p -> {
            productRepository.deleteById(p);
        });
        return ResponseEntity.ok(Collections.singletonMap("message","deleted correctly"));
    }

    public List<Product> showProductsByTitle(String title) {
        return productRepository.findAllByTitle(title);
    }

    public List<Product> showProductsByArtist(String artist) {
        return productRepository.findAllByArtist(artist);
    }
}
