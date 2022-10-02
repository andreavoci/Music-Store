package com.example.musicstore.repository;

import com.example.musicstore.entity.MusicGenre;
import com.example.musicstore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByTitleContaining(String title);
    List<Product> findByTitleContainingIgnoreCase(String title);
    List<Product> findByArtist(String artist);
    List<Product> findByISRC(Long ISRC);
    Page<Product> findByGenre(@NotNull(message = "Genre is required") MusicGenre genre, Pageable pageable);
    List<Product> findByRelaseDate(String date);
    List<Product> findByTracklistContaining(String song);

    boolean existsByTitle(String title);
    boolean existsByISRC(Long ISRC);
    boolean existsByArtist(String artist);
    //boolean StockNotNull();
}
