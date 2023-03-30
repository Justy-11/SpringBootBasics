package com.jathursh.sb.service;

import com.jathursh.sb.dto.ReviewDto;
import com.jathursh.sb.model.Pokemon;
import com.jathursh.sb.model.Review;
import com.jathursh.sb.repository.PokemonRepository;
import com.jathursh.sb.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Pokemon pokemon;
    private Review review;
    private ReviewDto reviewDto;

    @BeforeEach
    public void init(){
        pokemon = Pokemon.builder()
                .name("testPokemon")
                .type("testType")
                .build();

        review = Review.builder()
                .stars(4)
                .title("testTitle")
                .content("testContent")
                .build();

        reviewDto = ReviewDto.builder()
                .stars(3)
                .content("testContent")
                .title("testTitle")
                .build();
    }

    @Test
    void createReview() {

        Mockito.when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
        Mockito.when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto reviewDto1 = reviewService.createReview(pokemon.getId(), reviewDto);

        assertNotNull(reviewDto1);

    }

    @Test
    void getReviewByPokemonId() {

        Mockito.when(reviewRepository.findByPokemonId(pokemon.getId())).thenReturn(List.of(review));

        List<ReviewDto> reviewDtoList = reviewService.getReviewByPokemonId(pokemon.getId());

        assertNotNull(reviewDtoList);

    }

    @Test
    void getReviewById() {

        review.setPokemon(pokemon);  //need to set the pokemon to review

        Mockito.when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        Mockito.when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));

        ReviewDto reviewDto = reviewService.getReviewById(pokemon.getId(), review.getId());

        assertNotNull(reviewDto);

    }

    @Test
    void updateReview() {
        pokemon.setReviews(List.of(review));  //without this test is passing
        review.setPokemon(pokemon);

        Mockito.when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        Mockito.when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
        Mockito.when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto reviewDto1 = reviewService.updateReview(pokemon.getId(), review.getId(), reviewDto);

        assertNotNull(reviewDto1);
    }

    @Test
    void deleteReview() {
        review.setPokemon(pokemon);

        Mockito.when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        Mockito.when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));

        assertAll(()-> reviewService.deleteReview(pokemon.getId(), review.getId()));

    }
}