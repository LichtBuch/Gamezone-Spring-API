package com.lichtbuch.gamezone.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GameCreateRequest {

    public GameCreateRequest() {
    }

    public GameCreateRequest(String title, String description, LocalDate releaseDate, BigDecimal price, byte review, boolean deleted, boolean wishlisted) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.price = price;
        this.review = review;
        this.deleted = deleted;
        this.wishlisted = wishlisted;
    }

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

    private boolean deleted = false;
    private boolean wishlisted;

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

}
