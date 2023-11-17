package com.example.musicstore.repository;

import com.example.musicstore.entity.MusicGenre;
import com.example.musicstore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE LOWER(p.title) LIKE %:value%")
    List<Product> findAllByTitle(@Param("value") String value);

    @Query("SELECT p FROM Product p WHERE p.artist = ?1")
    List<Product> findAllByArtist(String artist);
}
