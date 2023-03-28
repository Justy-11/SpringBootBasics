package com.jathursh.sb.service;

import com.jathursh.sb.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

     ReviewDto createReview(int pokemonId, ReviewDto reviewDto);
     List<ReviewDto> getReviewByPokemonId(int pokemonId);
}
