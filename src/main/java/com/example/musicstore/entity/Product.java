package com.example.musicstore.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product", schema = "store")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

   private long isrc;

   private String title;

   private String artist;

    private Date releaseDate;

    @Enumerated(EnumType.STRING)
    private MusicGenre genre;

    private int cd;

    private String tracklist;

    private double price;

    private String cover;

    private int stock;

    @Version
    private long version = 0L;

    public Product(String title, String artist, long isrc, Date releaseDate, MusicGenre genre, int cd, String tracklist,String cover, double price, int stock){
        this.isrc =isrc;
        this.title=title;
        this.artist=artist;
        this.releaseDate=releaseDate;
        this.genre=genre;
        this.cd=cd;
        this.tracklist=tracklist;
        this.price=price;
        this.cover=cover;
        this.stock=stock;
        this.version= 0L;
    }
}
