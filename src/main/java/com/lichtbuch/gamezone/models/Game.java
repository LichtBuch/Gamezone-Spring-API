package com.lichtbuch.gamezone.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Game {

    public Game() {
    }

    public Game(String title, String description, LocalDate releaseDate, BigDecimal price, byte review, boolean deleted, boolean wishlisted) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.price = price;
        this.review = review;
        this.deleted = deleted;
        this.wishlisted = wishlisted;
    }

    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    private String title;

    @NotNull
    private String description;

    private LocalDate releaseDate;
    @PositiveOrZero
    @NotNull
    private BigDecimal price;

    @PositiveOrZero
    @Max(5)
    private byte review;

    private boolean deleted;

    private boolean wishlisted;

    @ManyToMany(mappedBy = "games")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "game")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Image> images = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isWishlisted() {
        return wishlisted;
    }

    public void setWishlisted(boolean wishlisted) {
        this.wishlisted = wishlisted;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public byte getReview() {
        return review;
    }

    public void setReview(byte review) {
        this.review = review;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id && review == game.review && deleted == game.deleted && wishlisted == game.wishlisted && Objects.equals(title, game.title) && Objects.equals(description, game.description) && Objects.equals(releaseDate, game.releaseDate) && Objects.equals(price, game.price) && Objects.equals(categories, game.categories) && Objects.equals(images, game.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, releaseDate, price, review, deleted, wishlisted, categories, images);
    }
}
