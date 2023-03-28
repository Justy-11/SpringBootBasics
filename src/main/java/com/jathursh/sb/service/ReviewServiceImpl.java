package com.jathursh.sb.service;

import com.jathursh.sb.dto.ReviewDto;
import com.jathursh.sb.model.Review;
import com.jathursh.sb.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
        return null;
    }

    @Override
    public List<ReviewDto> getReviewByPokemonId(int pokemonId) {
        List<Review> reviews = reviewRepository.findByPokemonId(pokemonId);

        return reviews.stream().map(review-> mapToDto(review)).collect(Collectors.toList());
    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setStars(review.getStars());
        reviewDto.setContent(review.getContent());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        // have auto generated id
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        review.setTitle(reviewDto.getTitle());
        return review;
    }
}
