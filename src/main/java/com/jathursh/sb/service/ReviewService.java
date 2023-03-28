package com.jathursh.sb.service;

import com.jathursh.sb.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

     ReviewDto createReview(int pokemonId, ReviewDto reviewDto);
     List<ReviewDto> getReviewByPokemonId(int pokemonId);
     ReviewDto getReviewById(int pokemonId, int reviewId);
     ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto);
     void deleteReview(int pokemonId, int reviewId);
}
