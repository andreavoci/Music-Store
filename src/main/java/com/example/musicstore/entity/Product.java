package com.example.musicstore.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
@Table(name = "product", schema = "store")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private long id;

   @Basic
   @Column(name = "ISRC", nullable = false, length = 100)
   private long ISRC;

   @Basic
   @NotNull(message = "Album title is required!")
   @Column(name = "title", length = 150)
   private String title;

   @Basic
   @NotNull(message = "Album artist is required!")
   @Column(name = "artist", length = 100)
   private String artist;

    @Basic
    @NotNull(message = "Album relase date is required!")
    @Column(name = "relase_date", length = 150)
    private String relaseDate;

    @Basic
    @NotNull(message = "Album genre is required!")
    @Enumerated(EnumType.STRING)
    @Column(name = "genre", length = 150)
    private MusicGenre genre;

    @Basic
    @Column(name = "cd_number", nullable = false)
    private int CDNumber;

    @Basic
    @Column(name = "tracklist", nullable = false, length = 1000)
    private String tracklist;

    @Basic
    @Column(name = "price", nullable = false, length = 20)
    private double price;

    @Basic
    @Column(name = "cover", nullable = false, length = Integer.MAX_VALUE)
    private String cover;

    @Basic
    @Column(name = "stock", nullable = false, length = 20)
    private int stock;

    @Basic
    @Column(name = "opt_lock", nullable = false)
    private long version = 0L;

    public Product(String title, String artist, long ISRC, String relaseDate, MusicGenre genre, int CDNumber, String tracklist,String cover, double price, int stock, long version){
        this.ISRC=ISRC;
        this.title=title;
        this.artist=artist;
        this.relaseDate=relaseDate;
        this.genre=genre;
        this.CDNumber=CDNumber;
        this.tracklist=tracklist;
        this.price=price;
        this.cover=cover;
        this.stock=stock;
        this.version=version;

    }
}
