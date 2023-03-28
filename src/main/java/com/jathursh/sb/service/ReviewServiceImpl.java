package com.jathursh.sb.service;

import com.jathursh.sb.dto.ReviewDto;
import com.jathursh.sb.exception.PokemonNotFoundException;
import com.jathursh.sb.exception.ReviewNotFoundException;
import com.jathursh.sb.model.Pokemon;
import com.jathursh.sb.model.Review;
import com.jathursh.sb.repository.PokemonRepository;
import com.jathursh.sb.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);

        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon with associated review not found"));

        review.setPokemon(pokemon);

        Review newReview = reviewRepository.save(review);

        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewByPokemonId(int pokemonId) {
        List<Review> reviews = reviewRepository.findByPokemonId(pokemonId);

        return reviews.stream().map(review-> mapToDto(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int pokemonId, int reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new ReviewNotFoundException("Review with associated pokemon not found"));
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(()->new PokemonNotFoundException("Pokemon with associated review not found"));

        if(review.getPokemon().getId() != pokemon.getId()){
            throw new ReviewNotFoundException("Review with associated pokemon not found");
        }
        //else
        return mapToDto(review);
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
