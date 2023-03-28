package com.jathursh.sb.controller;

import com.jathursh.sb.dto.ReviewDto;
import com.jathursh.sb.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("pokemon/{pokemonId}/reviews")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "pokemonId") int pokemonId, @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.createReview(pokemonId , reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("pokemon/{pokemonId}/reviews")
    List<ReviewDto> getReviews(@PathVariable(value = "pokemonId") int pokemonId){
        return reviewService.getReviewByPokemonId(pokemonId);
    }

    @GetMapping("pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "pokemonId") int pokemonId, @PathVariable(value = "reviewId") int reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(pokemonId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.CREATED);
    }
}
