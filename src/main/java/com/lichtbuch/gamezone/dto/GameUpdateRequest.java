package com.lichtbuch.gamezone.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GameUpdateRequest {

    @NotBlank
    private String title;

    private String description;

    private LocalDate releaseDate;

    @PositiveOrZero
    private BigDecimal price;

    @PositiveOrZero
    @Max(5)
    private Byte review;

    private Boolean deleted;
    private Boolean wishlisted;

    public GameUpdateRequest() {
    }

    public GameUpdateRequest(String title, String description, LocalDate releaseDate, BigDecimal price, Byte review, Boolean deleted, Boolean wishlisted) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.price = price;
        this.review = review;
        this.deleted = deleted;
        this.wishlisted = wishlisted;
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

    public Byte getReview() {
        return review;
    }

    public void setReview(Byte review) {
        this.review = review;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getWishlisted() {
        return wishlisted;
    }

    public void setWishlisted(Boolean wishlisted) {
        this.wishlisted = wishlisted;
    }
}
