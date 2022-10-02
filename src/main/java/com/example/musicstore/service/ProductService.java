package com.example.musicstore.service;

import com.example.musicstore.entity.MusicGenre;
import com.example.musicstore.entity.Product;
import com.example.musicstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> showAllProducts(){return productRepository.findAll();}

    @Transactional(readOnly = true)
    public Optional<Product> getProducts(long id){return productRepository.findById(id);}

    @Transactional(readOnly = false)
    public Optional<Product> addProduct(Product product) {
        Optional<Product> musicOptional = getProducts(product.getId());
        if (musicOptional.isEmpty()) {
            return Optional.of(productRepository.save(product));
        }
        return Optional.empty();
    }

    @Transactional(readOnly = false)
    public Optional<Product> updateProduct(Product product) {
        Optional<Product> musicOptional = getProducts(product.getId());
        return musicOptional.map(value -> productRepository.save(
                value
                        .setTitle(product.getTitle()))
                        .setStock(product.getStock())
                        .setCover(product.getCover())
                        .setTracklist(product.getTracklist())
                        .setCDNumber(product.getCDNumber())
                        .setArtist(product.getArtist())
                        .setRelaseDate(product.getRelaseDate())
                        .setGenre(product.getGenre())
                        .setPrice(product.getPrice()));
    }

    @Transactional(readOnly = false)
    public List<Product> showAllProductsPaged(int pageNumber, int pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Product> pagedResult = productRepository.findAll(paging);
        if(pagedResult.hasContent())
            return pagedResult.getContent();
        else
            return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Product> showProductsByTitle(String title){
        return productRepository.findByTitleContaining(title);
    }

    @Transactional(readOnly = true)
    public List<Product> showProductsByArtist(String artist){
        return productRepository.findByArtist(artist);
    }

    @Transactional(readOnly = true)
    public List<Product> showProductsBySong(String song){return productRepository.findByTracklistContaining(song);
    }

    @Transactional(readOnly = true)
    public List<Product> showProductsByGenre(MusicGenre genre, int pageNumber, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Product> pagedResult = productRepository.findByGenre(genre, paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }
    @Transactional(readOnly = true)
    public List<Product> showProductsByRelaseDate(String date){ return productRepository.findByRelaseDate(date); }
}
